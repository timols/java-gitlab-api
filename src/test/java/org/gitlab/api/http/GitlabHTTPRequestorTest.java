package org.gitlab.api.http;

import org.gitlab.api.GitlabAPI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GitlabHTTPRequestorTest {

    @Test
    public void testSettingInvalidHTTPMethod() {
        GitlabHTTPRequestor http = new GitlabHTTPRequestor(GitlabAPI.connect("localhost", "api"));
        try {
            http.method("WRONG METHOD");
        } catch (Exception e) {
            assertEquals("Invalid HTTP Method: WRONG METHOD. Must be one of GET, PUT, POST, PATCH, DELETE, HEAD, OPTIONS, TRACE", e.getMessage());
        }
    }

}
