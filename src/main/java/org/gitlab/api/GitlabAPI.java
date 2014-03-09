package org.gitlab.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.models.GitlabBranch;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabNote;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabProjectHook;
import org.gitlab.api.models.GitlabSession;

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
    
    public static GitlabSession connect(String hostUrl, String username, String password) throws IOException {
    	String tailUrl = GitlabSession.URL;
    	GitlabAPI api = connect(hostUrl, null);
    	return api.dispatch().with("login", username).with("password", password)
    			.to(tailUrl, GitlabSession.class);
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

    public GitlabProject getProject(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId;
        return retrieve().to(tailUrl, GitlabProject.class);
    }

    public List<GitlabProject> getProjects() throws IOException {
        String tailUrl = GitlabProject.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabProject[].class));
    }

    public List<GitlabProject> getAllProjects() throws IOException {
        String tailUrl = GitlabProject.URL;
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

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

    public List<GitlabMergeRequest> getMergeRequests(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabMergeRequest.URL;
        return fetchMergeRequests(tailUrl);
    }

    public List<GitlabMergeRequest> getMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return fetchMergeRequests(tailUrl);
    }

    public List<GitlabMergeRequest> getAllMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public GitlabMergeRequest getMergeRequest(GitlabProject project, Integer mergeRequestId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/merge_request/" + mergeRequestId;
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    public List<GitlabNote> getNotes(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() +
                GitlabNote.URL;

        GitlabNote[] notes = retrieve().to(tailUrl, GitlabNote[].class);
        return Arrays.asList(notes);
    }

    public List<GitlabNote> getAllNotes(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() +
                GitlabNote.URL;
        
        return retrieve().getAll(tailUrl, GitlabNote[].class);

    }

    public List<GitlabCommit> getCommits(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                "/repository" + GitlabCommit.URL + "?ref_name=" + mergeRequest.getSourceBranch();

        GitlabCommit[] commits = retrieve().to(tailUrl, GitlabCommit[].class);
        return Arrays.asList(commits);
    }

    public GitlabNote createNote(GitlabMergeRequest mergeRequest, String body) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() + GitlabNote.URL;

        return dispatch().with("body", body).to(tailUrl, GitlabNote.class);
    }
    
    public List<GitlabBranch> getBranches(GitlabProject project) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL;
        GitlabBranch[] branches = retrieve().to(tailUrl, GitlabBranch[].class);
        return Arrays.asList(branches);
    }
    
    public GitlabBranch getBranch(GitlabProject project, String branchName) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + branchName;
        GitlabBranch branch = retrieve().to(tailUrl, GitlabBranch.class);
        return branch;
    }
    
    public void protectBranch(GitlabProject project, String branchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + branchName + "/protect";
        retrieve().method("PUT").to(tailUrl, Void.class);
    }
    
    public void unprotectBranch(GitlabProject project, String branchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + branchName + "/unprotect";
        retrieve().method("PUT").to(tailUrl, Void.class);
    }
    
    public List<GitlabProjectHook> getProjectHooks(GitlabProject project) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL;
    	GitlabProjectHook[] hooks = retrieve().to(tailUrl, GitlabProjectHook[].class);
        return Arrays.asList(hooks);
    }
    
    public GitlabProjectHook getProjectHook(GitlabProject project, String hookId) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId;
    	GitlabProjectHook hook = retrieve().to(tailUrl, GitlabProjectHook.class);
        return hook;
    }
    
    public GitlabProjectHook addProjectHook(GitlabProject project, String url) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "?url=" + URLEncoder.encode(url, "UTF-8");
    	return dispatch().to(tailUrl, GitlabProjectHook.class);
    }
    
    public GitlabProjectHook editProjectHook(GitlabProject project, String hookId, String url) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId + "?url=" + URLEncoder.encode(url, "UTF-8");
    	return retrieve().method("PUT").to(tailUrl, GitlabProjectHook.class);
    }
    
    public void deleteProjectHook(GitlabProject project, String hookId) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId;
    	retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    private List<GitlabMergeRequest> fetchMergeRequests(String tailUrl) throws IOException {
        GitlabMergeRequest[] mergeRequests = retrieve().to(tailUrl, GitlabMergeRequest[].class);
        return Arrays.asList(mergeRequests);
    }
    
    public List<GitlabIssue> getIssues(GitlabProject project) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabIssue.URL;
    	return retrieve().getAll(tailUrl, GitlabIssue[].class);
    }
    
    public GitlabIssue getIssue(Integer projectId, Integer issueId) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL + "/" + issueId;
    	return retrieve().to(tailUrl, GitlabIssue.class);
    }
    
    public GitlabIssue createIssue(int projectId, int assigneeId, int milestoneId, String labels, 
    		String description, String title) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL;
    	GitlabHTTPRequestor requestor = dispatch().with("title", title)
    			.with("description", description)
    			.with("labels", labels)
    			.with("assignee_id", assigneeId)
    			.with("milestone_id", milestoneId);

    	return requestor.to(tailUrl, GitlabIssue.class);
    }
    
    public GitlabIssue editIssue(int projectId, int issueId, int assigneeId, int milestoneId, String labels,
    		String description, String title, GitlabIssue.Action action) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL + "/" + issueId;
    	GitlabHTTPRequestor requestor = retrieve().method("PUT").with("title", title)
    			.with("description", description)
    			.with("labels", labels)
    			.with("assignee_id", assigneeId)
    			.with("milestone_id", milestoneId);
    	
    	if(action != GitlabIssue.Action.LEAVE) {
    			requestor.with("state_event", action.toString().toLowerCase());
    	}
    	
    	return requestor.to(tailUrl, GitlabIssue.class);
    }
    
    public List<GitlabNote> getNotes(GitlabIssue issue) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" 
    			+ issue.getId() + GitlabNote.URL;
    	return Arrays.asList(retrieve().to(tailUrl, GitlabNote[].class));
    }
    
    public GitlabNote createNote(Integer projectId, Integer issueId, String message) throws IOException {
    	String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL
    			+ "/" + issueId + GitlabNote.URL;
    	return dispatch().with("body", message).to(tailUrl, GitlabNote.class);
    }
    
    public GitlabNote createNote(GitlabIssue issue, String message) throws IOException {
    	return createNote(issue.getProjectId(), issue.getId(), message);
    }
}
