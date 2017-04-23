package org.gitlab.api;

import org.gitlab.api.models.GitlabBuildVariable;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabUser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

@Ignore
public class GitlabAPITest {

    GitlabAPI api;

    private static final String TEST_URL = System.getProperty("TEST_URL", "http://localhost");
    private static final String TEST_TOKEN = System.getProperty("TEST_TOKEN", "y0E5b9761b7y4qk");

    String rand = UUID.randomUUID().toString().replace("-", "").substring(0, 8);


    @Before
    public void setup() throws IOException {
        api = GitlabAPI.connect(TEST_URL, TEST_TOKEN);
        try {
            api.dispatch().with("login", "INVALID").with("password", rand).to("session", GitlabUser.class);
        } catch (ConnectException e) {
            assumeNoException("GITLAB not running on '" + TEST_URL + "', skipping...", e);
        } catch (GitlabAPIException e) {
            final String message = e.getMessage();
            if (!message.equals("{\"message\":\"401 Unauthorized\"}")) {
                throw new AssertionError("Expected an unauthorized message", e);
            } else if(e.getResponseCode() != 401) {
                throw new AssertionError("Expected 401 code", e);
            }
        }
    }

    @Test
    public void testAllProjects() throws IOException {
        api.getProjects();
    }

    @Test
    public void testConnect() throws IOException {
        assertEquals(GitlabAPI.class, api.getClass());
    }

    @Test
    public void testGetAPIUrl() throws IOException {
        URL expected = new URL(TEST_URL + "/api/v3/");
        assertEquals(expected, api.getAPIUrl(""));
    }

    @Test
    public void testGetUrl() throws IOException {
        URL expected = new URL(TEST_URL);
        assertEquals(expected + "/", api.getUrl("").toString());
    }

    @Test
    public void testCreateUpdateDeleteVariable() throws IOException {
        String key = randVal("key");
        String value = randVal("value");
        String newValue = randVal("new_value");
        String projectName = randVal("project");

        GitlabProject project = api.createProject(projectName);
        assertNotNull(project);

        GitlabBuildVariable variable = api.createBuildVariable(project.getId(), key, value);
        assertNotNull(variable);

        GitlabBuildVariable refetched = api.getBuildVariable(project.getId(), key);

        assertNotNull(refetched);

        assertEquals(refetched.getKey(), variable.getKey());
        assertEquals(refetched.getValue(), variable.getValue());

        api.updateBuildVariable(project.getId(), key, newValue);


        GitlabBuildVariable postUpdate = api.getBuildVariable(project.getId(), key);


        assertNotNull(postUpdate);
        assertEquals(postUpdate.getKey(), variable.getKey());
        assertNotEquals(postUpdate.getValue(), variable.getValue());
        assertEquals(postUpdate.getValue(), newValue);


        api.deleteBuildVariable(project.getId(), key);

        // expect a 404, but we have no access to it
        try {
            GitlabBuildVariable shouldNotExist = api.getBuildVariable(project.getId(), key);
            assertNull(shouldNotExist);
        } catch (FileNotFoundException thisIsSoOddForAnRESTApiClient) {
            assertTrue(true); // expected
        }

        api.deleteProject(project.getId());
    }

    @Test
    public void testCreateUpdateDeleteUser() throws IOException {

        String password = randVal("$%password");


        GitlabUser gitUser = api.createUser(randVal("testEmail@gitlabapitest.com"),
                password,
                randVal("userName"),
                randVal("fullName"),
                randVal("skypeId"),
                randVal("linkedin"),
                randVal("twitter"),
                "http://" + randVal("url.com"),
                10,
                randVal("externuid"),
                randVal("externprovidername"),
                randVal("bio"),
                false,
                false,
                false);
        assertNotNull(gitUser);

        GitlabUser refetched = api.getUserViaSudo(gitUser.getUsername());

        assertNotNull(refetched);

        assertEquals(refetched.getUsername(), gitUser.getUsername());

        api.updateUser(gitUser.getId(), gitUser.getEmail(), password, gitUser.getUsername(),
                gitUser.getName(), "newSkypeId", gitUser.getLinkedin(), gitUser.getTwitter(), gitUser.getWebsiteUrl(),
                10 /* project limit does not come back on GET */, gitUser.getExternUid(), gitUser.getExternProviderName(),
                gitUser.getBio(), gitUser.isAdmin(), gitUser.isCanCreateGroup());


        GitlabUser postUpdate = api.getUserViaSudo(gitUser.getUsername());


        assertNotNull(postUpdate);
        assertEquals(postUpdate.getSkype(), "newSkypeId");


        api.deleteUser(postUpdate.getId());

        // expect a 404, but we have no access to it
        try {
            GitlabUser shouldNotExist = api.getUser(postUpdate.getId());
            assertNull(shouldNotExist);
        } catch (FileNotFoundException thisIsSoOddForAnRESTApiClient) {
            assertTrue(true); // expected
        }


    }

    @Test
    public void testGetGroupByPath() throws IOException {
        // Given
        String name = "groupName";
        String path = "groupPath";

        GitlabGroup originalGroup = api.createGroup(name, path);

        // When
        GitlabGroup group = api.getGroup(path);

        // Then:
        assertNotNull(group);
        assertEquals(originalGroup.getId(), group.getId());
        assertEquals(originalGroup.getName(), group.getName());
        assertEquals(originalGroup.getPath(), group.getPath());

        // Cleanup
        api.deleteGroup(group.getId());
    }

    private String randVal(String postfix) {
        return rand + "_" + postfix;
    }
}
