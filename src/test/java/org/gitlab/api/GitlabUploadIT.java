package org.gitlab.api;

import org.apache.commons.io.IOUtils;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUpload;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

public class GitlabUploadIT {

    static GitlabAPI api;

    @BeforeClass
    public static void getApi() {
        api = APIForIntegrationTestingHolder.INSTANCE.getApi();
    }


    @Test
    public void testUploadToProject() throws Exception {
        GitlabProject project;
        try {
            project = api.getProject("root", "project");
        } catch (FileNotFoundException e) {
            project = api.createUserProject(api.getUser().getId(), "project");
        }
        String content = "test file content";
        File tempFile = createTempFile(content);
        try {
            GitlabUpload upload = api.uploadFile(project, tempFile);
            Assert.assertNotNull(upload.getUrl());
        } finally {
            tempFile.delete();
        }
    }

    private File createTempFile(String content) throws IOException {
        File tempFile = File.createTempFile("upload-", ".txt");
        InputStream is = new ByteArrayInputStream(content.getBytes());
        OutputStream os = new FileOutputStream(tempFile);
        try {
            IOUtils.copy(is, os);
        } finally {
            is.close();
            os.close();
        }
        return tempFile;
    }
}
