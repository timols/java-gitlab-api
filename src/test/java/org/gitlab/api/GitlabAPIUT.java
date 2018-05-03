package org.gitlab.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Oleg Shaburov on 03.05.2018
 * shaburov.o.a@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class GitlabAPIUT {

    @Test
    @DisplayName(value = "Check non-routable connection with connection timeout error")
    public void unitTest_20180503175711() {
        GitlabAPI api = GitlabAPI.connect("http://172.16.0.0:80", "test");
        api.setConnectionTimeout(100);
        Throwable exception = assertThrows(SocketTimeoutException.class, api::getVersion);
        assertThat(exception.getMessage(), is("connect timed out"));
    }

    @Test
    @DisplayName(value = "Check default value is 0 for connection timeout")
    public void unitTest_20180503185536() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        assertThat(api.getConnectionTimeout(), is(0));
    }

    @Test
    @DisplayName(value = "Check set/get methods for connection timeout parameter")
    public void unitTest_20180503185632() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        api.setConnectionTimeout(123);
        assertThat(api.getConnectionTimeout(), is(123));
    }

    @Test
    @DisplayName(value = "Check ignore negative value for connection timeout parameter")
    public void unitTest_20180503185750() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        api.setConnectionTimeout(-123);
        assertThat(api.getConnectionTimeout(), is(0));
    }

    @Test
    @DisplayName(value = "Check connection with read timeout error")
    public void unitTest_20180503191159() throws IOException {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(15896);
            GitlabAPI api = GitlabAPI.connect("http://localhost:15896", "test");
            api.setResponseReadTimeout(100);
            Throwable exception = assertThrows(SocketTimeoutException.class, api::getVersion);
            assertThat(exception.getMessage(), is("Read timed out"));
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    @Test
    @DisplayName(value = "Check default value is 0 for request timeout parameter")
    public void unitTest_20180503191716() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        assertThat(api.getResponseReadTimeout(), is(0));
    }

    @Test
    @DisplayName(value = "Check set/get methods for request timeout parameter")
    public void unitTest_20180503191945() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        api.setResponseReadTimeout(123);
        assertThat(api.getResponseReadTimeout(), is(123));
    }

    @Test
    @DisplayName(value = "Check ignore negative value for request timeout parameter")
    public void unitTest_20180503192141() {
        GitlabAPI api = GitlabAPI.connect("http://test.api", "test");
        api.setResponseReadTimeout(-123);
        assertThat(api.getResponseReadTimeout(), is(0));
    }

}
