package org.gitlab.api.http;

import org.apache.commons.io.IOUtils;
import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.GitlabAPIException;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabCommit;

import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Gitlab HTTP Requestor
 * Responsible for handling HTTP requests to the Gitlab API
 *
 * @author &#064;timols (Tim O)
 */
public class GitlabHTTPRequestor {

    private static final Pattern PAGE_PATTERN = Pattern.compile("([&|?])page=(\\d+)");

    private final GitlabAPI root;

    private String method = "GET"; // Default to GET requests
    private Map<String, Object> data = new HashMap<String, Object>();

    private String apiToken;
    private TokenType tokenType;
    private AuthMethod authMethod;

    private enum METHOD {
        GET, PUT, POST, PATCH, DELETE, HEAD, OPTIONS, TRACE;

        public static String prettyValues() {
            METHOD[] methods = METHOD.values();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < methods.length; i++) {
                METHOD method = methods[i];
                builder.append(method.toString());

                if (i != methods.length - 1) {
                    builder.append(", ");
                }
            }
            return builder.toString();
        }
    }

    public GitlabHTTPRequestor(GitlabAPI root) {
        this.root = root;
    }

    /**
     * Sets authentication data for the request.
     * Has a fluent api for method chaining.
     *
     * @param token  The token value
     * @param type   The type of the token
     * @param method The authentication method
     * @return this
     */
    public GitlabHTTPRequestor authenticate(String token, TokenType type, AuthMethod method) {
        this.apiToken = token;
        this.tokenType = type;
        this.authMethod = method;
        return this;
    }

    /**
     * Sets the HTTP Request method for the request.
     * Has a fluent api for method chaining.
     *
     * @param method The HTTP method
     * @return this
     */
    public GitlabHTTPRequestor method(String method) {
        try {
            this.method = METHOD.valueOf(method).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HTTP Method: " + method + ". Must be one of " + METHOD.prettyValues());
        }

        return this;
    }

    /**
     * Sets the HTTP Form Post parameters for the request
     * Has a fluent api for method chaining
     *
     * @param key       Form parameter Key
     * @param value     Form parameter Value
     * @return this
     */
    public GitlabHTTPRequestor with(String key, Object value) {
        if (value != null && key != null) {
            data.put(key, value);
        }
        return this;
    }

    public <T> T to(String tailAPIUrl, T instance) throws IOException {
        return to(tailAPIUrl, null, instance);
    }

    public <T> T to(String tailAPIUrl, Class<T> type) throws IOException {
        return to(tailAPIUrl, type, null);
    }

    /**
     * Opens the HTTP(S) connection, submits any data and parses the response.
     * Will throw an error
     *
     * @param <T>        The return type of the method
     * @param tailAPIUrl The url to open a connection to (after the host and namespace)
     * @param type       The type of the response to be deserialized from
     * @param instance   The instance to update from the response
     * @return An object of type T
     * @throws java.io.IOException on gitlab api error
     */
    public <T> T to(String tailAPIUrl, Class<T> type, T instance) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = setupConnection(root.getAPIUrl(tailAPIUrl));

            if (hasOutput()) {
                 submitData(connection);
            } else if ("PUT".equals(method)) {
                // PUT requires Content-Length: 0 even when there is no body (eg: API for protecting a branch)
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(0);
            }

            try {
                return parse(connection, type, instance);
            } catch (IOException e) {
                handleAPIError(e, connection);
            }

            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public <T> List<T> getAll(final String tailUrl, final Class<T[]> type) {
        List<T> results = new ArrayList<T>();
        Iterator<T[]> iterator = asIterator(tailUrl, type);

        while (iterator.hasNext()) {
            T[] requests = iterator.next();

            if (requests.length > 0) {
                results.addAll(Arrays.asList(requests));
            }
        }
        return results;
    }

    public <T> Iterator<T> asIterator(final String tailApiUrl, final Class<T> type) {
        method("GET"); // Ensure we only use iterators for GET requests

        // Ensure that we don't submit any data and alert the user
        if (!data.isEmpty()) {
            throw new IllegalStateException();
        }

        return new Iterator<T>() {
            T next;
            URL url;

            {
                try {
                    url = root.getAPIUrl(tailApiUrl);
                } catch (IOException e) {
                    throw new Error(e);
                }
            }

            @Override
            public boolean hasNext() {
                fetch();
                if (next != null && next.getClass().isArray()) {
                    Object[] arr = (Object[]) next;
                    return arr.length != 0;
                } else {
                    return next != null;
                }
            }

            @Override
            public T next() {
                fetch();
                T record = next;

                if (record == null) {
                    throw new NoSuchElementException();
                }

                next = null;
                return record;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void fetch() {
                if (next != null) {
                    return;
                }

                if (url == null) {
                    return;
                }

                try {
                    HttpURLConnection connection = setupConnection(url);
                    try {
                        next = parse(connection, type, null);
                        assert next != null;
                        findNextUrl();
                    } catch (IOException e) {
                        handleAPIError(e, connection);
                    }
                } catch (IOException e) {
                    throw new Error(e);
                }
            }

            private void findNextUrl() throws MalformedURLException {
                String url = this.url.toString();

                this.url = null;
                /* Increment the page number for the url if a "page" property exists,
                 * otherwise, add the page property and increment it.
                 * The Gitlab API is not a compliant hypermedia REST api, so we use
                 * a naive implementation.
                 */
                Matcher matcher = PAGE_PATTERN.matcher(url);

                if (matcher.find()) {
                    Integer page = Integer.parseInt(matcher.group(2)) + 1;
                    this.url = new URL(matcher.replaceAll(matcher.group(1) + "page=" + page));
                } else {
                    if (GitlabCommit[].class == type) {
                        // there is a bug in the Gitlab CE API
                        // (https://gitlab.com/gitlab-org/gitlab-ce/issues/759)
                        // that starts pagination with page=0 for commits
                        this.url = new URL(url + (url.indexOf('?') > 0 ? '&' : '?') + "page=1");
                    } else {
                        // Since the page query was not present, its safe to assume that we just
                        // currently used the first page, so we can default to page 2
                        this.url = new URL(url + (url.indexOf('?') > 0 ? '&' : '?') + "&page=2");
                    }
                }
            }
        };
    }

    private void submitData(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        GitlabAPI.MAPPER.writeValue(connection.getOutputStream(), data);
    }

    private boolean hasOutput() {
        return method.equals("POST") || method.equals("PUT") && !data.isEmpty();
    }

    private HttpURLConnection setupConnection(URL url) throws IOException {
        if (root.isIgnoreCertificateErrors()) {
            ignoreCertificateErrors();
        }

        if (apiToken != null && authMethod == AuthMethod.URL_PARAMETER) {
            String urlWithAuth = url.toString();
            urlWithAuth = urlWithAuth + (urlWithAuth.indexOf('?') > 0 ? '&' : '?') + tokenType.getTokenParamName() + "=" + apiToken;
            url = new URL(urlWithAuth);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (apiToken != null && authMethod == AuthMethod.HEADER) {
            connection.setRequestProperty(tokenType.getTokenHeaderName(), String.format(tokenType.getTokenHeaderFormat(), apiToken));
        }

        final int requestTimeout = root.getRequestTimeout();
        if (requestTimeout > 0) {
            connection.setReadTimeout(requestTimeout);
        }

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            // Hack in case the API uses a non-standard HTTP verb
            try {
                Field methodField = connection.getClass().getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(connection, method);
            } catch (Exception x) {
                throw (IOException) new IOException("Failed to set the custom verb").initCause(x);
            }
        }

        connection.setRequestProperty("Accept-Encoding", "gzip");
        return connection;
    }

    private <T> T parse(HttpURLConnection connection, Class<T> type, T instance) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(wrapStream(connection, connection.getInputStream()), "UTF-8");
            if (byte[].class == type) {
            	return type.cast(IOUtils.toByteArray(reader));
            }
            String data = IOUtils.toString(reader);
            if (type != null) {
                return GitlabAPI.MAPPER.readValue(data, type);
            } else if (instance != null) {
                return GitlabAPI.MAPPER.readerForUpdating(instance).readValue(data);
            } else {
                return null;
            }
        } catch (SSLHandshakeException e) {
            throw new SSLHandshakeException("You can disable certificate checking by setting ignoreCertificateErrors on GitlabHTTPRequestor. SSL Error: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private InputStream wrapStream(HttpURLConnection connection, InputStream inputStream) throws IOException {
        String encoding = connection.getContentEncoding();

        if (encoding == null || inputStream == null) {
            return inputStream;
        } else if (encoding.equals("gzip")) {
            return new GZIPInputStream(inputStream);
        } else {
            throw new UnsupportedOperationException("Unexpected Content-Encoding: " + encoding);
        }
    }

    private void handleAPIError(IOException e, HttpURLConnection connection) throws IOException {
        if (e instanceof FileNotFoundException) {
            throw e;    // pass through 404 Not Found to allow the caller to handle it intelligently
        }
        if (e instanceof SocketTimeoutException && root.getRequestTimeout() > 0) {
            throw e;
        }

        InputStream es = wrapStream(connection, connection.getErrorStream());
        try {
            String error = null;
            if (es != null) {
                error = IOUtils.toString(es, "UTF-8");
            }
            throw new GitlabAPIException(error, connection.getResponseCode(), e);
        } finally {
            IOUtils.closeQuietly(es);
        }
    }

    private void ignoreCertificateErrors() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Added per https://github.com/timols/java-gitlab-api/issues/44
        HostnameVerifier nullVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Added per https://github.com/timols/java-gitlab-api/issues/44
            HttpsURLConnection.setDefaultHostnameVerifier(nullVerifier);
        } catch (Exception e) {
            // Ignore it
        }
    }
}
