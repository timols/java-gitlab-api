package org.gitlab.api;

import org.gitlab.api.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class GitlabAPIIT {

    private static GitlabAPI api;

    private static final String TEST_URL = "http://" + System.getProperty("docker.host.address", "localhost") +
            ":" + System.getProperty("gitlab.port", "18080");

    @BeforeClass
    public static void getApi() {
        api = APIForIntegrationTestingHolder.INSTANCE.getApi();
    }

    @Test
    public void checkInvalidCredentials() throws Exception {
        try {
            api.dispatch().with("login", "INVALID").with("password", createRandomString())
                    .to("session", GitlabUser.class);
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
    public void testAllProjects() {
        assertThat(api.getAllProjects(), is(notNullValue()));
    }

    @Test
    public void testGetAPIUrl() throws Exception {
        URL expected = new URL(TEST_URL + "/api/v4/");
        assertEquals(expected, api.getAPIUrl(""));
    }

    @Test
    public void testGetUrl() throws Exception {
        URL expected = new URL(TEST_URL);
        assertEquals(expected + "/", api.getUrl("").toString());
    }

    @Test
    public void testCreateUpdateDeleteVariable() throws Exception {
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
    public void testCreateUpdateDeleteUser() throws Exception {
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
                true,
                false);
        assertNotNull(gitUser);

        GitlabUser refetched = api.getUserViaSudo(gitUser.getUsername());

        assertNotNull(refetched);
        assertEquals(refetched.getUsername(), gitUser.getUsername());

        api.updateUser(gitUser.getId(), gitUser.getEmail(), password, gitUser.getUsername(),
                gitUser.getName(), "newSkypeId", gitUser.getLinkedin(),
                gitUser.getTwitter(), gitUser.getWebsiteUrl(),
                10 /* project limit does not come back on GET */,
                gitUser.getExternUid(), gitUser.getExternProviderName(),
                gitUser.getBio(), gitUser.isAdmin(), gitUser.isCanCreateGroup(), gitUser.isExternal());

        GitlabUser postUpdate = api.getUserViaSudo(gitUser.getUsername());

        assertNotNull(postUpdate);
        assertEquals(postUpdate.getSkype(), "newSkypeId");

        // block
        api.blockUser(refetched.getId());
        api.unblockUser(refetched.getId());

        api.deleteUser(postUpdate.getId());
        // This is odd, but it seems the user is deleted asynchronously...
        Thread.sleep(1000);
        // expect a 404, but we have no access to it
        try {
            GitlabUser shouldNotExist = api.getUser(postUpdate.getId());
            assertNull(shouldNotExist);
        } catch (FileNotFoundException thisIsSoOddForAnRESTApiClient) {
            assertTrue(true); // expected
        }
    }

    @Test
    public void testGetGroupByPath() throws Exception {
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
	
	@Test
    public void testCreateAndUpdateGroup() throws Exception {
        // Given
        GitlabGroup originalGroup = new GitlabGroup();
        originalGroup.setDescription("test description");
        originalGroup.setName("groupNameTest");
        originalGroup.setPath("groupPathTest");
        originalGroup.setVisibility(GitlabVisibility.INTERNAL);

        GitlabGroup newGroup = api.createGroup(originalGroup, null);
        assertNotNull(newGroup);
        assertEquals(originalGroup.getId(), newGroup.getId());
        assertEquals(originalGroup.getName(), newGroup.getName());
        assertEquals(originalGroup.getPath(), newGroup.getPath());
        assertEquals(originalGroup.getDescription(), newGroup.getDescription());
        assertEquals(originalGroup.getVisibility(), newGroup.getVisibility());

        GitlabGroup groupToUpdate = new GitlabGroup();
        groupToUpdate.setId(newGroup.getId());
        groupToUpdate.setVisibility(GitlabVisibility.PRIVATE);

        // When
        GitlabGroup updatedGroup = api.updateGroup(newGroup, null);

        // Then:
        assertNotNull(updatedGroup);
        assertEquals(groupToUpdate.getVisibility(), updatedGroup.getVisibility());

        // Cleanup
        api.deleteGroup(updatedGroup.getId());
    }

    @Test
    public void testGetMembershipProjects() throws Exception {
        assertThat(api.getMembershipProjects(), not(nullValue()));
    }

    @Test
    public void checkGetOwnedProjects() throws Exception {
        assertThat(api.getOwnedProjects(), not(nullValue()));
    }

    @Test
    public void checkSearchProjects() throws Exception {
        final List<GitlabProject> searchedProjects = api.searchProjects("foo");
        assertThat(searchedProjects, not(nullValue()));
        assertThat(searchedProjects.isEmpty(), is(true));
    }

    /**
     * There is at least one namespace for the user
     */
    @Test
    public void testGetNamespace() {
        final List<GitlabNamespace> gitlabNamespaces = api.getNamespaces();
        assertThat(gitlabNamespaces, not(nullValue()));
        assertThat(gitlabNamespaces.isEmpty(), is(false));
    }

    @Test
    public void testCreateDeleteFork() throws Exception {
        String projectName = randVal("Fork-me");
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
                false,
                false);

        String namespace = api.getNamespaces().get(0).getPath();

        GitlabProject project = api.createUserProject(gitUser.getId(), projectName);
        GitlabProject fork = api.createFork(namespace, project);

        assertNotNull(fork);
        assertEquals(project.getId(), fork.getForkedFrom().getId());
        assertEquals(project.getNamespace(), namespace);

        api.deleteProject(project.getId());
        api.deleteProject(fork.getId());
        api.deleteUser(gitUser.getId());
    }

    private String randVal(String postfix) {
        return createRandomString() + "_" + postfix;
    }

    private static String createRandomString() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
