package org.gitlab.api.http;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;
import org.gitlab.api.GitlabAPI;

/**
 * Gitlab HTTP Requestor
 *
 * Responsible for handling HTTP requests to the Gitlab API
 *
 * @author @timols
 */
public class GitlabHTTPRequestor {

    private final GitlabAPI _root;
    private String _method = "GET"; // Default to GET requests
    private Map<String, Object> _data = new HashMap<String, Object>();

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
        _root = root;
    }

    /**
     * Sets the HTTP Request method for the request.
     *
     * Has a fluent api for method chaining.
     *
     * @param   method    The HTTP method
     * @return  this
     */
    public GitlabHTTPRequestor method(String method) {
        try {
            _method = METHOD.valueOf(method).toString();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HTTP Method: " + method + ". Must be one of " + METHOD.prettyValues());
        }

        return this;
    }

    /**
     * Sets the HTTP Form Post parameters for the request
     *
     * Has a fluent api for method chaining
     *
     * @param   key
     * @param   value
     * @return  this
     */
    public GitlabHTTPRequestor with(String key, Object value) {
        if (value != null && key != null) {
            _data.put(key, value);
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
     * @param tailAPIUrl       The url to open a connection to (after the host and namespace)
     * @param type             The type of the response to be deserialized from
     * @param instance         The instance to update from the response
     *
     * @return                 An object of type T
     * @throws java.io.IOException
     */
    public <T> T to(String tailAPIUrl, Class<T> type, T instance) throws IOException {
        HttpURLConnection connection = setupConnection(_root.getAPIUrl(tailAPIUrl));

        if (hasOutput()) {
            submitData(connection);
        }

        try {
            return parse(connection, type, instance);
        } catch (IOException e) {
            handleAPIError(e, connection);
        }

        return null;
    }

    private void submitData(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        GitlabAPI.MAPPER.writeValue(connection.getOutputStream(), _data);
    }

    private boolean hasOutput() {
        return _method.equals("POST") || _method.equals("PUT") && !_data.isEmpty();
    }

    private HttpURLConnection setupConnection(URL url) throws IOException {
        if (_root.isIgnoreCertificateErrors()) {
            ignoreCertificateErrors();
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod(_method);
        } catch (ProtocolException e) {
            // Hack in case the API uses a non-standard HTTP verb
            try {
                Field methodField = connection.getClass().getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(connection, _method);
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
            String data = IOUtils.toString(reader);

            if (type != null) {
                return GitlabAPI.MAPPER.readValue(data, type);
            } else if (instance != null) {
                return GitlabAPI.MAPPER.readerForUpdating(instance).readValue(data);
            } else {
                return null;
            }
        } catch (SSLHandshakeException e) {
            throw new SSLHandshakeException("You can disable certificate checking by setting ignoreCertificateErrors on GitlabHTTPRequestor");
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

        InputStream es = wrapStream(connection, connection.getErrorStream());
        try {
            if (es != null) {
                throw (IOException) new IOException(IOUtils.toString(es, "UTF-8")).initCause(e);
            } else {
                throw e;
            }
        } finally {
            IOUtils.closeQuietly(es);
        }
    }

    private void ignoreCertificateErrors() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            // Ignore it
        }
    }
}
