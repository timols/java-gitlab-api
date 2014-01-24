package org.gitlab.api;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabCommitDiff;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabNote;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Gitlab API Wrapper class
 *
 * @author @timols
 */
public class GitlabAPI {
    private final String _hostUrl;
    private final String _apiToken;
    private boolean _ignoreCertificateErrors = false;
    private static final String API_NAMESPACE = "/api/v3";
    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private GitlabAPI(String hostUrl, String apiToken) {
        _hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        _apiToken = apiToken;
    }

    public static GitlabAPI connect(String hostUrl, String apiToken) {
        return new GitlabAPI(hostUrl, apiToken);
    }

    public GitlabAPI ignoreCertificateErrors(boolean ignoreCertificateErrors) {
        _ignoreCertificateErrors = ignoreCertificateErrors;
        return this;
    }

    public GitlabHTTPRequestor retrieve() {
        return new GitlabHTTPRequestor(this);
    }

    public GitlabHTTPRequestor dispatch() {
        return new GitlabHTTPRequestor(this).method("POST");
    }

    public boolean isIgnoreCertificateErrors() {
        return _ignoreCertificateErrors;
    }

    public URL getAPIUrl(String tailAPIUrl) throws IOException {
        if (_apiToken != null) {
            tailAPIUrl = tailAPIUrl + (tailAPIUrl.indexOf('?') > 0 ? '&' : '?') + "private_token=" + _apiToken;
        }

        if (!tailAPIUrl.startsWith("/")) {
            tailAPIUrl = "/" + tailAPIUrl;
        }
        return new URL(_hostUrl + API_NAMESPACE + tailAPIUrl);
    }

    public URL getUrl(String tailAPIUrl) throws IOException {
        if (!tailAPIUrl.startsWith("/")) {
            tailAPIUrl = "/" + tailAPIUrl;
        }

        return new URL(_hostUrl + tailAPIUrl);
    }

    private List<GitlabMergeRequest> fetchMergeRequests(String tailUrl) throws IOException {
        GitlabMergeRequest[] mergeRequests = retrieve().to(tailUrl, GitlabMergeRequest[].class);
        return Arrays.asList(mergeRequests);
    }

    /*
    Project APIs
    http://api.gitlab.org/projects.html
    */

    // Get single project
    // GET /projects/:id
    public GitlabProject getProject(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId;
        return retrieve().to(tailUrl, GitlabProject.class);
    }

    // List projects
    // GET /projects
    public List<GitlabProject> getProjects() throws IOException {
        String tailUrl = GitlabProject.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabProject[].class));
    }

    // List all projects
    // GET /projects
    public List<GitlabProject> getAllProjects() throws IOException {
        String tailUrl = GitlabProject.URL;
        List<GitlabProject> results = new ArrayList<GitlabProject>();
        Iterator<GitlabProject[]> iterator = retrieve().asIterator(tailUrl, GitlabProject[].class);

        while (iterator.hasNext()) {
            GitlabProject[] projects = iterator.next();

            if (projects.length > 0) {
                results.addAll(Arrays.asList(projects));
            }
        }

        return results;

    }

    /*
    Merge Request APIs
    http://api.gitlab.org/merge_requests.html
    */

    // List all open merge requests for a project id
    // GET /projects/:id/merge_requests
    public List<GitlabMergeRequest> getOpenMergeRequests(GitlabProject project) throws IOException {
        List<GitlabMergeRequest> allMergeRequests = getAllMergeRequests(project);
        List<GitlabMergeRequest> openMergeRequests = new ArrayList<GitlabMergeRequest>();

        for (GitlabMergeRequest mergeRequest : allMergeRequests) {
            if (mergeRequest.isMerged() || mergeRequest.isClosed()) {
                continue;
            }
            openMergeRequests.add(mergeRequest);
        }

        return openMergeRequests;
    }

    // List merge requests for a project id
    // GET /projects/:id/merge_requests
    public List<GitlabMergeRequest> getMergeRequests(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabMergeRequest.URL;
        return fetchMergeRequests(tailUrl);
    }

    // List merge requests for a project
    // GET /projects/:id/merge_requests
    public List<GitlabMergeRequest> getMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return fetchMergeRequests(tailUrl);
    }

    // List all merge requests for a project
    // GET /projects/:id/merge_requests
    public List<GitlabMergeRequest> getAllMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        List<GitlabMergeRequest> results = new ArrayList<GitlabMergeRequest>();
        Iterator<GitlabMergeRequest[]> iterator = retrieve().asIterator(tailUrl, GitlabMergeRequest[].class);

        while (iterator.hasNext()) {
            GitlabMergeRequest[] requests = iterator.next();

            if (requests.length > 0) {
                results.addAll(Arrays.asList(requests));
            }
        }

        return results;
    }

    // Get single merge request
    // GET /projects/:id/merge_request/:merge_request_id
    public GitlabMergeRequest getMergeRequest(GitlabProject project, Integer mergeRequestId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/merge_request/" + mergeRequestId;
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    /*
    Notes API
    http://api.gitlab.org/notes.html
    */

    // List merge request notes
    // GET /projects/:id/merge_requests/:merge_request_id/notes
    public List<GitlabNote> getNotes(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() +
                GitlabNote.URL;

        GitlabNote[] notes = retrieve().to(tailUrl, GitlabNote[].class);
        return Arrays.asList(notes);
    }

    // List all merge request notes
    // GET /projects/:id/merge_requests/:merge_request_id/notes
    public List<GitlabNote> getAllNotes(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() +
                GitlabNote.URL;

        List<GitlabNote> results = new ArrayList<GitlabNote>();
        Iterator<GitlabNote[]> iterator = retrieve().asIterator(tailUrl, GitlabNote[].class);

        while (iterator.hasNext()) {
            GitlabNote[] projects = iterator.next();

            if (projects.length > 0) {
                results.addAll(Arrays.asList(projects));
            }
        }

        return results;
    }

    // Create new merge request note
    // POST /projects/:id/merge_requests/:merge_request_id/notes
    public GitlabNote createNote(GitlabMergeRequest mergeRequest, String body) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() + GitlabNote.URL;

        return dispatch().with("body", body).to(tailUrl, GitlabNote.class);
    }

    /*
    Repository APIs
    http://api.gitlab.org/repositories.html
    */

    // Get a specific commit identified by the commit hash or name of a branch or tag
    // GET /projects/:id/repository/commits/:sha
    public GitlabCommit getCommit(String projectId, String commitHash) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + "/repository/commits/" + commitHash;
        GitlabCommit commit = retrieve().to(tailUrl, GitlabCommit.class);
        return commit;
    }

    // List repository commits for a merge request
    // GET /projects/:id/repository/commits
    public List<GitlabCommit> getCommits(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                "/repository" + GitlabCommit.URL + "?ref_name=" + mergeRequest.getSourceBranch();

        GitlabCommit[] commits = retrieve().to(tailUrl, GitlabCommit[].class);
        return Arrays.asList(commits);
    }

    // List commit diffs for a project ID and commit hash
    // GET /projects/:id/repository/commits/:sha/diff
    public List<GitlabCommitDiff> getCommitDiffs(String projectId, String commitHash) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + "/repository/commits/" + commitHash + GitlabCommitDiff.URL;
        GitlabCommitDiff[] diffs = retrieve().to(tailUrl, GitlabCommitDiff[].class);
        return Arrays.asList(diffs);
    }

    /*
    User APIs
    http://api.gitlab.org/users.html
    */

    // List of users
    // GET /users
    public List<GitlabUser> getUsers() throws IOException {
        String tailUrl = GitlabUser.URL;

        GitlabUser[] users = retrieve().to(tailUrl, GitlabUser[].class);
        return Arrays.asList(users);
    }

    // List all users
    // GET /users
    public List<GitlabUser> getAllUsers() throws IOException {
        String tailUrl = GitlabUser.URL;

        List<GitlabUser> results = new ArrayList<GitlabUser>();
        Iterator<GitlabUser[]> iterator = retrieve().asIterator(tailUrl, GitlabUser[].class);

        while (iterator.hasNext()) {
            GitlabUser[] users = iterator.next();

            if (users.length > 0) {
                results.addAll(Arrays.asList(users));
            }
        }

        return results;
    }

    // Gets currently authenticated user
    // GET /user
    public GitlabUser getLoggedInUser() throws IOException {
        String tailUrl = "/user";
        GitlabUser user = retrieve().to(tailUrl, GitlabUser.class);
        return user;
    }
}
