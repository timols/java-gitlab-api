package org.gitlab.api;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class GitlabAPITest {

    GitlabAPI _api;

    @Before
    public void setup() {
        _api = GitlabAPI.connect("http://192.168.1.104:9002/", "cULf7pfeELg4uMPRNqFV");
    }

    @Test
    public void testName() throws Exception {
        System.out.println(_api.getUser("00sj6za4pq"));
    }

    @Test
    public void testDeleteProject() throws Exception {
        _api.deleteProject("root/aobl0oxwqj");
    }

    @Test
    public void testDeleteUser() throws Exception {
        _api.deleteUser("okokokokok");
    }

    @Test
    public void testCreateProject() throws Exception {
        GitlabProject project = new GitlabProject();
        project.setName("okokokokoko");
        assertNotNull(_api.createProject(project).getId());
    }

    @Test
    public void testCreateUser() throws Exception {
        GitlabUser user = new GitlabUser();
        user.setName("okokokok");
        user.setEmail("okokok@173.com");
        user.setUsername("okokokokok");
        assertNotNull(_api.createUser(user, "password").getId());


    }

    @Test
    public void testGetProject() throws IOException {
        assertNotNull(_api.getProject("root/aobl0oxwqj"));
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
