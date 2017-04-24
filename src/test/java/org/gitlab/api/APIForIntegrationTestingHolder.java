package org.gitlab.api;


import org.gitlab.api.models.GitlabSession;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;

import java.io.IOException;

/**
 * Token holder for integration tests.
 * <ul>
 *    <li>By throwing an {@link AssumptionViolatedException} when the host is not reachable,
 *   provoke skipping of tests.</li>
 *   <li>GitLab is strict about creating too much sessions for username/password.
 *   If you create too many sessions, you will receive HTTP/429 (Retry Later).</li>
 * </ul>
 */
public class APIForIntegrationTestingHolder {

    private static final String TEST_URL = "http://" + System.getProperty("docker.host.address", "localhost") + ":" + System.getProperty("gitlab.port", "18080");

    public static APIForIntegrationTestingHolder INSTANCE = new APIForIntegrationTestingHolder();

    private final GitlabAPI api;

    private APIForIntegrationTestingHolder(){
        final GitlabSession session;
        try {
            session = GitlabAPI.connect(TEST_URL, "root", "password");
            String privateToken = session.getPrivateToken();
            api = GitlabAPI.connect(TEST_URL, privateToken);
        } catch (IOException e) {
            throw new AssumptionViolatedException("GITLAB not running on '" + TEST_URL + "', skipping...", e);
        }
    }

    public GitlabAPI getApi() {
        return api;
    }
}
