package org.gitlab.api;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.http.Query;
import org.gitlab.api.models.*;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Gitlab API Wrapper class
 *
 * @author @timols
 */
public class GitlabAPI {

    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String API_NAMESPACE = "/api/v3";
    private static final String PARAM_SUDO = "sudo";

    private final String hostUrl;

    private final String apiToken;
    private boolean ignoreCertificateErrors = false;

    private GitlabAPI(String hostUrl, String apiToken) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
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
        this.ignoreCertificateErrors = ignoreCertificateErrors;
        return this;
    }

    public GitlabHTTPRequestor retrieve() {
        return new GitlabHTTPRequestor(this);
    }

    public GitlabHTTPRequestor dispatch() {
        return new GitlabHTTPRequestor(this).method("POST");
    }

    public boolean isIgnoreCertificateErrors() {
        return ignoreCertificateErrors;
    }

    public URL getAPIUrl(String tailAPIUrl) throws IOException {
        if (apiToken != null) {
            tailAPIUrl = tailAPIUrl + (tailAPIUrl.indexOf('?') > 0 ? '&' : '?') + "private_token=" + apiToken;
        }

        if (!tailAPIUrl.startsWith("/")) {
            tailAPIUrl = "/" + tailAPIUrl;
        }
        return new URL(hostUrl + API_NAMESPACE + tailAPIUrl);
    }

    public URL getUrl(String tailAPIUrl) throws IOException {
        if (!tailAPIUrl.startsWith("/")) {
            tailAPIUrl = "/" + tailAPIUrl;
        }

        return new URL(hostUrl + tailAPIUrl);
    }

    public List<GitlabUser> getUsers() throws IOException {
        String tailUrl = GitlabUser.URL;
        return retrieve().getAll(tailUrl, GitlabUser[].class);
    }

    // Search users by Email or username
    // GET /users?search=:email_or_username
    public List<GitlabUser> findUsers(String emailOrUsername) throws IOException {
        String tailUrl = GitlabUser.URL + "?search=" + emailOrUsername;
        GitlabUser[] users = retrieve().to(tailUrl, GitlabUser[].class);
        return Arrays.asList(users);
    }

    public GitlabUser getUser(Integer userId) throws IOException {
        String tailUrl = GitlabUser.URL + "/" + userId;
        return retrieve().to(tailUrl, GitlabUser.class);
    }

    public GitlabUser getUserViaSudo(String username) throws IOException {
        String tailUrl = GitlabUser.USER_URL + "?" + PARAM_SUDO + "=" + username;
        return retrieve().to(tailUrl, GitlabUser.class);
    }

    /**
     * Create a new User
     *
     * @param email
     * @param password
     * @param username
     * @param fullName
     * @param skypeId
     * @param linkedIn
     * @param twitter
     * @param website_url
     * @param projects_limit
     * @param extern_uid
     * @param extern_provider_name
     * @param bio
     * @param isAdmin
     * @param can_create_group
     * @param skip_confirmation
     * @return
     * @throws IOException
     * @see http://doc.gitlab.com/ce/api/users.html
     */
    public GitlabUser createUser(String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group,
                                 Boolean skip_confirmation) throws IOException {

        Query query = new Query()
                .append("email", email)
                .appendIf("skip_confirmation", skip_confirmation)
                .appendIf("password", password)
                .appendIf("username", username)
                .appendIf("name", fullName)
                .appendIf("skype", skypeId)
                .appendIf("linkedin", linkedIn)
                .appendIf("twitter", twitter)
                .appendIf("website_url", website_url)
                .appendIf("projects_limit", projects_limit)
                .appendIf("extern_uid", extern_uid)
                .appendIf("provider", extern_provider_name)
                .appendIf("bio", bio)
                .appendIf("admin", isAdmin)
                .appendIf("can_create_group", can_create_group);

        String tailUrl = GitlabUser.USERS_URL + query.toString();

        return dispatch().to(tailUrl, GitlabUser.class);
    }


    /**
     * Update a user
     *
     * @param targetUserId
     * @param email
     * @param password
     * @param username
     * @param fullName
     * @param skypeId
     * @param linkedIn
     * @param twitter
     * @param website_url
     * @param projects_limit
     * @param extern_uid
     * @param extern_provider_name
     * @param bio
     * @param isAdmin
     * @param can_create_group
     * @param skip_confirmation
     * @return
     * @throws IOException
     */
    public GitlabUser updateUser(Integer targetUserId,
                                 String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group,
                                 Boolean skip_confirmation) throws IOException {

        Query query = new Query()
                .append("email", email)
                .appendIf("skip_confirmation", skip_confirmation)
                .appendIf("password", password)
                .appendIf("username", username)
                .appendIf("name", fullName)
                .appendIf("skype", skypeId)
                .appendIf("linkedin", linkedIn)
                .appendIf("twitter", twitter)
                .appendIf("website_url", website_url)
                .appendIf("projects_limit", projects_limit)
                .appendIf("extern_uid", extern_uid)
                .appendIf("provider", extern_provider_name)
                .appendIf("bio", bio)
                .appendIf("admin", isAdmin)
                .appendIf("can_create_group", can_create_group);

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + query.toString();

        return retrieve().method("PUT").to(tailUrl, GitlabUser.class);
    }

    /**
     * Delete a user
     *
     * @param targetUserId
     * @throws IOException
     */
    public void deleteUser(Integer targetUserId) throws IOException {
        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    public GitlabGroup getGroup(Integer groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId;
        return retrieve().to(tailUrl, GitlabGroup.class);
    }

    public List<GitlabGroup> getGroups() throws IOException {
        String tailUrl = GitlabGroup.URL;
        return retrieve().getAll(tailUrl, GitlabGroup[].class);
    }

    /**
     * Gets all members of a Group
     *
     * @param group The GitLab Group
     * @return The Group Members
     */
    public List<GitlabGroupMember> getGroupMembers(GitlabGroup group) throws IOException {
        return getGroupMembers(group.getId());
    }

    /**
     * Gets all members of a Group
     *
     * @param groupId The id of the GitLab Group
     * @return The Group Members
     */
    public List<GitlabGroupMember> getGroupMembers(Integer groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabGroupMember.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabGroupMember[].class));
    }

    /**
     * Creates a Group
     *
     * @param name The name of the group. The
     *             name will also be used as the path
     *             of the group.
     * @return The GitLab Group
     */
    public GitlabGroup createGroup(String name) throws IOException {
        return createGroup(name, name);
    }

    /**
     * Creates a Group
     *
     * @param name The name of the group
     * @param path The path for the group
     * @return The GitLab Group
     */
    public GitlabGroup createGroup(String name, String path) throws IOException {
        return createGroup(name, path, null, null);
    }

    /**
     * Creates a Group
     *
     * @param name       The name of the group
     * @param path       The path for the group
     * @param ldapCn     LDAP Group Name to sync with, null otherwise
     * @param ldapAccess Access level for LDAP group members, null otherwise
     * @return The GitLab Group
     */
    public GitlabGroup createGroup(String name, String path, String ldapCn, GitlabAccessLevel ldapAccess) throws IOException {

        Query query = new Query()
                .append("name", name)
                .append("path", path)
                .appendIf("ldap_cn", ldapCn)
                .appendIf("ldap_access", ldapAccess);

        String tailUrl = GitlabGroup.URL + query.toString();

        return dispatch().to(tailUrl, GitlabGroup.class);
    }

    /**
     * Add a group member.
     *
     * @param group       the GitlabGroup
     * @param user        the GitlabUser
     * @param accessLevel the GitlabAccessLevel
     * @return the GitlabGroupMember
     * @throws IOException on gitlab api call error
     */
    public GitlabGroupMember addGroupMember(GitlabGroup group, GitlabUser user, GitlabAccessLevel accessLevel) throws IOException {
        return addGroupMember(group.getId(), user.getId(), accessLevel);
    }

    /**
     * Add a group member.
     *
     * @param groupId     the group id
     * @param userId      the user id
     * @param accessLevel the GitlabAccessLevel
     * @return the GitlabGroupMember
     * @throws IOException on gitlab api call error
     */
    public GitlabGroupMember addGroupMember(Integer groupId, Integer userId, GitlabAccessLevel accessLevel) throws IOException {
        Query query = new Query()
                .appendIf("id", groupId)
                .appendIf("user_id", userId)
                .appendIf("access_level", accessLevel);
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabProjectMember.URL + query.toString();
        return dispatch().to(tailUrl, GitlabGroupMember.class);
    }

    /**
     * Delete a group member.
     *
     * @param group the GitlabGroup
     * @param user  the GitlabUser
     * @throws IOException on gitlab api call error
     */
    public void deleteGroupMember(GitlabGroup group, GitlabUser user) throws IOException {
        deleteGroupMember(group.getId(), user.getId());
    }

    /**
     * Delete a group member.
     *
     * @param groupId the group id
     * @param userId  the user id
     * @throws IOException on gitlab api call error
     */
    public void deleteGroupMember(Integer groupId, Integer userId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + "/" + GitlabGroupMember.URL + "/" + userId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    /**
     * Delete a group.
     *
     * @param groupId the group id
     * @throws IOException on gitlab api call error
     */
    public void deleteGroup(Integer groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    public GitlabProject getProject(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        return retrieve().to(tailUrl, GitlabProject.class);
    }

    public List<GitlabProject> getProjects() throws IOException {
        String tailUrl = GitlabProject.URL;
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    public List<GitlabProject> getAllProjects() throws IOException {
        String tailUrl = GitlabProject.URL + "/all";
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Creates a private Project
     *
     * @param name The name of the project
     * @return The GitLab Project
     */
    public GitlabProject createProject(String name) throws IOException {
        return createProject(name, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a Project
     *
     * @param name                 The name of the project
     * @param namespaceId          The Namespace for the new project, otherwise null indicates to use the GitLab default (user)
     * @param description          A description for the project, null otherwise
     * @param issuesEnabled        Whether Issues should be enabled, otherwise null indicates to use GitLab default
     * @param wallEnabled          Whether The Wall should be enabled, otherwise null indicates to use GitLab default
     * @param mergeRequestsEnabled Whether Merge Requests should be enabled, otherwise null indicates to use GitLab default
     * @param wikiEnabled          Whether a Wiki should be enabled, otherwise null indicates to use GitLab default
     * @param snippetsEnabled      Whether Snippets should be enabled, otherwise null indicates to use GitLab default
     * @param publik               Whether the project is public or private, if true same as setting visibilityLevel = 20, otherwise null indicates to use GitLab default
     * @param visibilityLevel      The visibility level of the project, otherwise null indicates to use GitLab default
     * @param importUrl            The Import URL for the project, otherwise null
     * @return the Gitlab Project
     */
    public GitlabProject createProject(String name, Integer namespaceId, String description, Boolean issuesEnabled, Boolean wallEnabled, Boolean mergeRequestsEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, Boolean publik, Integer visibilityLevel, String importUrl) throws IOException {
        Query query = new Query()
                .append("name", name)
                .appendIf("namespace_id", namespaceId)
                .appendIf("description", description)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("public", publik)
                .appendIf("visibility_level", visibilityLevel)
                .appendIf("import_url", importUrl);

        String tailUrl = GitlabProject.URL + query.toString();

        return dispatch().to(tailUrl, GitlabProject.class);
    }

    /**
     * Creates a Project for a specific User
     *
     * @param userId The id of the user to create the project for
     * @param name   The name of the project
     * @return The GitLab Project
     */
    public GitlabProject createUserProject(Integer userId, String name) throws IOException {
        return createUserProject(userId, name, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Creates a Project for a specific User
     *
     * @param userId               The id of the user to create the project for
     * @param name                 The name of the project
     * @param description          A description for the project, null otherwise
     * @param defaultBranch        The default branch for the project, otherwise null indicates to use GitLab default (master)
     * @param issuesEnabled        Whether Issues should be enabled, otherwise null indicates to use GitLab default
     * @param wallEnabled          Whether The Wall should be enabled, otherwise null indicates to use GitLab default
     * @param mergeRequestsEnabled Whether Merge Requests should be enabled, otherwise null indicates to use GitLab default
     * @param wikiEnabled          Whether a Wiki should be enabled, otherwise null indicates to use GitLab default
     * @param snippetsEnabled      Whether Snippets should be enabled, otherwise null indicates to use GitLab default
     * @param publik               Whether the project is public or private, if true same as setting visibilityLevel = 20, otherwise null indicates to use GitLab default
     * @param visibilityLevel      The visibility level of the project, otherwise null indicates to use GitLab default
     * @return The GitLab Project
     */
    public GitlabProject createUserProject(Integer userId, String name, String description, String defaultBranch, Boolean issuesEnabled, Boolean wallEnabled, Boolean mergeRequestsEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, Boolean publik, Integer visibilityLevel) throws IOException {
        Query query = new Query()
                .append("name", name)
                .appendIf("description", description)
                .appendIf("default_branch", defaultBranch)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("public", publik)
                .appendIf("visibility_level", visibilityLevel);

        String tailUrl = GitlabProject.URL + "/user/" + userId + query.toString();

        return dispatch().to(tailUrl, GitlabProject.class);
    }

    /**
     * Delete a Project.
     *
     * @param projectId The id of the project to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteProject(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL + "?state=opened";
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL;
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

    // Get a specific commit identified by the commit hash or name of a branch or tag
    // GET /projects/:id/repository/commits/:sha
    public GitlabCommit getCommit(Serializable projectId, String commitHash) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + commitHash;
        GitlabCommit commit = retrieve().to(tailUrl, GitlabCommit.class);
        return commit;
    }

    public List<GitlabCommit> getCommits(GitlabMergeRequest mergeRequest) throws IOException {
        Integer projectId = mergeRequest.getSourceProjectId();
        if (projectId == null) {
            projectId = mergeRequest.getProjectId();
        }

        Query query = new Query()
                .append("ref_name", mergeRequest.getSourceBranch());

        String tailUrl = GitlabProject.URL + "/" + projectId +
                "/repository" + GitlabCommit.URL + query.toString();

        GitlabCommit[] commits = retrieve().to(tailUrl, GitlabCommit[].class);
        return Arrays.asList(commits);
    }

    // gets all commits for a project
    public List<GitlabCommit> getAllCommits(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository" + GitlabCommit.URL;
        return retrieve().getAll(tailUrl, GitlabCommit[].class);
    }

    // List commit diffs for a project ID and commit hash
    // GET /projects/:id/repository/commits/:sha/diff
    public List<GitlabCommitDiff> getCommitDiffs(Serializable projectId, String commitHash) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + commitHash + GitlabCommitDiff.URL;
        GitlabCommitDiff[] diffs = retrieve().to(tailUrl, GitlabCommitDiff[].class);
        return Arrays.asList(diffs);
    }

    public GitlabNote createNote(GitlabMergeRequest mergeRequest, String body) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getId() + GitlabNote.URL;

        return dispatch().with("body", body).to(tailUrl, GitlabNote.class);
    }

    public List<GitlabBranch> getBranches(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBranch.URL;
        GitlabBranch[] branches = retrieve().to(tailUrl, GitlabBranch[].class);
        return Arrays.asList(branches);
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

    public List<GitlabProjectHook> getProjectHooks(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabProjectHook.URL;
        GitlabProjectHook[] hooks = retrieve().to(tailUrl, GitlabProjectHook[].class);
        return Arrays.asList(hooks);
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
        Query query = new Query()
                .append("url", url);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + query.toString();
        return dispatch().to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook addProjectHook(Serializable projectId, String url, boolean pushEvents, boolean issuesEvents, boolean mergeRequestEvents) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabProjectHook.URL;

        return dispatch()
                .with("url", url)
                .with("push_events", pushEvents ? "true" : "false")
                .with("issues_events", issuesEvents ? "true" : "false")
                .with("merge_requests_events", mergeRequestEvents ? "true" : "false")
                .to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook editProjectHook(GitlabProject project, String hookId, String url) throws IOException {
        Query query = new Query()
                .append("url", url);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId + query.toString();
        return retrieve().method("PUT").to(tailUrl, GitlabProjectHook.class);
    }

    public void deleteProjectHook(GitlabProjectHook hook) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + hook.getProjectId() + GitlabProjectHook.URL + "/" + hook.getId();
        retrieve().method("DELETE").to(tailUrl, Void.class);
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

    public GitlabIssue getIssue(Serializable projectId, Integer issueId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabIssue.URL + "/" + issueId;
        return retrieve().to(tailUrl, GitlabIssue.class);
    }

    public GitlabIssue createIssue(int projectId, int assigneeId, int milestoneId, String labels,
                                   String description, String title) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL;
        GitlabHTTPRequestor requestor = dispatch();
        applyIssue(requestor, projectId, assigneeId, milestoneId, labels, description, title);

        return requestor.to(tailUrl, GitlabIssue.class);
    }

    public GitlabIssue editIssue(int projectId, int issueId, int assigneeId, int milestoneId, String labels,
                                 String description, String title, GitlabIssue.Action action) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL + "/" + issueId;
        GitlabHTTPRequestor requestor = retrieve().method("PUT");
        applyIssue(requestor, projectId, assigneeId, milestoneId, labels, description, title);

        if (action != GitlabIssue.Action.LEAVE) {
            requestor.with("state_event", action.toString().toLowerCase());
        }

        return requestor.to(tailUrl, GitlabIssue.class);
    }

    private void applyIssue(GitlabHTTPRequestor requestor, int projectId,
                            int assigneeId, int milestoneId, String labels, String description,
                            String title) {

        requestor.with("title", title)
                .with("description", description)
                .with("labels", labels)
                .with("milestone_id", milestoneId);

        if (assigneeId != 0) {
            requestor.with("assignee_id", assigneeId == -1 ? 0 : assigneeId);
        }
    }

    public List<GitlabNote> getNotes(GitlabIssue issue) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/"
                + issue.getId() + GitlabNote.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabNote[].class));
    }

    public GitlabNote createNote(Serializable projectId, Integer issueId, String message) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL
                + "/" + issueId + GitlabNote.URL;
        return dispatch().with("body", message).to(tailUrl, GitlabNote.class);
    }

    public GitlabNote createNote(GitlabIssue issue, String message) throws IOException {
        return createNote(String.valueOf(issue.getProjectId()), issue.getId(), message);
    }

    public List<GitlabMilestone> getMilestones(GitlabProject project) throws IOException {
        return getMilestones(String.valueOf(project.getId()));
    }

    public List<GitlabMilestone> getMilestones(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMilestone.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabMilestone[].class));
    }

    /**
     * Add a project member.
     *
     * @param project     the GitlabProject
     * @param user        the GitlabUser
     * @param accessLevel the GitlabAccessLevel
     * @return the GitlabProjectMember
     * @throws IOException on gitlab api call error
     */
    public GitlabProjectMember addProjectMember(GitlabProject project, GitlabUser user, GitlabAccessLevel accessLevel) throws IOException {
        return addProjectMember(project.getId(), user.getId(), accessLevel);
    }

    /**
     * Add a project member.
     *
     * @param projectId   the project id
     * @param userId      the user id
     * @param accessLevel the GitlabAccessLevel
     * @return the GitlabProjectMember
     * @throws IOException on gitlab api call error
     */
    public GitlabProjectMember addProjectMember(Integer projectId, Integer userId, GitlabAccessLevel accessLevel) throws IOException {
        Query query = new Query()
                .appendIf("id", projectId)
                .appendIf("user_id", userId)
                .appendIf("access_level", accessLevel);
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabProjectMember.URL + query.toString();
        return dispatch().to(tailUrl, GitlabProjectMember.class);
    }

    /**
     * Delete a project team member.
     *
     * @param project the GitlabProject
     * @param user    the GitlabUser
     * @throws IOException on gitlab api call error
     */
    public void deleteProjectMember(GitlabProject project, GitlabUser user) throws IOException {
        deleteProjectMember(project.getId(), user.getId());
    }

    /**
     * Delete a project team member.
     *
     * @param projectId the project id
     * @param userId    the user id
     * @throws IOException on gitlab api call error
     */
    public void deleteProjectMember(Integer projectId, Integer userId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + "/" + GitlabProjectMember.URL + "/" + userId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    public List<GitlabProjectMember> getProjectMembers(GitlabProject project) throws IOException {
        return getProjectMembers(project.getId());
    }

    public List<GitlabProjectMember> getProjectMembers(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabProjectMember.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabProjectMember[].class));
    }

    /**
     * This will fail, if the given namespace is a user and not a group
     *
     * @param namespace
     * @return
     * @throws IOException
     */
    public List<GitlabProjectMember> getNamespaceMembers(GitlabNamespace namespace) throws IOException {
        return getNamespaceMembers(namespace.getId());
    }

    /**
     * This will fail, if the given namespace is a user and not a group
     *
     * @param namespaceId
     * @return
     * @throws IOException
     */
    public List<GitlabProjectMember> getNamespaceMembers(Integer namespaceId) throws IOException {
        String tailUrl = GitlabNamespace.URL + "/" + namespaceId + GitlabProjectMember.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabProjectMember[].class));
    }

    public GitlabSession getCurrentSession() throws IOException {
        String tailUrl = "/user";
        return retrieve().to(tailUrl, GitlabSession.class);
    }

    private String sanitizeProjectId(Serializable projectId) {
        if (!(projectId instanceof String) && !(projectId instanceof Integer)) {
            throw new IllegalArgumentException();
        }

        try {
            return URLEncoder.encode(String.valueOf(projectId), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException((e));
        }
    }
}
