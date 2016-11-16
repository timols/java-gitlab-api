package org.gitlab.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUpload;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class GitlabUploadTest {

	private static final String TEST_URL = System.getProperty("TEST_URL", "http://localhost");
    private static final String TEST_TOKEN = System.getProperty("TEST_TOKEN", "y0E5b9761b7y4qk");
    private static final String TEST_PROJECT = System.getProperty("TEST_PROJECT", "user/project");

    @Test
    public void testUploadToProject() throws Exception {
    	GitlabAPI api = GitlabAPI.connect(TEST_URL, TEST_TOKEN);
    	String content = "test file content";
    	File tempFile = createTempFile(content);
    	try{
    		GitlabUpload upload = api.uploadFile(gitlabProject(api), tempFile);
    		Assert.assertNotNull(upload.getUrl());
    	}finally{
    		tempFile.delete();
    	}
    }
    
    private File createTempFile(String content) throws IOException{
    	File tempFile = File.createTempFile("upload-", ".txt");
    	InputStream is = new ByteArrayInputStream(content.getBytes());
    	OutputStream os = new FileOutputStream(tempFile);
    	try{
    		IOUtils.copy(is, os);
    	}finally{
    		is.close();
    		os.close();
    	}
    	return tempFile;
    }

    private GitlabProject gitlabProject(GitlabAPI api) throws IOException{
    	for(GitlabProject gitlabProject : api.getProjects()){
    		String projetPath = String.format("%s/%s", gitlabProject.getNamespace().getPath(), gitlabProject.getPath());
    		if(projetPath.equals(TEST_PROJECT)){
    			return gitlabProject;
    		}
    	}
    	throw new IllegalStateException();
    }
    
}
