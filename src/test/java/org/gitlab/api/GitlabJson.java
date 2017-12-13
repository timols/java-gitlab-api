package org.gitlab.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.gitlab.api.models.GitlabProject;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitlabJson {

    static GitlabAPI api;

    @BeforeClass
    public static void getApi() {
        api = APIForIntegrationTestingHolder.INSTANCE.getApi();
    }

    @Test
    public void getProjectJson () throws IOException {
        List<GitlabProject> projects = api.getProjects();
        int randomProjectNumber = getRandomProject(projects);
        if (randomProjectNumber != 0) {
            String p = api.getProjectJson(randomProjectNumber);
            assertTrue("The JSON is 0 length",p.length() > 0);
            assertTrue("Project JSON does not contain 'id'.",p.indexOf("id") > 0);
            assertTrue("Project JSON does not contain 'default_branch'.",p.indexOf("default_branch") > 0);
        } else {
            fail("No projects are defined in the gitlab instance, or something failed.");
        }
    }

    private int getRandomProject (List<GitlabProject> projects) {
        if (projects.size() > 0) {
            Random rand = new Random();
            return projects.get(rand.nextInt(projects.size())).getId();
        } else
            return 0;
    }
}
