package org.gitlab.api.http;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.gitlab.api.GitlabAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class GitlabHTTPRequestorTest {

    @Test
    @DisplayName(value = "Expected success, calling the \"setupConnection\" method if the connection timeout = 0")
    public void unitTest_20180503194340() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getConnectionTimeout()).thenReturn(0);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        HttpURLConnection connection = (HttpURLConnection) method.invoke(http, url);
        assertThat(connection.getConnectTimeout(), is(0));
    }

    @Test
    @DisplayName(value = "Expected success, calling the \"setupConnection\" method if the connection timeout > 0")
    public void unitTest_20180503194559() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getConnectionTimeout()).thenReturn(456);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        HttpURLConnection connection = (HttpURLConnection) method.invoke(http, url);
        assertThat(connection.getConnectTimeout(), is(456));
    }

    @Test
    @DisplayName(value = "An error is expected, calling the \"setupConnection\" method if the connection timeout < 0")
    public void unitTest_20180503194643() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getConnectionTimeout()).thenReturn(-555);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        Throwable throwable = null;
        try {
            method.invoke(http, url);
        } catch (Exception e) {
            throwable = e.getCause();
        }
        assertThat(throwable, not(nullValue()));
        assertThat(throwable, is(instanceOf(IllegalArgumentException.class)));
        assertThat(throwable.getMessage(), is("timeouts can't be negative"));
    }

    @Test
    @DisplayName(value = "Expected success, calling the \"setupConnection\" method if the read timeout = 0")
    public void unitTest_20180503202458() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getResponseReadTimeout()).thenReturn(0);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        HttpURLConnection connection = (HttpURLConnection) method.invoke(http, url);
        assertThat(connection.getReadTimeout(), is(0));
    }

    @Test
    @DisplayName(value = "Expected success, calling the \"setupConnection\" method if the read timeout > 0")
    public void unitTest_20180503203531() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getResponseReadTimeout()).thenReturn(555);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        HttpURLConnection connection = (HttpURLConnection) method.invoke(http, url);
        assertThat(connection.getReadTimeout(), is(555));
    }

    @Test
    @DisplayName(value = "An error is expected, calling the \"setupConnection\" method if the read timeout < 0")
    public void unitTest_20180503203635() throws Exception {
        GitlabAPI api = mock(GitlabAPI.class);
        when(api.getResponseReadTimeout()).thenReturn(-555);
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(api);
        URL url = new URL("http://test.url");
        Method method = GitlabHTTPRequestor.class.getDeclaredMethod("setupConnection", URL.class);
        method.setAccessible(true);
        Throwable throwable = null;
        try {
            method.invoke(http, url);
        } catch (Exception e) {
            throwable = e.getCause();
        }
        assertThat(throwable, not(nullValue()));
        assertThat(throwable, is(instanceOf(IllegalArgumentException.class)));
        assertThat(throwable.getMessage(), is("timeouts can't be negative"));
    }

}
