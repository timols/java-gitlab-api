package org.gitlab.api;

import org.gitlab.api.models.GitlabUser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;

public class GitlabAPIT {

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
        } catch (IOException e) {
            final String message = e.getMessage();
            if (!message.equals("{\"message\":\"401 Unauthorized\"}")) {
                throw new AssertionError("Expected an unauthorized message", e);
            }
        }
    }

    @Test
    public void testConnect() throws IOException {
        assertEquals(GitlabAPI.class, api.getClass());
    }

    @Test
    public void testGetAPIUrl() throws IOException {
        URL expected = new URL(TEST_URL + "/api/v3/?private_token=" + TEST_TOKEN);
        assertEquals(expected, api.getAPIUrl(""));
    }

    @Test
    public void testGetUrl() throws IOException {
        URL expected = new URL(TEST_URL);
        assertEquals(expected + "/", api.getUrl("").toString());
    }

    @Test
    public void testCreateUpdateDeleteUser() throws IOException {

        String password = randVal("$%password");


        GitlabUser gitUser = api.createUser(randVal("testEmail@gitlabapitest.com"),
                password,
                randVal("userName"),
                randVal("fullName"),
                randVal("skypeId"),
                randVal("linledin"),
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
                gitUser.getBio(), gitUser.isAdmin(), gitUser.isCanCreateGroup(), false);


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

    private String randVal(String postfix) {
        return rand + "-" + postfix;
    }
}
