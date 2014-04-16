package org.gitlab.api;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GitlabAPITest {

    GitlabAPI _api;

    @Before
    public void setup() {
        _api = GitlabAPI.connect("http://192.168.1.104:9002/", "token");
    }

    @Test
    public void testName() throws Exception {
        System.out.println(_api.getUser("00sj6za4pq"));
    }

    @Test
    public void testGetProject() throws IOException {
        assertNotNull(_api.getProject("root%2F00ic94qju1"));
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
