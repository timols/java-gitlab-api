package org.gitlab.api;

import org.gitlab.api.models.GitlabUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class GitlabAPITest {

    GitlabAPI _api;
    
    private static final String TEST_URL = "http://localhost";
    private static final String TEST_TOKEN = "y0E5b9761b7y4qk";
    
	String rand = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	
	

    @Before
    public void setup() {
        _api = GitlabAPI.connect(TEST_URL, TEST_TOKEN);
    }

    @Test
    public void testConnect() throws IOException {
        assertEquals(GitlabAPI.class, _api.getClass());
    }

    @Test
    public void testGetAPIUrl() throws IOException {
        URL expected = new URL(TEST_URL+"/api/v3/?private_token="+TEST_TOKEN);
        assertEquals(expected, _api.getAPIUrl(""));
    }

    @Test
    public void testGetUrl() throws IOException {
        URL expected = new URL(TEST_URL);
        assertEquals(expected +"/", _api.getUrl("").toString());
    }
    
    @Test 
    public void testCreateUpdateDeleteUser() throws IOException {
    	
    	String password = randVal("$%password");
    	

    	GitlabUser gitUser = _api.createUser(randVal("testEmail@gitlabapitest.com"), 
					    					password, 
					    					randVal("userName"), 
					    					randVal("fullName"), 
					    					randVal("skypeId"), 
					    					randVal("linledin"), 
					    					randVal("twitter"), 
					    					"http://"+randVal("url.com"), 
					    					10, 
					    					randVal("externuid"), 
					    					randVal("externprovidername"), 
					    					randVal("bio"), 
					    					false, 
					    					false,
					    					false);
    	Assert.assertNotNull(gitUser);
    	
    	GitlabUser refetched = _api.getUserViaSudo(gitUser.getUsername());
    	
    	Assert.assertNotNull(refetched);
    	
    	Assert.assertEquals(refetched.getUsername(),gitUser.getUsername());
    	
    	_api.updateUser(gitUser.getId(), gitUser.getEmail(), password , gitUser.getUsername(), 
    			gitUser.getName(), "newSkypeId", gitUser.getLinkedin(), gitUser.getTwitter(), gitUser.getWebsiteUrl(), 
    			10 /* project limit does not come back on GET */, gitUser.getExternUid(), gitUser.getExternProviderName(),
    			gitUser.getBio(), gitUser.isAdmin(), gitUser.isCanCreateGroup(), false);
    	
    	
    	GitlabUser postUpdate = _api.getUserViaSudo(gitUser.getUsername());
    	
    	
    	Assert.assertNotNull(postUpdate);
    	Assert.assertEquals(postUpdate.getSkype(),"newSkypeId");
    	
    	
    	_api.deleteUser(postUpdate.getId());
    	
    	// expect a 404, but we have no access to it
    	try {
    		GitlabUser shouldNotExist = _api.getUser(postUpdate.getId());
    		Assert.assertNull(shouldNotExist); // should never even get here
    		
    	} catch(FileNotFoundException thisIsSoOddForAnRESTApiClient) {
    		Assert.assertTrue(true); // expected
    	}
    	
    	
    }
    
    private String randVal(String postfix) {
    	return rand + "-" + postfix;
    }
}
