package org.gitlab.api;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class GitlabAPITest {

    GitlabAPI _api;

    @Before
    public void setup() {
        _api = GitlabAPI.connect("http://localhost", "token");
    }

    @Test
    public void testConnect() throws IOException {
        assertEquals(GitlabAPI.class, _api.getClass());
    }

    @Test
    public void testGetAPIUrl() throws IOException {
        URL expected = new URL("http://localhost/api/v3/?private_token=token");
        assertEquals(expected, _api.getAPIUrl(""));
    }

    @Test
    public void testGetUrl() throws IOException {
        URL expected = new URL("http://localhost/");
        assertEquals(expected, _api.getUrl(""));
    }
}
