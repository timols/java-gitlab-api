package org.gitlab.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.http.Query;
import org.gitlab.api.models.*;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;


/**
 * Gitlab API Wrapper class
 *
 * @author &#064;timols (Tim O)
 */
public class GitlabAPI {

    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String API_NAMESPACE = "/api/v3";
    private static final String PARAM_SUDO = "sudo";

    private final String hostUrl;

    private final String apiToken;
    private final TokenType tokenType;
    private boolean ignoreCertificateErrors = false;

    private GitlabAPI(String hostUrl, String apiToken, TokenType tokenType) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
        this.tokenType = tokenType;
    }

    public static GitlabSession connect(String hostUrl, String username, String password) throws IOException {
        String tailUrl = GitlabSession.URL;
        GitlabAPI api = connect(hostUrl, null, (TokenType) null);
        return api.dispatch().with("login", username).with("password", password)
                .to(tailUrl, GitlabSession.class);
    }

    public static GitlabAPI connect(String hostUrl, String apiToken) {
        return new GitlabAPI(hostUrl, apiToken, TokenType.PRIVATE_TOKEN);
    }

    public static GitlabAPI connect(String hostUrl, String apiToken, TokenType tokenType) {
        return new GitlabAPI(hostUrl, apiToken, tokenType);
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
            tailAPIUrl = tailAPIUrl + (tailAPIUrl.indexOf('?') > 0 ? '&' : '?') + tokenType.getTokenParamName() + "=" + apiToken;
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
     * @param email                User email
     * @param password             Password
     * @param username             User name
     * @param fullName             Full name
     * @param skypeId              Skype Id
     * @param linkedIn             LinkedIn
     * @param twitter              Twitter
     * @param website_url          Website URL
     * @param projects_limit       Projects limit
     * @param extern_uid           External User ID
     * @param extern_provider_name External Provider Name
     * @param bio                  Bio
     * @param isAdmin              Is Admin
     * @param can_create_group     Can Create Group
     * @param skip_confirmation    Skip Confirmation
     * @return                     A GitlabUser
     * @throws IOException on gitlab api call error
     * @see <a href="http://doc.gitlab.com/ce/api/users.html">http://doc.gitlab.com/ce/api/users.html</a>
     */
    public GitlabUser createUser(String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group,
                                 Boolean skip_confirmation) throws IOException {

        Query query = new Query()
                .append("email", email)
                .appendIf("confirm", skip_confirmation == null ? null : !skip_confirmation)
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
     * @param targetUserId         User ID
     * @param email                User email
     * @param password             Password
     * @param username             User name
     * @param fullName             Full name
     * @param skypeId              Skype Id
     * @param linkedIn             LinkedIn
     * @param twitter              Twitter
     * @param website_url          Website URL
     * @param projects_limit       Projects limit
     * @param extern_uid           External User ID
     * @param extern_provider_name External Provider Name
     * @param bio                  Bio
     * @param isAdmin              Is Admin
     * @param can_create_group     Can Create Group
     * @return The Updated User
     * @throws IOException on gitlab api call error
     */
    public GitlabUser updateUser(Integer targetUserId,
                                 String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group) throws IOException {

        Query query = new Query()
                .append("email", email)
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
     * Block a user
     *
     * @param targetUserId The id of the Gitlab user
     * @throws IOException on gitlab api call error
     */
    public void blockUser(Integer targetUserId) throws IOException {

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabUser.BLOCK_URL;

        retrieve().method("PUT").to(tailUrl, Void.class);
    }

    /**
     * Unblock a user
     *
     * @param targetUserId The id of the Gitlab user
     * @throws IOException on gitlab api call error
     */
    public void unblockUser(Integer targetUserId) throws IOException {

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabUser.UNBLOCK_URL;

        retrieve().method("PUT").to(tailUrl, Void.class);
    }

    /**
     * Create a new ssh key for the user
     *
     * @param targetUserId The id of the Gitlab user
     * @param title        The title of the ssh key
     * @param key          The public key
     * @return The new GitlabSSHKey
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey createSSHKey(Integer targetUserId, String title, String key) throws IOException {

        Query query = new Query()
                .append("title", title)
                .append("key", key);

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabSSHKey.KEYS_URL + query.toString();

        return dispatch().to(tailUrl, GitlabSSHKey.class);
    }

    /**
     * Delete user's ssh key
     *
     * @param targetUserId The id of the Gitlab user
     * @param targetKeyId  The id of the Gitlab ssh key
     * @throws IOException on gitlab api call error
     */
    public void deleteSSHKey(Integer targetUserId, Integer targetKeyId) throws IOException {
        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabSSHKey.KEYS_URL + "/" + targetKeyId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }


    /**
     * Gets all ssh keys for a user
     *
     * @param targetUserId The id of the GitLab User
     * @return The list of user ssh keys
     * @throws IOException on gitlab api call error
     */
    public List<GitlabSSHKey> getSSHKeys(Integer targetUserId) throws IOException {
        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabSSHKey.KEYS_URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabSSHKey[].class));
    }

    /**
     * Get key with user information by ID of an SSH key.
     *
     * @param keyId The ID of an SSH key
     * @return The SSH key with user information
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey getSSHKey(Integer keyId) throws IOException {
        String tailUrl = GitlabSSHKey.KEYS_URL + "/" + keyId;
        return retrieve().to(tailUrl, GitlabSSHKey.class);
    }

    /**
     * Delete a user
     *
     * @param targetUserId  The target User ID
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
     */
    public List<GitlabGroupMember> getGroupMembers(GitlabGroup group) throws IOException {
        return getGroupMembers(group.getId());
    }

    /**
     * Gets all members of a Group
     *
     * @param groupId The id of the GitLab Group
     * @return The Group Members
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * @throws IOException on gitlab api call error
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
     * Updates a Project
     *
     * @param projectId            The id of the project to update
     * @param name                 The name of the project
     * @param description          A description for the project, null otherwise
     * @param issuesEnabled        Whether Issues should be enabled, otherwise null indicates to use GitLab default
     * @param wallEnabled          Whether The Wall should be enabled, otherwise null indicates to use GitLab default
     * @param mergeRequestsEnabled Whether Merge Requests should be enabled, otherwise null indicates to use GitLab default
     * @param wikiEnabled          Whether a Wiki should be enabled, otherwise null indicates to use GitLab default
     * @param snippetsEnabled      Whether Snippets should be enabled, otherwise null indicates to use GitLab default
     * @param publik               Whether the project is public or private, if true same as setting visibilityLevel = 20, otherwise null indicates to use GitLab default
     * @param visibilityLevel      The visibility level of the project, otherwise null indicates to use GitLab default
     * @return the Gitlab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject updateProject(Integer projectId, String name, String description, Boolean issuesEnabled, Boolean wallEnabled, Boolean mergeRequestsEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, Boolean publik, Integer visibilityLevel) throws IOException {
        Query query = new Query()
                .appendIf("name", name)
                .appendIf("description", description)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("public", publik)
                .appendIf("visibility_level", visibilityLevel);

        String tailUrl = GitlabProject.URL + "/" + projectId + query.toString();

        return retrieve().method("PUT").to(tailUrl, GitlabProject.class);
    }

    /**
     * Delete a Project.
     *
     * @param projectId The id of the project to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteProject(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        retrieve().method("DELETE").to(tailUrl, null);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "?state=opened";
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL + "?state=opened";
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getAllMergeRequests(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public GitlabMergeRequest getMergeRequest(GitlabProject project, Integer mergeRequestId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/merge_request/" + mergeRequestId;
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    /**
     * @param project           The Project
     * @param mergeRequestId    Merge Request ID
     * @param mergeCommitMessage optional merge commit message. Null if not set
     * @return new merge request status
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest acceptMergeRequest(GitlabProject project, Integer mergeRequestId, String mergeCommitMessage) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/merge_request/" + mergeRequestId + "/merge";
        GitlabHTTPRequestor requestor = retrieve().method("PUT");
        requestor.with("id", project.getId());
        requestor.with("merge_request_id", mergeRequestId);
        if (mergeCommitMessage != null)
            requestor.with("merge_commit_message", mergeCommitMessage);
        return requestor.to(tailUrl, GitlabMergeRequest.class);
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
        return retrieve().to(tailUrl, GitlabCommit.class);
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

    /**
     * Get raw file content
     *
     * @param project The Project
     * @param sha   The commit or branch name
     * @param filepath   The path of the file
     * @throws IOException on gitlab api call error
     */
    public byte[] getRawFileContent(GitlabProject project, String sha, String filepath) throws IOException {
        Query query = new Query()
                .append("filepath", filepath);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/blobs/" + sha + query.toString();
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Get the raw file contents for a blob by blob SHA.
     *
     * @param project The Project
     * @param sha   The commit or branch name
     * @throws IOException on gitlab api call error
     */
    public byte[] getRawBlobContent(GitlabProject project, String sha) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/raw_blobs/" + sha;
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Get an archive of the repository
     *
     * @param project The Project
     * @throws IOException on gitlab api call error
     */
    public byte[] getFileArchive(GitlabProject project) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/archive";
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Get an archive of the repository
     *
     * @param project The Project
     * @param path The path inside the repository. Used to get content of subdirectories (optional)
     * @param ref_name The name of a repository branch or tag or if not given the default branch (optional)
     * @throws IOException on gitlab api call error
     */
    public List<GitlabRepositoryTree> getRepositoryTree(GitlabProject project, String path, String ref_name) throws IOException {
        Query query = new Query()
                .appendIf("path", path)
                .appendIf("ref_name", ref_name);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository" + GitlabRepositoryTree.URL + query.toString();
        GitlabRepositoryTree[] tree = retrieve().to(tailUrl, GitlabRepositoryTree[].class);
        return Arrays.asList(tree);
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
        return retrieve().to(tailUrl, GitlabBranch.class);
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
     * @param namespace The namespace
     * @return  A list of Gitlab Project members
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProjectMember> getNamespaceMembers(GitlabNamespace namespace) throws IOException {
        return getNamespaceMembers(namespace.getId());
    }

    /**
     * This will fail, if the given namespace is a user and not a group
     *
     * @param namespaceId Namespace ID
     * @return  A list of Gitlab Project members
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProjectMember> getNamespaceMembers(Integer namespaceId) throws IOException {
        String tailUrl = GitlabNamespace.URL + "/" + namespaceId + GitlabProjectMember.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabProjectMember[].class));
    }

    /**
     * Transfer a project to the given namespace
     *
     * @param namespaceId Namespace ID
     * @param projectId   Project ID
     * @throws IOException on gitlab api call error
     */
    public void transfer(Integer namespaceId, Integer projectId) throws IOException {
        String tailUrl = GitlabNamespace.URL + "/" + namespaceId + GitlabProject.URL + "/" + projectId;
        dispatch().to(tailUrl, Void.class);
    }

    /**
     * Create a new deploy key for the project
     *
     * @param targetProjectId The id of the Gitlab project
     * @param title        The title of the ssh key
     * @param key          The public key
     * @return The new GitlabSSHKey
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey createDeployKey(Integer targetProjectId, String title, String key) throws IOException {
        Query query = new Query()
                .append("title", title)
                .append("key", key);

        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.KEYS_URL + query.toString();

        return dispatch().to(tailUrl, GitlabSSHKey.class);
    }

    /**
     * Delete a deploy key for a project
     *
     * @param targetProjectId The id of the Gitlab project
     * @param targetKeyId  The id of the Gitlab ssh key
     * @throws IOException on gitlab api call error
     */
    public void deleteDeployKey(Integer targetProjectId, Integer targetKeyId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.KEYS_URL + "/" + targetKeyId;
        retrieve().method("DELETE").to(tailUrl, Void.class);
    }

    /**
     * Gets all deploy keys for a project
     *
     * @param targetProjectId The id of the Gitlab project
     * @return The list of project deploy keys
     * @throws IOException on gitlab api call error
     */
    public List<GitlabSSHKey> getDeployKeys(Integer targetProjectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.KEYS_URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabSSHKey[].class));
    }

    public GitlabSession getCurrentSession() throws IOException {
        String tailUrl = "/user";
        return retrieve().to(tailUrl, GitlabSession.class);
    }

    /**
     * Get list of system hooks
     *
     * @return The system hooks list
     * @throws IOException on gitlab api call error
     */
    public List<GitlabSystemHook> getSystemHooks() throws IOException {
        String tailUrl = GitlabSystemHook.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabSystemHook[].class));
    }

    /**
     * Add new system hook hook
     *
     * @param url System hook url
     * @throws IOException on gitlab api call error
     */
    public GitlabSystemHook addSystemHook(String url) throws IOException {
        String tailUrl = GitlabSystemHook.URL;
        return dispatch().with("url", url).to(tailUrl, GitlabSystemHook.class);
    }

    /**
     * Test system hook
     *
     * @throws IOException on gitlab api call error
     */
    public void testSystemHook(Integer hookId) throws IOException {
        String tailUrl = GitlabSystemHook.URL + "/" + hookId;
        retrieve().to(tailUrl, Void.class);
    }

    /**
     * Delete system hook
     *
     * @throws IOException on gitlab api call error
     */
    public GitlabSystemHook deleteSystemHook(Integer hookId) throws IOException {
        String tailUrl = GitlabSystemHook.URL + "/" + hookId;
        return retrieve().method("DELETE").to(tailUrl, GitlabSystemHook.class);
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
