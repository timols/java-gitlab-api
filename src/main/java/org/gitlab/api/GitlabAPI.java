package org.gitlab.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.http.Query;
import org.gitlab.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.gitlab.api.http.Method.*;

/**
 * Gitlab API Wrapper class
 *
 * @author &#064;timols (Tim O)
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class GitlabAPI {

    private static final Logger LOG = LoggerFactory.getLogger(GitlabAPI.class);

    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String API_NAMESPACE = "/api/v4";
    private static final String PARAM_SUDO = "sudo";
    private static final String PARAM_MAX_ITEMS_PER_PAGE = new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).toString();

    private final String hostUrl;

    private final String apiToken;
    private final TokenType tokenType;
    private AuthMethod authMethod;
    private boolean ignoreCertificateErrors = false;
    private Proxy proxy;
    private int defaultTimeout = 0;
    private int readTimeout = defaultTimeout;
    private int connectionTimeout = defaultTimeout;
    private String userAgent = GitlabAPI.class.getCanonicalName() + "/" + System.getProperty("java.version");

    private GitlabAPI(String hostUrl, String apiToken, TokenType tokenType, AuthMethod method) {
        this.hostUrl = hostUrl.endsWith("/") ? hostUrl.replaceAll("/$", "") : hostUrl;
        this.apiToken = apiToken;
        this.tokenType = tokenType;
        this.authMethod = method;
    }

    public static GitlabSession connect(String hostUrl, String username, String password) throws IOException {
        String tailUrl = GitlabSession.URL;
        GitlabAPI api = connect(hostUrl, null, null, null);
        return api.dispatch().with("login", username).with("password", password)
                .to(tailUrl, GitlabSession.class);
    }

    public static GitlabAPI connect(String hostUrl, String apiToken) {
        return new GitlabAPI(hostUrl, apiToken, TokenType.PRIVATE_TOKEN, AuthMethod.HEADER);
    }

    public static GitlabAPI connect(String hostUrl, String apiToken, TokenType tokenType) {
        return new GitlabAPI(hostUrl, apiToken, tokenType, AuthMethod.HEADER);
    }

    public static GitlabAPI connect(String hostUrl, String apiToken, TokenType tokenType, AuthMethod method) {
        return new GitlabAPI(hostUrl, apiToken, tokenType, method);
    }

    public GitlabAPI ignoreCertificateErrors(boolean ignoreCertificateErrors) {
        this.ignoreCertificateErrors = ignoreCertificateErrors;
        return this;
    }

    public GitlabAPI proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    public int getResponseReadTimeout() {
        return readTimeout;
    }

    /**
     * @deprecated use this.getResponseReadTimeout() method
     */
    @Deprecated
    public int getRequestTimeout() {
        return getResponseReadTimeout();
    }

    /**
     * @deprecated use this.setResponseReadTimeout(int readTimeout) method
     */
    @Deprecated
    public GitlabAPI setRequestTimeout(int readTimeout) {
        setResponseReadTimeout(readTimeout);
        return this;
    }

    public GitlabAPI setResponseReadTimeout(int readTimeout) {
        if (readTimeout < 0) {
            LOG.warn("The value of the \"Response Read Timeout\" parameter can not be negative. " +
                    "The default value [{}] will be used.", defaultTimeout);
            this.readTimeout = defaultTimeout;
        } else {
            this.readTimeout = readTimeout;
        }
        return this;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public GitlabAPI setConnectionTimeout(int connectionTimeout) {
        if (connectionTimeout < 0) {
            LOG.warn("The value of the \"Connection Timeout\" parameter can not be negative. " +
                    "The default value [{}] will be used.", defaultTimeout);
            this.connectionTimeout = defaultTimeout;
        } else {
            this.connectionTimeout = connectionTimeout;
        }
        return this;
    }

    public GitlabHTTPRequestor retrieve() {
        return new GitlabHTTPRequestor(this).authenticate(apiToken, tokenType, authMethod);
    }

    public GitlabHTTPRequestor dispatch() {
        return new GitlabHTTPRequestor(this).authenticate(apiToken, tokenType, authMethod).method(POST);
    }

    public boolean isIgnoreCertificateErrors() {
        return ignoreCertificateErrors;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public URL getAPIUrl(String tailAPIUrl) throws IOException {
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

    public String getHost() {
        return hostUrl;
    }

    public List<GitlabUser> getUsers() {
        String tailUrl = GitlabUser.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabUser[].class);
    }

    /**
     * Finds users by email address or username.
     *
     * @param emailOrUsername Some portion of the email address or username
     * @return A non-null List of GitlabUser instances.  If the search term is
     * null or empty a List with zero GitlabUsers is returned.
     * @throws IOException on gitlab api call error
     */
    public List<GitlabUser> findUsers(String emailOrUsername) throws IOException {
        List<GitlabUser> users = new ArrayList<>();
        if (emailOrUsername != null && !emailOrUsername.equals("")) {
            String tailUrl = GitlabUser.URL + "?search=" + emailOrUsername;
            GitlabUser[] response = retrieve().to(tailUrl, GitlabUser[].class);
            users = Arrays.asList(response);
        }
        return users;
    }

    /**
     * Return API User
     */
    public GitlabUser getUser() throws IOException {
        String tailUrl = GitlabUser.USER_URL;
        return retrieve().to(tailUrl, GitlabUser.class);
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
     * @param external             External
     * @return                     A GitlabUser
     * @throws IOException on gitlab api call error
     * @see <a href="http://doc.gitlab.com/ce/api/users.html">http://doc.gitlab.com/ce/api/users.html</a>
     */
    public GitlabUser createUser(String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group,
                                 Boolean skip_confirmation, Boolean external) throws IOException {

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
                .appendIf("can_create_group", can_create_group)
                .appendIf("external", external);

        String tailUrl = GitlabUser.USERS_URL + query.toString();

        return dispatch().to(tailUrl, GitlabUser.class);
    }

    /**
     * Create a new user. This may succeed only if the requester is an administrator.
     *
     * @param request An object that represents the parameters for the request.
     * @return {@link GitlabUser}
     * @throws IOException on gitlab api call error
     */
    public GitlabUser createUser(CreateUserRequest request) throws IOException {
        String tailUrl = GitlabUser.USERS_URL + request.toQuery().toString();
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
     * @param external             External
     * @return The Updated User
     * @throws IOException on gitlab api call error
     */
    public GitlabUser updateUser(Integer targetUserId,
                                 String email, String password, String username,
                                 String fullName, String skypeId, String linkedIn,
                                 String twitter, String website_url, Integer projects_limit,
                                 String extern_uid, String extern_provider_name,
                                 String bio, Boolean isAdmin, Boolean can_create_group, Boolean external) throws IOException {

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
                .appendIf("can_create_group", can_create_group)
                .appendIf("external", external);

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + query.toString();

        return retrieve().method(PUT).to(tailUrl, GitlabUser.class);
    }

    /**
     * Block a user
     *
     * @param targetUserId The id of the Gitlab user
     * @throws IOException on gitlab api call error
     */
    public void blockUser(Integer targetUserId) throws IOException {

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabUser.BLOCK_URL;

        retrieve().method(POST).to(tailUrl, Void.class);
    }

    /**
     * Unblock a user
     *
     * @param targetUserId The id of the Gitlab user
     * @throws IOException on gitlab api call error
     */
    public void unblockUser(Integer targetUserId) throws IOException {

        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId + GitlabUser.UNBLOCK_URL;

        retrieve().method(POST).to(tailUrl, Void.class);
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
     * Create a new ssh key for the authenticated user.
     *
     * @param title        The title of the ssh key
     * @param key          The public key
     * @return The new GitlabSSHKey
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey createSSHKey(String title, String key) throws IOException {

        Query query = new Query()
                .append("title", title)
                .append("key", key);

        String tailUrl = GitlabUser.USER_URL + GitlabSSHKey.KEYS_URL + query.toString();

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
        retrieve().method(DELETE).to(tailUrl, Void.class);
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
     * @param targetUserId The target User ID
     * @throws IOException on gitlab api call error
     */
    public void deleteUser(Integer targetUserId) throws IOException {
        String tailUrl = GitlabUser.USERS_URL + "/" + targetUserId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    public GitlabGroup getGroup(Integer groupId) throws IOException {
        return getGroup(groupId.toString());
    }

    /**
     * Get a group by path
     *
     * @param path Path of the group
     * @return {@link GitlabGroup} object
     *
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup getGroup(String path) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + URLEncoder.encode(path, "UTF-8");
        return retrieve().to(tailUrl, GitlabGroup.class);
    }

    public List<GitlabGroup> getGroups() throws IOException {
        return getGroupsViaSudo(null, new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE));
    }

    public List<GitlabGroup> getGroupsViaSudo(String username, Pagination pagination) throws IOException {
        String tailUrl = GitlabGroup.URL;

        Query query = new Query()
                .appendIf(PARAM_SUDO, username);

        if (pagination != null) {
            query.mergeWith(pagination.asQuery());
        }

        return retrieve().getAll(tailUrl + query.toString(), GitlabGroup[].class);
    }

    /**
     * Get all the projects for a group.
     *
     * @param group the target group
     * @return a list of projects for the group
     */
    public List<GitlabProject> getGroupProjects(GitlabGroup group) {
        return getGroupProjects(group.getId());
    }

    /**
     * Get all the projects for a group.
     *
     * @param groupId the target group's id.
     * @return a list of projects for the group
     */
    public List<GitlabProject> getGroupProjects(Integer groupId) {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabProject.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Gets all members of a Group
     *
     * @param group The GitLab Group
     * @return The Group Members
     */
    public List<GitlabGroupMember> getGroupMembers(GitlabGroup group) {
        return getGroupMembers(group.getId());
    }

    /**
     * Gets all members of a Group
     *
     * @param groupId The id of the GitLab Group
     * @return The Group Members
     */
    public List<GitlabGroupMember> getGroupMembers(Integer groupId) {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabGroupMember.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabGroupMember[].class);
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
        return createGroup(name, path, null, null, null);
    }

    /**
     * Creates a Group
     *
     * @param name     The name of the group
     * @param path     The path for the group
     * @param sudoUser The user to create the group on behalf of
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup createGroupViaSudo(String name, String path, GitlabUser sudoUser) throws IOException {
        return createGroup(name, path, null, null, sudoUser);
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
        return createGroup(name, path, ldapCn, ldapAccess, null);
    }


    /**
     * Creates a Group
     *
     * @param request  An object that represents the parameters for the request.
     * @param sudoUser The user for whom we're creating the group
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup createGroup(CreateGroupRequest request, GitlabUser sudoUser) throws IOException {
        Query query = request.toQuery();
        query.appendIf(PARAM_SUDO, sudoUser != null ? sudoUser.getId() : null);

        String tailUrl = GitlabGroup.URL + query.toString();

        return dispatch().to(tailUrl, GitlabGroup.class);
    }

    /**
     * Creates a Group
     *
     * @param name       The name of the group
     * @param path       The path for the group
     * @param ldapCn     LDAP Group Name to sync with, null otherwise
     * @param ldapAccess Access level for LDAP group members, null otherwise
     * @param sudoUser   The user to create the group on behalf of
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup createGroup(String name, String path, String ldapCn, GitlabAccessLevel ldapAccess, GitlabUser sudoUser) throws IOException {
        return createGroup(name, path, ldapCn, ldapAccess, sudoUser, null);
    }

    /**
     * Creates a Group
     *
     * @param name       The name of the group
     * @param path       The path for the group
     * @param ldapCn     LDAP Group Name to sync with, null otherwise
     * @param ldapAccess Access level for LDAP group members, null otherwise
     * @param sudoUser   The user to create the group on behalf of
     * @param parentId   The id of a parent group; the new group will be its subgroup
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup createGroup(String name, String path, String ldapCn, GitlabAccessLevel ldapAccess, GitlabUser sudoUser, Integer parentId) throws IOException {

        Query query = new Query()
                .append("name", name)
                .append("path", path)
                .appendIf("ldap_cn", ldapCn)
                .appendIf("ldap_access", ldapAccess)
                .appendIf(PARAM_SUDO, sudoUser != null ? sudoUser.getId() : null)
                .appendIf("parent_id", parentId);

        String tailUrl = GitlabGroup.URL + query.toString();

        return dispatch().to(tailUrl, GitlabGroup.class);
    }
	
	/**
     * Creates a Group
     *
     * @param group The gitlab Group object
     * @param sudoUser The user to create the group on behalf of
     *
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup createGroup(GitlabGroup group, GitlabUser sudoUser) throws IOException {

        Query query = new Query()
                .append("name", group.getName())
                .append("path", group.getPath())
                .appendIf("description", group.getDescription())
                .appendIf("membership_lock", group.getMembershipLock())
                .appendIf("share_with_group_lock", group.getShareWithGroupLock())
                .appendIf("visibility", group.getVisibility().toString())
                .appendIf("lfs_enabled", group.isLfsEnabled())
                .appendIf("request_access_enabled", group.isRequestAccessEnabled())
                .appendIf("shared_runners_minutes_limit", group.getSharedRunnersMinutesLimit())
                .appendIf("ldap_cn", group.getLdapCn())
                .appendIf("ldap_access", group.getLdapAccess())
                .appendIf(PARAM_SUDO, sudoUser != null ? sudoUser.getId() : null);

        String tailUrl = GitlabGroup.URL + query.toString();

        return dispatch().to(tailUrl, GitlabGroup.class);
    }

    /**
     * Updates a Group
     *
     * @param group the group object
     * @param sudoUser The user to create the group on behalf of
     * @return The GitLab Group
     * @throws IOException on gitlab api call error
     */
    public GitlabGroup updateGroup(GitlabGroup group, GitlabUser sudoUser) throws IOException {

        Query query = new Query()
                .appendIf("name", group.getName())
                .appendIf("path", group.getPath())
                .appendIf("description", group.getDescription())
                .appendIf("membership_lock", group.getMembershipLock())
                .appendIf("share_with_group_lock", group.getShareWithGroupLock())
                .appendIf("visibility", group.getVisibility().toString())
                .appendIf("lfs_enabled", group.isLfsEnabled())
                .appendIf("request_access_enabled", group.isRequestAccessEnabled())
                .appendIf("shared_runners_minutes_limit", group.getSharedRunnersMinutesLimit())
                .appendIf("ldap_cn", group.getLdapCn())
                .appendIf("ldap_access", group.getLdapAccess())
                .appendIf(PARAM_SUDO, sudoUser != null ? sudoUser.getId() : null);

        String tailUrl = GitlabGroup.URL + "/" + group.getId() + query.toString();

        return retrieve().method(PUT).to(tailUrl, GitlabGroup.class);
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
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Delete a group.
     *
     * @param groupId the group id
     * @throws IOException on gitlab api call error
     */
    public void deleteGroup(Integer groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Get's all projects in Gitlab, requires sudo user
     *
     * @return A list of gitlab projects
     */
    public List<GitlabProject> getAllProjects() {
        String tailUrl = GitlabProject.URL;
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get Project by project Id
     *
     * @param projectId - gitlab project Id
     * @return {@link GitlabProject}
     * @throws IOException on gitlab api call error
     */
    public GitlabProject getProject(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        return retrieve().to(tailUrl, GitlabProject.class);
    }

    /**
     * use namespace & project name to get project
     */
    public GitlabProject getProject(String namespace, String projectName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeGroupId(namespace) + "%2F" + sanitizeProjectId(projectName);
        return retrieve().to(tailUrl, GitlabProject.class);
    }

    /*
     * use project id to get Project JSON
     */
    public String getProjectJson(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        return retrieve().to(tailUrl, String.class);
    }

    /*
     * use namespace & project name to get project
     */
    public String getProjectJson(String namespace, String projectName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeGroupId(namespace) + "%2F" + sanitizeProjectId(projectName);
        return retrieve().to(tailUrl, String.class);
    }

    /**
     * Get a list of projects accessible by the authenticated user.
     *
     * @return A list of gitlab projects
     */
    public List<GitlabProject> getProjects() {
        String tailUrl = GitlabProject.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get a list of projects of size perPage accessible by the authenticated user.
     *
     * @param page    page offset.
     * @param perPage number elements to get after page offset.
     * @return A list of gitlab projects
     * @throws IOException on Gitlab API call error
     */
    public List<GitlabProject> getProjectsWithPagination(int page, int perPage) throws IOException {
        Pagination pagination = new Pagination()
                .withPage(page)
                .withPerPage(perPage);
        return getProjectsWithPagination(pagination);
    }

    /**
     * Get a list of projects by pagination accessible by the authenticated user.
     *
     * @param pagination
     * @return
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> getProjectsWithPagination(Pagination pagination) throws IOException {
        StringBuilder tailUrl = new StringBuilder(GitlabProject.URL);

        if (pagination != null) {
            Query query = pagination.asQuery();
            tailUrl.append(query.toString());
        }

        return Arrays.asList(retrieve().method(GET).to(tailUrl.toString(), GitlabProject[].class));
    }

    /**
     * Get a list of projects owned by the authenticated user.
     *
     * @return A list of gitlab projects
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> getOwnedProjects() throws IOException {
        Query query = new Query().append("owned", "true");
        query.mergeWith(new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).asQuery());
        String tailUrl = GitlabProject.URL + query.toString();
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get a list of projects that the authenticated user is a member of.
     *
     * @return A list of gitlab projects
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> getMembershipProjects() throws IOException {
        Query query = new Query().append("membership", "true");
        query.mergeWith(new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).asQuery());
        String tailUrl = GitlabProject.URL + query.toString();
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get a list of projects starred by the authenticated user.
     *
     * @return A list of gitlab projects
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> getStarredProjects() throws IOException {
        Query query = new Query().append("starred", "true");
        query.mergeWith(new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).asQuery());
        String tailUrl = GitlabProject.URL + query.toString();
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get a list of projects accessible by the authenticated user.
     *
     * @return A list of gitlab projects
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> getProjectsViaSudo(GitlabUser user) throws IOException {
        Query query = new Query()
                .appendIf(PARAM_SUDO, user.getId());
        query.mergeWith(new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).asQuery());
        String tailUrl = GitlabProject.URL + query.toString();
        return retrieve().getAll(tailUrl, GitlabProject[].class);
    }

    /**
     * Get a list of projects of perPage elements accessible by the authenticated user given page offset
     *
     * @param user    Gitlab User to invoke sudo with
     * @param page    Page offset
     * @param perPage Number of elements to get after page offset
     * @return A list of gitlab projects
     * @throws IOException Gitlab API call error
     */
    public List<GitlabProject> getProjectsViaSudoWithPagination(GitlabUser user, int page, int perPage) throws IOException {
        Pagination pagination = new Pagination()
                .withPage(page)
                .withPerPage(perPage);
        return getProjectsViaSudoWithPagination(user, pagination);
    }

    /**
     * Get a list of projects of with Pagination.
     *
     * @param user       Gitlab User to invoke sudo with
     * @param pagination
     * @return A list of gitlab projects
     * @throws IOException Gitlab API call error
     */
    public List<GitlabProject> getProjectsViaSudoWithPagination(GitlabUser user, Pagination pagination) throws IOException {
        StringBuilder tailUrl = new StringBuilder(GitlabProject.URL);

        Query query = new Query()
                .appendIf(PARAM_SUDO, user.getId());

        if (pagination != null) {
            query.mergeWith(pagination.asQuery());
        }

        tailUrl.append(query.toString());
        return Arrays.asList(retrieve().method(GET).to(tailUrl.toString(), GitlabProject[].class));
    }

    /**
     * Get a list of the namespaces of the authenticated user.
     * If the user is an administrator, a list of all namespaces in the GitLab instance is shown.
     *
     * @return A list of gitlab namespace
     */
    public List<GitlabNamespace> getNamespaces() {
        String tailUrl = GitlabNamespace.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabNamespace[].class);
    }

    /**
     * Uploads a file to a project
     *
     * @param project
     * @param file
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabUpload uploadFile(GitlabProject project, File file) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(project.getId()) + GitlabUpload.URL;
        return dispatch().withAttachment("file", file).to(tailUrl, GitlabUpload.class);
    }

    /**
     * Gets a list of a project's jobs in Gitlab
     *
     * @param project the project
     * @return A list of project jobs
     */
    public List<GitlabJob> getProjectJobs(GitlabProject project) {
        return getProjectJobs(project.getId());
    }

    /**
     * Gets a list of a project's jobs in Gitlab
     *
     * @param projectId the project id
     * @return A list of project jobs
     */
    public List<GitlabJob> getProjectJobs(Integer projectId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabJob[].class);
    }


    /**
     * Gets a list of project's jobs of the given pipeline in Gitlab
     *
     * @param project    the project
     * @param pipelineId
     * @return A list of project jobs
     */
    public List<GitlabJob> getPipelineJobs(GitlabProject project, Integer pipelineId) {
        return getPipelineJobs(project.getId(), pipelineId);
    }

    /**
     * Gets a list of project's jobs of the given pipeline in Gitlab
     *
     * @param projectId
     * @param pipelineId
     * @return A list of project jobs
     */
    public List<GitlabJob> getPipelineJobs(Integer projectId, Integer pipelineId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabPipeline.URL + "/" + sanitizeId(pipelineId, "PipelineID") + GitlabJob.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabJob[].class);
    }


    /**
     * Cancel a single job of a project
     *
     * @param projectId
     * @param jobId
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabJob cancelJob(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + sanitizeId(jobId, "JobID") + "/cancel";
        return dispatch().to(tailUrl, GitlabJob.class);
    }

    /**
     * Retry a single job of a project
     *
     * @param projectId
     * @param jobId
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabJob retryJob(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + sanitizeId(jobId, "JobID") + "/retry";
        return dispatch().to(tailUrl, GitlabJob.class);
    }

    /**
     * Erase a single job of a project (remove job artifacts and a job trace)
     *
     * @param projectId
     * @param jobId
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabJob eraseJob(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + sanitizeId(jobId, "JobID") + "/erase";
        return dispatch().to(tailUrl, GitlabJob.class);
    }


    /**
     * Triggers a manual action to start a job.
     *
     * @param projectId
     * @param jobId
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabJob playJob(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + sanitizeId(jobId, "JobID") + "/play";
        return dispatch().to(tailUrl, GitlabJob.class);
    }


    /**
     * Gets a build for a project
     *
     * @param projectId the project id
     * @param jobId     the build id
     * @return A list of project jobs
     * @throws IOException on gitlab api call error
     */
    public GitlabJob getProjectJob(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + jobId;
        return retrieve().to(tailUrl, GitlabJob.class);
    }

    /**
     * Get build artifacts of a project build
     *
     * @param project The Project
     * @param job     The build
     * @throws IOException on gitlab api call error
     */
    public byte[] getJobArtifact(GitlabProject project, GitlabJob job) throws IOException {
        return getJobArtifact(project.getId(), job.getId());
    }

    /**
     * Get build artifacts of a project build
     *
     * @param projectId The Project's Id
     * @param jobId     The build's Id
     * @throws IOException on gitlab api call error
     */
    public byte[] getJobArtifact(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + jobId + "/artifacts";
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Get build trace of a project build
     *
     * @param project The Project
     * @param job     The build
     * @throws IOException on gitlab api call error
     */
    public byte[] getJobTrace(GitlabProject project, GitlabJob job) throws IOException {
        return getJobArtifact(project.getId(), job.getId());
    }

    /**
     * Get build trace of a project build
     *
     * @param projectId The Project's Id
     * @param jobId     The build's Id
     * @throws IOException on gitlab api call error
     */
    public byte[] getJobTrace(Integer projectId, Integer jobId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabJob.URL + "/" + jobId + "/trace";
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Creates a Project
     *
     * @param project The project to create
     * @return The GitLab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createProject(GitlabProject project) throws IOException {
        Query query = new Query()
                .appendIf("name", project.getName())
                .appendIf("path", project.getPath())
                .appendIf("default_branch", project.getDefaultBranch())
                .appendIf("description", project.getDescription())
                .appendIf("issues_enabled", project.isIssuesEnabled())
                .appendIf("merge_requests_enabled", project.isMergeRequestsEnabled())
                .appendIf("jobs_enabled", project.isJobsEnabled())
                .appendIf("wiki_enabled", project.isWikiEnabled())
                .appendIf("snippets_enabled", project.isSnippetsEnabled())

                .appendIf("container_registry_enabled", project.isContainerRegistryEnabled())
                .appendIf("shared_runners_enabled", project.isSharedRunnersEnabled())

                .appendIf("visibility", project.getVisibility())
                .appendIf("public_jobs", project.hasPublicJobs())
                .appendIf("import_url", project.getImportUrl())

                .appendIf("only_allow_merge_if_pipeline_succeeds", project.getOnlyAllowMergeIfPipelineSucceeds())
                .appendIf("only_allow_merge_if_all_discussions_are_resolved", project.getOnlyAllowMergeIfAllDiscussionsAreResolved())
                .appendIf("lfs_enabled", project.isLfsEnabled())
                .appendIf("request_access_enabled", project.isRequestAccessEnabled())
                .appendIf("repository_storage", project.getRepositoryStorage())
                .appendIf("approvals_before_merge", project.getApprovalsBeforeMerge())
                .appendIf("printing_merge_request_link_enabled", project.isPrintingMergeRequestLinkEnabled());

        GitlabNamespace namespace = project.getNamespace();
        if (namespace != null) {
            query.appendIf("namespace_id", namespace.getId());
        }


        String tailUrl = GitlabProject.URL + query.toString();

        return dispatch().to(tailUrl, GitlabProject.class);
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
     * Creates a group Project
     *
     * @param name  The name of the project
     * @param group The group for which the project should be crated
     * @return The GitLab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createProjectForGroup(String name, GitlabGroup group) throws IOException {
        return createProjectForGroup(name, group, null);
    }

    /**
     * Creates a group Project
     *
     * @param name        The name of the project
     * @param group       The group for which the project should be crated
     * @param description The project description
     * @return The GitLab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createProjectForGroup(String name, GitlabGroup group, String description) throws IOException {
        return createProjectForGroup(name, group, description, null);
    }

    /**
     * Creates a group Project
     *
     * @param name        The name of the project
     * @param group       The group for which the project should be crated
     * @param description The project description
     * @param visibility  The project visibility level (private: 0, internal: 10, public: 20)
     * @return The GitLab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createProjectForGroup(String name, GitlabGroup group, String description, String visibility) throws IOException {
        return createProject(name, group.getId(), description, null, null, null, null, null, null, visibility, null);
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
     * @param visibility           The visibility level of the project, otherwise null indicates to use GitLab default
     * @param importUrl            The Import URL for the project, otherwise null
     * @return the Gitlab Project
     * @throws IOException on gitlab api call error
     */
    @Deprecated
    public GitlabProject createProject(String name, Integer namespaceId, String description, Boolean issuesEnabled, Boolean wallEnabled, Boolean mergeRequestsEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, Boolean publik, String visibility, String importUrl) throws IOException {
        Query query = new Query()
                .append("name", name)
                .appendIf("namespace_id", namespaceId)
                .appendIf("description", description)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("visibility", visibility)
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
     * @param visibility           The visibility level of the project, otherwise null indicates to use GitLab default
     * @param importUrl            The Import URL for the project, otherwise null
     * @return The GitLab Project
     * @throws IOException on gitlab api call error
     */
    @Deprecated
    public GitlabProject createUserProject(Integer userId, String name, String description, String defaultBranch, Boolean issuesEnabled, Boolean wallEnabled, Boolean mergeRequestsEnabled, Boolean wikiEnabled, Boolean snippetsEnabled, String visibility, String importUrl) throws IOException {
        Query query = new Query()
                .append("name", name)
                .appendIf("description", description)
                .appendIf("default_branch", defaultBranch)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("visibility", visibility)
                .appendIf("import_url", importUrl);

        String tailUrl = GitlabProject.URL + "/user/" + userId + query.toString();

        return dispatch().to(tailUrl, GitlabProject.class);
    }

    /**
     * @param namespace The namespace of the fork
     * @param projectId ProjectId of the project forked
     * @return The new Gitlab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createFork(String namespace, Integer projectId) throws IOException {
        Query query = new Query()
                .appendIf("namespace", namespace);
        String tailUrl = GitlabProject.URL + "/" + projectId + "/fork" + query.toString();
        return dispatch().to(tailUrl, GitlabProject.class);
    }

    /**
     * @param namespace     The namespace of the fork
     * @param gitlabProject The project forked
     * @return The new Gitlab Project
     * @throws IOException on gitlab api call error
     */
    public GitlabProject createFork(String namespace, GitlabProject gitlabProject) throws IOException {
        return createFork(namespace, gitlabProject.getId());
    }

    /**
     * Updates a Project
     *
     * @param projectId            The id of the project to update
     * @param name                 The name of the project
     * @param description          A description for the project, null otherwise
     * @param defaultBranch        The branch displayed in the Gitlab UI when a user navigates to the project
     * @param issuesEnabled        Whether Issues should be enabled, otherwise null indicates to use GitLab default
     * @param wallEnabled          Whether The Wall should be enabled, otherwise null indicates to use GitLab default
     * @param mergeRequestsEnabled Whether Merge Requests should be enabled, otherwise null indicates to use GitLab default
     * @param wikiEnabled          Whether a Wiki should be enabled, otherwise null indicates to use GitLab default
     * @param snippetsEnabled      Whether Snippets should be enabled, otherwise null indicates to use GitLab default
     * @param visibility           The visibility level of the project, otherwise null indicates to use GitLab default
     * @return the Gitlab Project
     * @throws IOException on gitlab api call error
     */
    @Deprecated
    public GitlabProject updateProject(
            Integer projectId,
            String name,
            String description,
            String defaultBranch,
            Boolean issuesEnabled,
            Boolean wallEnabled,
            Boolean mergeRequestsEnabled,
            Boolean wikiEnabled,
            Boolean snippetsEnabled,
            String visibility)
            throws IOException {
        Query query = new Query()
                .appendIf("name", name)
                .appendIf("description", description)
                .appendIf("default_branch", defaultBranch)
                .appendIf("issues_enabled", issuesEnabled)
                .appendIf("wall_enabled", wallEnabled)
                .appendIf("merge_requests_enabled", mergeRequestsEnabled)
                .appendIf("wiki_enabled", wikiEnabled)
                .appendIf("snippets_enabled", snippetsEnabled)
                .appendIf("visibility", visibility);

        String tailUrl = GitlabProject.URL + "/" + projectId + query.toString();

        return retrieve().method(PUT).to(tailUrl, GitlabProject.class);
    }

    /**
     * Delete a Project.
     *
     * @param projectId The id of the project to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteProject(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId);
        retrieve().method(DELETE).to(tailUrl, null);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(Serializable projectId) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_OPENED);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(Serializable projectId, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_OPENED, pagination);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(GitlabProject project) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_OPENED);
    }

    public List<GitlabMergeRequest> getOpenMergeRequests(GitlabProject project, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_OPENED, pagination);
    }

    public List<GitlabMergeRequest> getMergedMergeRequests(Serializable projectId) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_MERGED);
    }

    public List<GitlabMergeRequest> getMergedMergeRequests(Serializable projectId, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_MERGED, pagination);
    }

    public List<GitlabMergeRequest> getMergedMergeRequests(GitlabProject project) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_MERGED);
    }

    public List<GitlabMergeRequest> getMergedMergeRequests(GitlabProject project, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_MERGED, pagination);
    }

    public List<GitlabMergeRequest> getClosedMergeRequests(Serializable projectId) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_CLOSED);
    }

    public List<GitlabMergeRequest> getClosedMergeRequests(Serializable projectId, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(projectId, GitlabMergeRequest.STATUS_CLOSED, pagination);
    }

    public List<GitlabMergeRequest> getClosedMergeRequests(GitlabProject project) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_CLOSED);
    }

    public List<GitlabMergeRequest> getClosedMergeRequests(GitlabProject project, Pagination pagination) throws IOException {
        return getMergeRequestsWithStatus(project, GitlabMergeRequest.STATUS_CLOSED, pagination);
    }

    public List<GitlabMergeRequest> getMergeRequestsWithStatus(Serializable projectId, String status) throws IOException {
        return getMergeRequestsWithStatus(projectId, status, new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE));
    }

    public List<GitlabMergeRequest> getMergeRequestsWithStatus(Serializable projectId, String state, Pagination pagination) throws IOException {
        Query query = pagination.asQuery();
        query.append("state", state);
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + query;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequestsWithStatus(GitlabProject project, String status) throws IOException {
        return getMergeRequestsWithStatus(project, status, new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE));
    }

    public List<GitlabMergeRequest> getMergeRequestsWithStatus(GitlabProject project, String state, Pagination pagination) throws IOException {
        Query query = pagination.asQuery();
        query.append("state", state);
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL + query;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(Serializable projectId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(Serializable projectId, Pagination pagination) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + pagination.toString();
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(GitlabProject project) {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getMergeRequests(GitlabProject project, Pagination pagination) {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL + pagination.toString();
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    public List<GitlabMergeRequest> getAllMergeRequests(GitlabProject project) {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabMergeRequest.URL;
        return retrieve().getAll(tailUrl, GitlabMergeRequest[].class);
    }

    /**
     * Get information about the approvals present and required for a merge request
     * EE only.
     */
    public GitlabMergeRequestApprovals getMergeRequestApprovals(GitlabMergeRequest mr) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(mr.getProjectId()) +
                GitlabMergeRequest.URL + "/" + mr.getIid() + GitlabMergeRequestApprovals.URL;
        return retrieve().to(tailUrl, GitlabMergeRequestApprovals.class);
    }

    /**
     * Cherry picks a commit.
     *
     * @param projectId        The id of the project
     * @param sha              The sha of the commit
     * @param targetBranchName The branch on which the commit must be cherry-picked
     * @return the commit of the cherry-pick.
     * @throws IOException on gitlab api call error
     */
    public GitlabCommit cherryPick(Serializable projectId, String sha, String targetBranchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + sha + "/cherry_pick";
        return retrieve().with("branch", targetBranchName).to(tailUrl, GitlabCommit.class);
    }

    public GitlabCommit cherryPick(GitlabProject project, String sha, String targetBranchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/commits/" + sha + "/cherry_pick";
        return dispatch().with("branch", targetBranchName).to(tailUrl, GitlabCommit.class);
    }

    /**
     * Return Merge Request.
     *
     * @param projectId       The id of the project
     * @param mergeRequestIid The iid of the merge request
     * @return the Gitlab Merge Request
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest getMergeRequestByIid(Serializable projectId, Integer mergeRequestIid) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "/" + mergeRequestIid;
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    /**
     * Return a Merge Request including its changes.
     *
     * @param projectId      The id of the project
     * @param mergeRequestId The id of the merge request
     * @return the Gitlab Merge Request
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest getMergeRequestChanges(Serializable projectId, Integer mergeRequestId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "/" + mergeRequestId + "/changes";
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    public GitlabMergeRequest getMergeRequest(Serializable projectId, Integer mergeRequestId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "/" + mergeRequestId;
        return retrieve().to(tailUrl, GitlabMergeRequest.class);
    }

    public GitlabMergeRequest getMergeRequest(GitlabProject project, Integer mergeRequestId) throws IOException {
        return getMergeRequest(project.getId(), mergeRequestId);
    }

    /**
     * Create a new MergeRequest
     *
     * @param projectId
     * @param sourceBranch
     * @param targetBranch
     * @param assigneeId
     * @param title
     * @return GitlabMergeRequest
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest createMergeRequest(Serializable projectId, String sourceBranch, String targetBranch,
                                                 Integer assigneeId, String title) throws IOException {

        Query query = new Query()
                .appendIf("target_branch", targetBranch)
                .appendIf("source_branch", sourceBranch)
                .appendIf("assignee_id", assigneeId)
                .appendIf("title", title);

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + query.toString();

        return dispatch().to(tailUrl, GitlabMergeRequest.class);
    }


    /**
     * Updates a Merge Request
     *
     * @param projectId      The id of the project
     * @param mergeRequestId The id of the merge request to update
     * @param targetBranch   The target branch of the merge request, otherwise null to leave it untouched
     * @param assigneeId     The id of the assignee, otherwise null to leave it untouched
     * @param title          The title of the merge request, otherwise null to leave it untouched
     * @param description    The description of the merge request, otherwise null to leave it untouched
     * @param stateEvent     The state (close|reopen|merge) of the merge request, otherwise null to leave it untouched
     * @param labels         A comma separated list of labels, otherwise null to leave it untouched
     * @return the Merge Request
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest updateMergeRequest(Serializable projectId, Integer mergeRequestId, String targetBranch,
                                                 Integer assigneeId, String title, String description, String stateEvent,
                                                 String labels) throws IOException {
        Query query = new Query()
                .appendIf("target_branch", targetBranch)
                .appendIf("assignee_id", assigneeId)
                .appendIf("title", title)
                .appendIf("description", description)
                .appendIf("state_event", stateEvent)
                .appendIf("labels", labels);

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "/" + mergeRequestId + query.toString();

        return retrieve().method(PUT).to(tailUrl, GitlabMergeRequest.class);
    }

    /**
     * @param project            The Project
     * @param mergeRequestId     Merge Request ID
     * @param mergeCommitMessage optional merge commit message. Null if not set
     * @return new merge request status
     * @throws IOException on gitlab api call error
     */
    public GitlabMergeRequest acceptMergeRequest(GitlabProject project, Integer mergeRequestId, String mergeCommitMessage) throws IOException {
        return acceptMergeRequest(project.getId(), mergeRequestId, mergeCommitMessage);
    }

    public GitlabMergeRequest acceptMergeRequest(Serializable projectId, Integer mergeRequestId, String mergeCommitMessage) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMergeRequest.URL + "/" + mergeRequestId + "/merge";
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        requestor.with("id", projectId);
        requestor.with("merge_request_id", mergeRequestId);
        if (mergeCommitMessage != null)
            requestor.with("merge_commit_message", mergeCommitMessage);
        return requestor.to(tailUrl, GitlabMergeRequest.class);
    }

    /**
     * Get a Note from a Merge Request.
     *
     * @param mergeRequest The merge request
     * @param noteId       The id of the note
     * @return the Gitlab Note
     * @throws IOException on gitlab api call error
     */
    public GitlabNote getNote(GitlabMergeRequest mergeRequest, Integer noteId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getIid() +
                GitlabNote.URL + "/" + noteId;

        return retrieve().to(tailUrl, GitlabNote.class);
    }

    public List<GitlabNote> getNotes(GitlabMergeRequest mergeRequest) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getIid() +
                GitlabNote.URL;

        GitlabNote[] notes = retrieve().to(tailUrl, GitlabNote[].class);
        return Arrays.asList(notes);
    }

    public List<GitlabNote> getAllNotes(GitlabMergeRequest mergeRequest) {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getIid() +
                GitlabNote.URL + PARAM_MAX_ITEMS_PER_PAGE;

        return retrieve().getAll(tailUrl, GitlabNote[].class);
    }

    // Get a specific commit identified by the commit hash or name of a branch or tag
    // GET /projects/:id/repository/commits/:sha
    public GitlabCommit getCommit(Serializable projectId, String commitHash) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + commitHash;
        return retrieve().to(tailUrl, GitlabCommit.class);
    }

    public List<GitlabCommit> getCommits(GitlabMergeRequest mergeRequest) throws IOException {
        return getCommits(mergeRequest, new Pagination());
    }

    public List<GitlabCommit> getCommits(GitlabMergeRequest mergeRequest, Pagination pagination) throws IOException {
        Integer projectId = mergeRequest.getSourceProjectId();
        if (projectId == null) {
            projectId = mergeRequest.getProjectId();
        }

        Query query = new Query()
                .append("ref_name", mergeRequest.getSourceBranch());

        query.mergeWith(pagination.asQuery());

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) +
                "/repository" + GitlabCommit.URL + query.toString();

        GitlabCommit[] commits = retrieve().to(tailUrl, GitlabCommit[].class);
        return Arrays.asList(commits);
    }

    public List<GitlabCommit> getLastCommits(Serializable projectId) throws IOException {
        return getCommits(projectId, null, null);
    }

    public List<GitlabCommit> getLastCommits(Serializable projectId, String branchOrTag) throws IOException {
        return getCommits(projectId, null, branchOrTag);
    }

    public List<GitlabCommit> getCommits(Serializable projectId, Pagination pagination,
                                         String branchOrTag) throws IOException {
        return getCommits(projectId, null, branchOrTag, null);
    }

    public List<GitlabCommit> getCommits(Serializable projectId, Pagination pagination,
                                         String branchOrTag, String path) throws IOException {
        final Query query = new Query();
        if (branchOrTag != null) {
            query.append("ref_name", branchOrTag);
        }
        if (path != null) {
            query.append("path", path);
        }
        if (pagination != null) {
            query.mergeWith(pagination.asQuery());
        }

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) +
                "/repository" + GitlabCommit.URL + query;
        final GitlabCommit[] commits = retrieve().to(tailUrl, GitlabCommit[].class);
        return Arrays.asList(commits);
    }

    // gets all commits for a project
    public List<GitlabCommit> getAllCommits(Serializable projectId) throws IOException {
        return getAllCommits(projectId, null, null);
    }

    // gets all commits for a project
    public List<GitlabCommit> getAllCommits(Serializable projectId, String branchOrTag) throws IOException {
        return getAllCommits(projectId, null, branchOrTag);
    }

    public List<GitlabCommit> getAllCommits(Serializable projectId, Pagination pagination,
                                            String branchOrTag) throws IOException {
        final Query query = new Query();
        if (branchOrTag != null) {
            query.append("ref_name", branchOrTag);
        }

        if (pagination != null) {
            query.mergeWith(pagination.asQuery());
        }

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) +
                "/repository" + GitlabCommit.URL + query;
        return retrieve().getAll(tailUrl, GitlabCommit[].class);
    }

    // List commit diffs for a project ID and commit hash
    // GET /projects/:id/repository/commits/:sha/diff
    public List<GitlabCommitDiff> getCommitDiffs(Serializable projectId, String commitHash) throws IOException {
        return getCommitDiffs(projectId, commitHash, new Pagination());
    }

    public List<GitlabCommitDiff> getCommitDiffs(Serializable projectId, String commitHash,
                                                 Pagination pagination) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + commitHash +
                GitlabCommitDiff.URL + pagination;
        GitlabCommitDiff[] diffs = retrieve().to(tailUrl, GitlabCommitDiff[].class);
        return Arrays.asList(diffs);
    }


    // List commit diffs for a project ID and two commit hashes
    // GET /projects/:id/repository/compare
    public GitlabCommitComparison compareCommits(Serializable projectId, String commitHash1, String commitHash2) throws IOException {
        return compareCommits(projectId, commitHash1, commitHash2, new Pagination());
    }

    // List commit diffs for a project ID and two commit hashes
    // GET /projects/:id/repository/compare
    public GitlabCommitComparison compareCommits(Serializable projectId, String commitHash1, String commitHash2, Pagination pagination) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabCommitComparison.URL;
        Query query = new Query()
                .append("from", commitHash1)
                .append("to", commitHash2);
        query.mergeWith(pagination.asQuery());
        return retrieve().to(tailUrl + query, GitlabCommitComparison.class);
    }

    // List commit statuses for a project ID and commit hash
    // GET /projects/:id/repository/commits/:sha/statuses
    public List<GitlabCommitStatus> getCommitStatuses(GitlabProject project, String commitHash) throws IOException {
        return getCommitStatuses(project.getId(), commitHash, new Pagination());
    }

    public List<GitlabCommitStatus> getCommitStatuses(Serializable projectId, String commitHash) throws IOException {
        return getCommitStatuses(projectId, commitHash, new Pagination());
    }

    public List<GitlabCommitStatus> getCommitStatuses(GitlabProject project, String commitHash,
                                                      Pagination pagination) throws IOException {
        return getCommitStatuses(project.getId(), commitHash, pagination);
    }

    public List<GitlabCommitStatus> getCommitStatuses(Serializable projectId, String commitHash,
                                                      Pagination pagination) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository" + GitlabCommit.URL + "/" +
                commitHash + GitlabCommitStatus.URL + pagination;
        GitlabCommitStatus[] statuses = retrieve().to(tailUrl, GitlabCommitStatus[].class);
        return Arrays.asList(statuses);
    }

    // Submit new commit statuses for a project ID and commit hash
    // GET /projects/:id/statuses/:sha
    public GitlabCommitStatus createCommitStatus(GitlabProject project, String commitHash, String state, String ref,
                                                 String name, String targetUrl, String description) throws IOException {
        return createCommitStatus(project.getId(), commitHash, state, ref, name, targetUrl, description);
    }

    public GitlabCommitStatus createCommitStatus(Serializable projectId, String commitHash, String state, String ref,
                                                 String name, String targetUrl, String description) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabCommitStatus.URL + "/" + commitHash;
        return dispatch()
                .with("state", state)
                .with("ref", ref)
                .with("name", name)
                .with("target_url", targetUrl)
                .with("description", description)
                .to(tailUrl, GitlabCommitStatus.class);
    }

    /**
     * Get raw file content
     *
     * @param project  The Project
     * @param sha      The commit or branch name
     * @param filepath The path of the file
     * @throws IOException on gitlab api call error
     */
    public byte[] getRawFileContent(GitlabProject project, String sha, String filepath) throws IOException {
        return getRawFileContent(project.getId(), sha, filepath);
    }

    /**
     * Get raw file content
     *
     * @param projectId The Project
     * @param sha       The commit or branch name
     * @param filepath  The path of the file
     * @throws IOException on gitlab api call error
     */
    public byte[] getRawFileContent(Serializable projectId, String sha, String filepath) throws IOException {
        Query query = new Query()
                .append("ref", sha);

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/files/" + sanitizePath(filepath) + "/raw" + query.toString();
        return retrieve().to(tailUrl, byte[].class);
    }

    /**
     * Get the raw file contents for a blob by blob SHA.
     *
     * @param project The Project
     * @param sha     The commit or branch name
     * @throws IOException on gitlab api call error
     */
    public byte[] getRawBlobContent(GitlabProject project, String sha) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/blobs/" + sha + "/raw";
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
     * @param path    The path inside the repository. Used to get content of subdirectories (optional)
     * @param ref     The name of a repository branch or tag or if not given the default branch (optional)
     * @throws IOException on gitlab api call error
     */
    public List<GitlabRepositoryTree> getRepositoryTree(GitlabProject project, String path, String ref, boolean recursive) throws IOException {
        Query query = new Pagination().withPerPage(Pagination.MAX_ITEMS_PER_PAGE).asQuery()
                .appendIf("path", path)
                .appendIf("ref", ref)
                .appendIf("recursive", recursive);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository" + GitlabRepositoryTree.URL + query.toString();
        return retrieve().getAll(tailUrl, GitlabRepositoryTree[].class);
    }

    public GitlabRepositoryFile getRepositoryFile(GitlabProject project, String path, String ref) throws IOException {
        Query query = new Query()
                .append("ref", ref);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/files/" + sanitizePath(path) + query.toString();
        return retrieve().to(tailUrl, GitlabRepositoryFile.class);
    }

    /**
     * Creates a new file in the repository
     *
     * @param project    The Project
     * @param path       The file path inside the repository
     * @param branchName The name of a repository branch
     * @param commitMsg  The commit message
     * @param content    The base64 encoded content of the file
     * @throws IOException on gitlab api call error
     */
    public GitlabSimpleRepositoryFile createRepositoryFile(GitlabProject project, String path, String branchName, String commitMsg, String content) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/files/" + sanitizePath(path);
        GitlabHTTPRequestor requestor = dispatch();

        return requestor
                .with("branch", branchName)
                .with("encoding", "base64")
                .with("commit_message", commitMsg)
                .with("content", content)
                .to(tailUrl, GitlabSimpleRepositoryFile.class);
    }

    /**
     * Updates the content of an existing file in the repository
     *
     * @param project    The Project
     * @param path       The file path inside the repository
     * @param branchName The name of a repository branch
     * @param commitMsg  The commit message
     * @param content    The base64 encoded content of the file
     * @throws IOException on gitlab api call error
     */
    public GitlabSimpleRepositoryFile updateRepositoryFile(GitlabProject project, String path, String branchName, String commitMsg, String content) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/files/" + sanitizePath(path);
        GitlabHTTPRequestor requestor = retrieve().method(PUT);

        return requestor
                .with("branch", branchName)
                .with("encoding", "base64")
                .with("commit_message", commitMsg)
                .with("content", content)
                .to(tailUrl, GitlabSimpleRepositoryFile.class);
    }

    /**
     * Deletes an existing file in the repository
     *
     * @param project    The Project
     * @param path       The file path inside the repository
     * @param branchName The name of a repository branch
     * @param commitMsg  The commit message
     * @throws IOException on gitlab api call error
     */
    public void deleteRepositoryFile(GitlabProject project, String path, String branchName, String commitMsg) throws IOException {
        Query query = new Query()
                .append("branch", branchName)
                .append("commit_message", commitMsg);
        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/repository/files/" + sanitizePath(path) + query.toString();
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Update a Merge Request Note
     *
     * @param mergeRequest The merge request
     * @param noteId       The id of the note
     * @param body         The content of the note
     * @return the Gitlab Note
     * @throws IOException on gitlab api call error
     */
    public GitlabNote updateNote(GitlabMergeRequest mergeRequest, Integer noteId, String body) throws IOException {
        Query query = new Query()
                .appendIf("body", body);

        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getIid() + GitlabNote.URL + "/" + noteId + query.toString();

        return retrieve().method(PUT).to(tailUrl, GitlabNote.class);
    }

    public GitlabNote createNote(GitlabMergeRequest mergeRequest, String body) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() +
                GitlabMergeRequest.URL + "/" + mergeRequest.getIid() + GitlabNote.URL;

        return dispatch().with("body", body).to(tailUrl, GitlabNote.class);
    }

    /**
     * Delete a Merge Request Note
     *
     * @param mergeRequest The merge request
     * @param noteToDelete The note to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteNote(GitlabMergeRequest mergeRequest, GitlabNote noteToDelete) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() + GitlabMergeRequest.URL + "/"
                + mergeRequest.getIid() + GitlabNote.URL + "/" + noteToDelete.getId();
        retrieve().method(DELETE).to(tailUrl, GitlabNote.class);
    }

    public List<GitlabBranch> getBranches(Serializable projectId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBranch.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabBranch[].class);
    }

    public List<GitlabBranch> getBranches(GitlabProject project) {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabBranch[].class);
    }

    /**
     * Create Branch.
     * <a href="http://doc.gitlab.com/ce/api/branches.html#create-repository-branch">
     * Create Repository Branch Documentation
     * </a>
     *
     * @param project    The gitlab project
     * @param branchName The name of the branch to create
     * @param ref        The branch name or commit SHA to create branch from
     * @throws IOException on gitlab api call error
     */
    public void createBranch(GitlabProject project, String branchName, String ref) throws IOException {
        createBranch(project.getId(), branchName, ref);
    }

    /**
     * Create Branch.
     * <a href="http://doc.gitlab.com/ce/api/branches.html#create-repository-branch">
     * Create Repository Branch Documentation
     * </a>
     *
     * @param projectId  The id of the project
     * @param branchName The name of the branch to create
     * @param ref        The branch name or commit SHA to create branch from
     * @throws IOException on gitlab api call error
     */
    public void createBranch(Serializable projectId, String branchName, String ref) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBranch.URL;
        dispatch().with("branch", branchName).with("ref", ref).to(tailUrl, Void.class);
    }

    /**
     * Delete Branch.
     *
     * @param projectId  The id of the project
     * @param branchName The name of the branch to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteBranch(Serializable projectId, String branchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBranch.URL + '/' + sanitizePath(branchName);
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    public GitlabBranch getBranch(Serializable projectId, String branchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBranch.URL + '/' + sanitizePath(branchName);
        return retrieve().to(tailUrl, GitlabBranch.class);
    }

    public GitlabBranch getBranch(GitlabProject project, String branchName) throws IOException {
        return getBranch(project.getId(), branchName);
    }

    public void protectBranch(GitlabProject project, String branchName) throws IOException {
        protectBranchWithDeveloperOptions(project, branchName, false, false);
    }

    public void protectBranchWithDeveloperOptions(GitlabProject project, String branchName, boolean developers_can_push, boolean developers_can_merge) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + '/' + sanitizePath(branchName) + "/protect";
        final Query query = new Query()
                .append("developers_can_push", Boolean.toString(developers_can_push))
                .append("developers_can_merge", Boolean.toString(developers_can_merge));
        retrieve().method(PUT).to(tailUrl + query.toString(), Void.class);
    }

    public void unprotectBranch(GitlabProject project, String branchName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabBranch.URL + '/' + sanitizePath(branchName) + "/unprotect";
        retrieve().method(PUT).to(tailUrl, Void.class);
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
        return retrieve().to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook addProjectHook(GitlabProject project, String url) throws IOException {
        Query query = new Query()
                .append("url", url);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + query.toString();
        return dispatch().to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook addProjectHook(GitlabProject project, String url, String token) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(project.getId()) + GitlabProjectHook.URL;
        return dispatch()
                .with("url", url)
                .with("token", token)
                .to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook addProjectHook(Serializable projectId, String url, boolean pushEvents, boolean issuesEvents, boolean mergeRequestEvents, boolean noteEvents, boolean tagPushEvents, boolean sslVerification, String token) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabProjectHook.URL;

        return dispatch()
                .with("url", url)
                .with("push_events", pushEvents ? "true" : "false")
                .with("issues_events", issuesEvents ? "true" : "false")
                .with("merge_requests_events", mergeRequestEvents ? "true" : "false")
                .with("note_events", noteEvents ? "true" : "false")
                .with("tag_push_events", tagPushEvents ? "true" : "false")
                .with("enable_ssl_verification", sslVerification ? "true" : "false")
                .with("token", token)
                .to(tailUrl, GitlabProjectHook.class);
    }

    public GitlabProjectHook editProjectHook(GitlabProject project, String hookId, String url) throws IOException {
        Query query = new Query()
                .append("url", url);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId + query.toString();
        return retrieve().method(PUT).to(tailUrl, GitlabProjectHook.class);
    }

    public void deleteProjectHook(GitlabProjectHook hook) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + hook.getProjectId() + GitlabProjectHook.URL + "/" + hook.getId();
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    public void deleteProjectHook(GitlabProject project, String hookId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabProjectHook.URL + "/" + hookId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    public List<GitlabIssue> getIssues(GitlabProject project) {
        return getIssues(project.getId());
    }

    public List<GitlabIssue> getIssues(Serializable projectId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabIssue.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabIssue[].class);
    }

    public List<GitlabIssue> getIssues(GitlabProject project, GitlabMilestone milestone) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(project.getId())
                + GitlabMilestone.URL + "/" + sanitizeMilestoneId(milestone.getId())
                + GitlabIssue.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabIssue[].class);
    }

    public List<GitlabIssue> getIssues(GitlabGroup group, GitlabMilestone milestone) {
        String tailUrl = GitlabGroup.URL + "/" + sanitizeGroupId(group.getId())
                + GitlabMilestone.URL + "/" + sanitizeMilestoneId(milestone.getId())
                + GitlabIssue.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabIssue[].class);
    }

    public GitlabIssue getIssue(Serializable projectId, Integer issueId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabIssue.URL + "/" + issueId;
        return retrieve().to(tailUrl, GitlabIssue.class);
    }

    public GitlabIssueTimeStats getIssueTimeStats(Serializable projectId, Integer issueId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabIssue.URL + "/" + issueId + GitlabIssueTimeStats.URL;
        return retrieve().to(tailUrl, GitlabIssueTimeStats.class);
    }

    public GitlabIssue createIssue(int projectId, int assigneeId, Integer milestoneId, String labels,
                                   String description, String title) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL;
        GitlabHTTPRequestor requestor = dispatch();
        applyIssue(requestor, projectId, assigneeId, milestoneId, labels, description, title);

        return requestor.to(tailUrl, GitlabIssue.class);
    }

    public GitlabIssue moveIssue(Integer projectId, Integer issueId, Integer toProjectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL + "/" + issueId + "/move";
        GitlabHTTPRequestor requestor = dispatch();
        requestor.with("to_project_id", toProjectId);
        return requestor.to(tailUrl, GitlabIssue.class);
    }

    public GitlabIssue editIssue(int projectId, int issueId, int assigneeId, int milestoneId, String labels,
                                 String description, String title, GitlabIssue.Action action) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabIssue.URL + "/" + issueId;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        applyIssue(requestor, projectId, assigneeId, milestoneId, labels, description, title);

        if (action != GitlabIssue.Action.LEAVE) {
            requestor.with("state_event", action.toString().toLowerCase());
        }

        return requestor.to(tailUrl, GitlabIssue.class);
    }

    private void applyIssue(GitlabHTTPRequestor requestor, int projectId,
                            int assigneeId, Integer milestoneId, String labels, String description,
                            String title) {

        requestor.with("title", title)
                .with("description", description)
                .with("labels", labels)
                .with("milestone_id", milestoneId);

        if (assigneeId != 0) {
            requestor.with("assignee_id", assigneeId == -1 ? 0 : assigneeId);
        }
    }

    public GitlabNote getNote(GitlabIssue issue, Integer noteId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() +
                GitlabIssue.URL + "/" + issue.getIid() +
                GitlabNote.URL + "/" + noteId;
        return retrieve().to(tailUrl, GitlabNote.class);
    }

    public List<GitlabNote> getNotes(GitlabIssue issue) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/"
                + issue.getIid() + GitlabNote.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabNote[].class));
    }

    public GitlabNote createNote(Serializable projectId, Integer issueId, String message) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabIssue.URL
                + "/" + issueId + GitlabNote.URL;
        return dispatch().with("body", message).to(tailUrl, GitlabNote.class);
    }

    public GitlabNote createNote(GitlabIssue issue, String message) throws IOException {
        return createNote(String.valueOf(issue.getProjectId()), issue.getIid(), message);
    }

    /**
     * Delete an Issue Note
     *
     * @param projectId    The project id
     * @param issueId      The issue id
     * @param noteToDelete The note to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteNote(Serializable projectId, Integer issueId, GitlabNote noteToDelete) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId)
                + GitlabIssue.URL + "/" + issueId + GitlabNote.URL
                + "/" + noteToDelete.getId();
        retrieve().method(DELETE).to(tailUrl, GitlabNote.class);
    }

    /**
     * Delete an Issue Note
     *
     * @param issue        The issue
     * @param noteToDelete The note to delete
     * @throws IOException on gitlab api call error
     */
    public void deleteNote(GitlabIssue issue, GitlabNote noteToDelete) throws IOException {
        deleteNote(String.valueOf(issue.getProjectId()), issue.getId(), noteToDelete);
    }

    /**
     * Get project badges
     *
     * @param projectId The id of the project for which the badges should be retrieved
     * @return The list of badges
     *
     * @throws IOException on GitLab API call error
     */
    public List<GitlabBadge> getProjectBadges(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBadge.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabBadge[].class));
    }

    /**
     * Get project badge
     *
     * @param projectId The id of the project for which the badge should be retrieved
     * @param badgeId   The id of the badge that should be retrieved
     * @return The badge with a given id
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge getProjectBadge(Serializable projectId, Integer badgeId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBadge.URL
                + "/" + badgeId;
        return retrieve().to(tailUrl, GitlabBadge.class);
    }

    /**
     * Add project badge
     *
     * @param projectId The id of the project for which the badge should be added
     * @param linkUrl   The URL that the badge should link to
     * @param imageUrl  The URL to the badge image
     * @return The created badge
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge addProjectBadge(Serializable projectId, String linkUrl, String imageUrl) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBadge.URL;
        return dispatch().with("link_url", linkUrl)
                .with("image_url", imageUrl)
                .to(tailUrl, GitlabBadge.class);
    }

    /**
     * Edit project badge
     *
     * @param projectId The id of the project for which the badge should be edited
     * @param badgeId   The id of the badge that should be edited
     * @param linkUrl   The URL that the badge should link to
     * @param imageUrl  The URL to the badge image
     * @return The updated badge
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge editProjectBadge(Serializable projectId, Integer badgeId, String linkUrl, String imageUrl) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBadge.URL
                + "/" + badgeId;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        requestor.with("link_url", linkUrl)
                .with("image_url", imageUrl);
        return requestor.to(tailUrl, GitlabBadge.class);
    }

    /**
     * Delete project badge
     *
     * @param projectId The id of the project for which the badge should be deleted
     * @param badgeId   The id of the badge that should be deleted
     * @throws IOException on GitLab API call error
     */
    public void deleteProjectBadge(Serializable projectId, Integer badgeId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabBadge.URL
                + "/" + badgeId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Get project badges
     *
     * @param groupId The id of the group for which the badges should be retrieved
     * @return The list of badges
     *
     * @throws IOException on GitLab API call error
     */
    public List<GitlabBadge> getGroupBadges(Integer groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabBadge.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabBadge[].class));
    }

    /**
     * Get group badge
     *
     * @param groupId The id of the group for which the badge should be retrieved
     * @param badgeId The id of the badge that should be retrieved
     * @return The badge with a given id
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge getGroupBadge(Integer groupId, Integer badgeId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabBadge.URL
                + "/" + badgeId;
        return retrieve().to(tailUrl, GitlabBadge.class);
    }

    /**
     * Add group badge
     *
     * @param groupId  The id of the group for which the badge should be added
     * @param linkUrl  The URL that the badge should link to
     * @param imageUrl The URL to the badge image
     * @return The created badge
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge addGroupBadge(Integer groupId, String linkUrl, String imageUrl) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabBadge.URL;
        return dispatch().with("link_url", linkUrl)
                .with("image_url", imageUrl)
                .to(tailUrl, GitlabBadge.class);
    }

    /**
     * Edit group badge
     *
     * @param groupId  The id of the group for which the badge should be edited
     * @param badgeId  The id of the badge that should be edited
     * @param linkUrl  The URL that the badge should link to
     * @param imageUrl The URL to the badge image
     * @return The updated badge
     *
     * @throws IOException on GitLab API call error
     */
    public GitlabBadge editGroupBadge(Integer groupId, Integer badgeId, String linkUrl, String imageUrl) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabBadge.URL
                + "/" + badgeId;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        requestor.with("link_url", linkUrl)
                .with("image_url", imageUrl);
        return requestor.to(tailUrl, GitlabBadge.class);
    }

    /**
     * Delete group badge
     *
     * @param groupId The id of the group for which the badge should be deleted
     * @param badgeId The id of the badge that should be deleted
     * @throws IOException on GitLab API call error
     */
    public void deleteGroupBadge(Integer groupId, Integer badgeId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + groupId + GitlabBadge.URL
                + "/" + badgeId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Gets labels associated with a project.
     *
     * @param projectId The ID of the project.
     * @return A non-null list of labels.
     * @throws IOException on gitlab api call error
     */
    public List<GitlabLabel> getLabels(Serializable projectId)
            throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabLabel.URL;
        GitlabLabel[] labels = retrieve().to(tailUrl, GitlabLabel[].class);
        return Arrays.asList(labels);
    }

    /**
     * Gets labels associated with a project.
     *
     * @param project The project associated with labels.
     * @return A non-null list of labels.
     * @throws IOException on gitlab api call error
     */
    public List<GitlabLabel> getLabels(GitlabProject project)
            throws IOException {
        return getLabels(project.getId());
    }

    /**
     * Creates a new label.
     *
     * @param projectId The ID of the project containing the new label.
     * @param name      The name of the label.
     * @param color     The color of the label (eg #ff0000).
     * @return The newly created label.
     * @throws IOException on gitlab api call error
     */
    public GitlabLabel createLabel(
            Serializable projectId,
            String name,
            String color) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabLabel.URL;
        return dispatch().with("name", name)
                .with("color", color)
                .to(tailUrl, GitlabLabel.class);
    }

    /**
     * Creates a new label.
     *
     * @param projectId The ID of the project containing the label.
     * @param label     The label to create.
     * @return The newly created label.
     */
    public GitlabLabel createLabel(Serializable projectId, GitlabLabel label)
            throws IOException {
        String name = label.getName();
        String color = label.getColor();
        return createLabel(projectId, name, color);
    }

    /**
     * Deletes an existing label.
     *
     * @param projectId The ID of the project containing the label.
     * @param name      The name of the label to delete.
     * @throws IOException on gitlab api call error
     */
    public void deleteLabel(Serializable projectId, String name)
            throws IOException {
        Query query = new Query();
        query.append("name", name);
        String tailUrl = GitlabProject.URL + "/" +
                sanitizeProjectId(projectId) +
                GitlabLabel.URL +
                query.toString();
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Deletes an existing label.
     *
     * @param projectId The ID of the project containing the label.
     * @param label     The label to delete.
     * @throws IOException on gitlab api call error
     */
    public void deleteLabel(Serializable projectId, GitlabLabel label)
            throws IOException {
        deleteLabel(projectId, label.getName());
    }

    /**
     * Updates an existing label.
     *
     * @param projectId The ID of the project containing the label.
     * @param name      The name of the label to update.
     * @param newName   The updated name.
     * @param newColor  The updated color.
     * @return The updated, deserialized label.
     * @throws IOException on gitlab api call error
     */
    public GitlabLabel updateLabel(Serializable projectId,
                                   String name,
                                   String newName,
                                   String newColor) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabLabel.URL;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        requestor.with("name", name);
        if (newName != null) {
            requestor.with("new_name", newName);
        }
        if (newColor != null) {
            requestor = requestor.with("color", newColor);
        }
        return requestor.to(tailUrl, GitlabLabel.class);
    }

    public List<GitlabMilestone> getMilestones(GitlabProject project) throws IOException {
        return getProjectMilestones(String.valueOf(project.getId()));
    }

    public List<GitlabMilestone> getMilestones(GitlabGroup group) throws IOException {
        return getGroupMilestones(String.valueOf(group.getId()));
    }

    public List<GitlabMilestone> getProjectMilestones(Serializable projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMilestone.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabMilestone[].class));
    }

    public List<GitlabMilestone> getGroupMilestones(Serializable groupId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + sanitizeGroupId(groupId) + GitlabMilestone.URL;
        return Arrays.asList(retrieve().to(tailUrl, GitlabMilestone[].class));
    }

    /**
     * Cretaes a new project milestone.
     *
     * @param projectId   The ID of the project.
     * @param title       The title of the milestone.
     * @param description The description of the milestone. (Optional)
     * @param dueDate     The date the milestone is due. (Optional)
     * @param startDate   The start date of the milestone. (Optional)
     * @return The newly created, de-serialized milestone.
     * @throws IOException on gitlab api call error
     */
    public GitlabMilestone createMilestone(
            Serializable projectId,
            String title,
            String description,
            Date dueDate,
            Date startDate) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabMilestone.URL;
        GitlabHTTPRequestor requestor = dispatch().with("title", title);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (description != null) {
            requestor = requestor.with("description", description);
        }
        if (dueDate != null) {
            requestor = requestor.with("due_date", formatter.format(dueDate));
        }
        if (startDate != null) {
            requestor = requestor.with("start_date", formatter.format(startDate));
        }
        return requestor.to(tailUrl, GitlabMilestone.class);
    }

    /**
     * Creates a new project milestone.
     *
     * @param projectId The ID of the project.
     * @param milestone The milestone to create.
     * @return The newly created, de-serialized milestone.
     * @throws IOException on gitlab api call error
     */
    public GitlabMilestone createMilestone(
            Serializable projectId,
            GitlabMilestone milestone) throws IOException {
        String title = milestone.getTitle();
        String description = milestone.getDescription();
        Date dateDue = milestone.getDueDate();
        Date dateStart = milestone.getStartDate();
        return createMilestone(projectId, title, description, dateDue, dateStart);
    }

    /**
     * Updates an existing project milestone.
     *
     * @param projectId   The ID of the project.
     * @param milestoneId The ID of the milestone.
     * @param title       The title of the milestone. (Optional)
     * @param description The description of the milestone. (Optional)
     * @param dueDate     The date the milestone is due. (Optional)
     * @param startDate   The start date of the milestone. (Optional)
     * @param stateEvent  A value used to update the state of the milestone.
     *                    (Optional) (activate | close)
     * @return The updated, de-serialized milestone.
     * @throws IOException on gitlab api call error
     */
    public GitlabMilestone updateMilestone(
            Serializable projectId,
            int milestoneId,
            String title,
            String description,
            Date dueDate,
            Date startDate,
            String stateEvent) throws IOException {
        String tailUrl = GitlabProject.URL + "/" +
                sanitizeProjectId(projectId) +
                GitlabMilestone.URL + "/" +
                milestoneId;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (title != null) {
            requestor.with("title", title);
        }
        if (description != null) {
            requestor = requestor.with("description", description);
        }
        if (dueDate != null) {
            requestor = requestor.with("due_date", formatter.format(dueDate));
        }
        if (startDate != null) {
            requestor = requestor.with("start_date", formatter.format(startDate));
        }
        if (stateEvent != null) {
            requestor.with("state_event", stateEvent);
        }
        return requestor.to(tailUrl, GitlabMilestone.class);
    }

    /**
     * Updates an existing project milestone.
     *
     * @param projectId  The ID of the project.
     * @param edited     The already edited milestone.
     * @param stateEvent A value used to update the state of the milestone.
     *                   (Optional) (activate | close)
     * @return The updated, de-serialized milestone.
     * @throws IOException on gitlab api call error
     */
    public GitlabMilestone updateMilestone(
            Serializable projectId,
            GitlabMilestone edited,
            String stateEvent) throws IOException {
        return updateMilestone(projectId,
                edited.getId(),
                edited.getTitle(),
                edited.getDescription(),
                edited.getDueDate(),
                edited.getStartDate(),
                stateEvent);
    }

    /**
     * Updates an existing project milestone.
     *
     * @param edited     The already edited milestone.
     * @param stateEvent A value used to update the state of the milestone.
     *                   (Optional) (activate | close)
     * @return The updated, de-serialized milestone.
     * @throws IOException on gitlab api call error
     */
    public GitlabMilestone updateMilestone(
            GitlabMilestone edited,
            String stateEvent)
            throws IOException {
        return updateMilestone(edited.getProjectId(), edited, stateEvent);
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
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Updates a project member.
     *
     * @param projectId   the project id
     * @param userId      the user id
     * @param accessLevel the updated access level for the specified user
     * @return GitLabProjectMember with updated access level on success
     * @throws IOException on Gitlab API call error
     */
    public GitlabProjectMember updateProjectMember(Integer projectId, Integer userId, GitlabAccessLevel accessLevel) throws IOException {
        return updateProjectMember(projectId, userId, accessLevel, null);
    }

    /**
     * Updates a project member.
     *
     * @param projectId   the project id
     * @param userId      the user id
     * @param accessLevel the updated access level for the specified user
     * @param expiresAt   the date at which the user's membership expires at in the form YEAR-MONTH-DAY
     * @return GitLabProjectMember with updated access level on success
     * @throws IOException on Gitlab API call error
     */
    public GitlabProjectMember updateProjectMember(Integer projectId, Integer userId, GitlabAccessLevel accessLevel, String expiresAt) throws IOException {
        Query query = new Query()
                .appendIf("access_level", accessLevel)
                .appendIf("expires_at", expiresAt);
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabProjectMember.URL + "/" + userId + query.toString();
        return retrieve().method(PUT).to(tailUrl, GitlabProjectMember.class);
    }


    public List<GitlabProjectMember> getProjectMembers(GitlabProject project) throws IOException {
        return getProjectMembers(project.getId());
    }

    public List<GitlabProjectMember> getProjectMembers(GitlabProject project, Pagination pagination) throws IOException {
        return getProjectMembers(project.getId(), pagination);
    }

    public List<GitlabProjectMember> getProjectMembers(Serializable projectId) throws IOException {
        return getProjectMembers(projectId, new Pagination());
    }

    public List<GitlabProjectMember> getProjectMembers(Serializable projectId, Pagination pagination) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabProjectMember.URL + pagination.asQuery();
        return Arrays.asList(retrieve().to(tailUrl, GitlabProjectMember[].class));
    }

    /**
     * This will fail, if the given namespace is a user and not a group
     *
     * @param namespace The namespace
     * @return A list of Gitlab Project members
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProjectMember> getNamespaceMembers(GitlabNamespace namespace) throws IOException {
        return getNamespaceMembers(namespace.getId());
    }

    /**
     * This will fail, if the given namespace is a user and not a group
     *
     * @param namespaceId Namespace ID
     * @return A list of Gitlab Project members
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProjectMember> getNamespaceMembers(Integer namespaceId) throws IOException {
        String tailUrl = GitlabGroup.URL + "/" + namespaceId + GitlabProjectMember.URL;
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
        String tailUrl = GitlabGroup.URL + "/" + namespaceId + GitlabProject.URL + "/" + projectId;
        dispatch().to(tailUrl, Void.class);
    }

    /**
     * Create a new deploy key for the project
     *
     * @param targetProjectId The id of the Gitlab project
     * @param title           The title of the ssh key
     * @param key             The public key
     * @return The new GitlabSSHKey
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey createDeployKey(Integer targetProjectId, String title, String key) throws IOException {
        return createDeployKey(targetProjectId, title, key, false);
    }

    /**
     * Create a new deploy key for the project which can push.
     *
     * @param targetProjectId The id of the Gitlab project
     * @param title           The title of the ssh key
     * @param key             The public key
     * @return The new GitlabSSHKey
     * @throws IOException on gitlab api call error
     */
    public GitlabSSHKey createPushDeployKey(Integer targetProjectId, String title, String key) throws IOException {
        return createDeployKey(targetProjectId, title, key, true);
    }

    private GitlabSSHKey createDeployKey(Integer targetProjectId, String title, String key, boolean canPush) throws IOException {
        Query query = new Query()
                .append("title", title)
                .append("key", key)
                .append("can_push", Boolean.toString(canPush));

        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.DEPLOY_KEYS_URL + query.toString();

        return dispatch().to(tailUrl, GitlabSSHKey.class);
    }


    /**
     * Delete a deploy key for a project
     *
     * @param targetProjectId The id of the Gitlab project
     * @param targetKeyId     The id of the Gitlab ssh key
     * @throws IOException on gitlab api call error
     */
    public void deleteDeployKey(Integer targetProjectId, Integer targetKeyId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.DEPLOY_KEYS_URL + "/" + targetKeyId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Gets all deploy keys for a project
     *
     * @param targetProjectId The id of the Gitlab project
     * @return The list of project deploy keys
     * @throws IOException on gitlab api call error
     */
    public List<GitlabSSHKey> getDeployKeys(Integer targetProjectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + targetProjectId + GitlabSSHKey.DEPLOY_KEYS_URL;
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
        return retrieve().method(DELETE).to(tailUrl, GitlabSystemHook.class);
    }

    private String sanitizeProjectId(Serializable projectId) {
        return sanitizeId(projectId, "projectId");
    }

    private String sanitizeGroupId(Serializable groupId) {
        return sanitizeId(groupId, "groupId");
    }

    private String sanitizeMilestoneId(Serializable milestoneId) {
        return sanitizeId(milestoneId, "milestoneId");
    }

    private String sanitizeId(Serializable id, String parameterName) {
        if (!(id instanceof String) && !(id instanceof Number)) {
            throw new IllegalArgumentException(parameterName + " needs to be of type String or Number");
        }

        try {
            return URLEncoder.encode(String.valueOf(id), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException((e));
        }
    }

    private String sanitizePath(String branch) {
        try {
            return URLEncoder.encode(branch, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException((e));
        }
    }

    /**
     * Post comment to commit
     *
     * @param projectId (required) - The ID of a project
     * @param sha       (required) - The name of a repository branch or tag or if not given the default branch
     * @param note      (required) - Text of comment
     * @param path      (optional) - The file path
     * @param line      (optional) - The line number
     * @param line_type (optional) - The line type (new or old)
     * @return A CommitComment
     * @throws IOException on gitlab api call error
     * @see <a href="http://doc.gitlab.com/ce/api/commits.html#post-comment-to-commit">http://doc.gitlab.com/ce/api/commits.html#post-comment-to-commit</a>
     */
    public CommitComment createCommitComment(Serializable projectId, String sha, String note,
                                             String path, String line, String line_type) throws IOException {

        Query query = new Query()
                .append("id", projectId.toString())
                .appendIf("sha", sha)
                .appendIf("note", note)
                .appendIf("path", path)
                .appendIf("line", line)
                .appendIf("line_type", line_type);
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + sha + CommitComment.URL + query.toString();

        return dispatch().to(tailUrl, CommitComment.class);
    }

    /**
     * Get the comments of a commit
     *
     * @param projectId (required) - The ID of a project
     * @param sha       (required) - The name of a repository branch or tag or if not given the default branch
     * @return A CommitComment
     * @throws IOException on gitlab api call error
     * @see <a href="http://doc.gitlab.com/ce/api/commits.html#post-comment-to-commit">http://doc.gitlab.com/ce/api/commits.html#post-comment-to-commit</a>
     */
    public List<CommitComment> getCommitComments(Integer projectId, String sha) throws IOException {

        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + "/repository/commits/" + sha + CommitComment.URL;

        return Arrays.asList(retrieve().to(tailUrl, CommitComment[].class));
    }

    /**
     * Get a list of tags in specific project
     *
     * @param projectId
     * @return
     */
    public List<GitlabTag> getTags(Serializable projectId) {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabTag.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabTag[].class);
    }

    /**
     * Get a list of tags in specific project
     *
     * @param project
     * @return
     */
    public List<GitlabTag> getTags(GitlabProject project) {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabTag.URL + PARAM_MAX_ITEMS_PER_PAGE;
        return retrieve().getAll(tailUrl, GitlabTag[].class);
    }

    /**
     * Create tag in specific project
     *
     * @param projectId
     * @param tagName
     * @param ref
     * @param message
     * @param releaseDescription
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabTag addTag(Serializable projectId, String tagName, String ref, String message, String releaseDescription) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabTag.URL;
        return dispatch()
                .with("tag_name", tagName)
                .with("ref", ref)
                .with("message", message)
                .with("release_description", releaseDescription)
                .to(tailUrl, GitlabTag.class);
    }

    /**
     * Create tag in specific project
     *
     * @param project
     * @param tagName
     * @param ref
     * @param message
     * @param releaseDescription
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabTag addTag(GitlabProject project, String tagName, String ref, String message, String releaseDescription) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project.getId() + GitlabTag.URL;
        return dispatch()
                .with("tag_name", tagName)
                .with("ref", ref)
                .with("message", message)
                .with("release_description", releaseDescription)
                .to(tailUrl, GitlabTag.class);
    }

    /**
     * Delete tag in specific project
     *
     * @param projectId
     * @param tagName
     * @throws IOException on gitlab api call error
     */
    public void deleteTag(Serializable projectId, String tagName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + sanitizeProjectId(projectId) + GitlabTag.URL + "/" + tagName;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Delete tag in specific project
     *
     * @param project
     * @param tagName
     * @throws IOException on gitlab api call error
     */
    public void deleteTag(GitlabProject project, String tagName) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + project + GitlabTag.URL + "/" + tagName;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Get all awards for a merge request
     *
     * @param mergeRequest
     */
    public List<GitlabAward> getAllAwards(GitlabMergeRequest mergeRequest) {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() + GitlabMergeRequest.URL + "/"
                + mergeRequest.getIid() + GitlabAward.URL + PARAM_MAX_ITEMS_PER_PAGE;

        return retrieve().getAll(tailUrl, GitlabAward[].class);
    }

    /**
     * Get a specific award for a merge request
     *
     * @param mergeRequest
     * @param awardId
     * @throws IOException on gitlab api call error
     */
    public GitlabAward getAward(GitlabMergeRequest mergeRequest, Integer awardId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() + GitlabMergeRequest.URL + "/"
                + mergeRequest.getIid() + GitlabAward.URL + "/" + awardId;

        return retrieve().to(tailUrl, GitlabAward.class);
    }

    /**
     * Create an award for a merge request
     *
     * @param mergeRequest
     * @param awardName
     * @throws IOException on gitlab api call error
     */
    public GitlabAward createAward(GitlabMergeRequest mergeRequest, String awardName) throws IOException {
        Query query = new Query().append("name", awardName);
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() + GitlabMergeRequest.URL + "/"
                + mergeRequest.getIid() + GitlabAward.URL + query.toString();

        return dispatch().to(tailUrl, GitlabAward.class);
    }

    /**
     * Delete an award for a merge request
     *
     * @param mergeRequest
     * @param award
     * @throws IOException on gitlab api call error
     */
    public void deleteAward(GitlabMergeRequest mergeRequest, GitlabAward award) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + mergeRequest.getProjectId() + GitlabMergeRequest.URL + "/"
                + mergeRequest.getIid() + GitlabAward.URL + "/" + award.getId();

        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Get all awards for an issue
     *
     * @param issue
     */
    public List<GitlabAward> getAllAwards(GitlabIssue issue) {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabAward.URL + PARAM_MAX_ITEMS_PER_PAGE;

        return retrieve().getAll(tailUrl, GitlabAward[].class);
    }

    /**
     * Get a specific award for an issue
     *
     * @param issue
     * @param awardId
     * @throws IOException on gitlab api call error
     */
    public GitlabAward getAward(GitlabIssue issue, Integer awardId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabAward.URL + "/" + awardId;

        return retrieve().to(tailUrl, GitlabAward.class);
    }

    /**
     * Create an award for an issue
     *
     * @param issue
     * @param awardName
     * @throws IOException on gitlab api call error
     */
    public GitlabAward createAward(GitlabIssue issue, String awardName) throws IOException {
        Query query = new Query().append("name", awardName);
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabAward.URL + query.toString();

        return dispatch().to(tailUrl, GitlabAward.class);
    }

    /**
     * Delete an award for an issue
     *
     * @param issue
     * @param award
     * @throws IOException on gitlab api call error
     */
    public void deleteAward(GitlabIssue issue, GitlabAward award) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabAward.URL + "/" + award.getId();
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Get all awards for an issue note
     *
     * @param issue
     * @param noteId
     */
    public List<GitlabAward> getAllAwards(GitlabIssue issue, Integer noteId) {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabNote.URL + noteId + GitlabAward.URL + PARAM_MAX_ITEMS_PER_PAGE;

        return retrieve().getAll(tailUrl, GitlabAward[].class);
    }

    /**
     * Get a specific award for an issue note
     *
     * @param issue
     * @param noteId
     * @param awardId
     * @throws IOException on gitlab api call error
     */
    public GitlabAward getAward(GitlabIssue issue, Integer noteId, Integer awardId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabNote.URL + noteId + GitlabAward.URL + "/" + awardId;

        return retrieve().to(tailUrl, GitlabAward.class);
    }

    /**
     * Create an award for an issue note
     *
     * @param issue
     * @param noteId
     * @param awardName
     * @throws IOException on gitlab api call error
     */
    public GitlabAward createAward(GitlabIssue issue, Integer noteId, String awardName) throws IOException {
        Query query = new Query().append("name", awardName);
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabNote.URL + noteId + GitlabAward.URL + query.toString();

        return dispatch().to(tailUrl, GitlabAward.class);
    }

    /**
     * Delete an award for an issue note
     *
     * @param issue
     * @param noteId
     * @param award
     * @throws IOException on gitlab api call error
     */
    public void deleteAward(GitlabIssue issue, Integer noteId, GitlabAward award) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + issue.getProjectId() + GitlabIssue.URL + "/" + issue.getId()
                + GitlabNote.URL + noteId + GitlabAward.URL + "/" + award.getId();
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Gets build variables associated with a project.
     *
     * @param projectId The ID of the project.
     * @return A non-null list of variables.
     * @throws IOException on gitlab api call error
     */
    public List<GitlabBuildVariable> getBuildVariables(Integer projectId)
            throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabBuildVariable.URL;
        GitlabBuildVariable[] variables = retrieve().to(tailUrl, GitlabBuildVariable[].class);
        return Arrays.asList(variables);
    }

    /**
     * Gets build variables associated with a project.
     *
     * @param project The project associated with variables.
     * @return A non-null list of variables.
     * @throws IOException on gitlab api call error
     */
    public List<GitlabBuildVariable> getBuildVariables(GitlabProject project)
            throws IOException {
        return getBuildVariables(project.getId());
    }

    /**
     * Gets build variable associated with a project and key.
     *
     * @param projectId The ID of the project.
     * @param key       The key of the variable.
     * @return A variable.
     * @throws IOException on gitlab api call error
     */
    public GitlabBuildVariable getBuildVariable(Integer projectId, String key)
            throws IOException {
        String tailUrl = GitlabProject.URL + "/" +
                projectId +
                GitlabBuildVariable.URL + "/" +
                key;
        return retrieve().to(tailUrl, GitlabBuildVariable.class);
    }

    /**
     * Gets build variable associated with a project and key.
     *
     * @param project The project associated with the variable.
     * @return A variable.
     * @throws IOException on gitlab api call error
     */
    public GitlabBuildVariable getBuildVariable(GitlabProject project, String key)
            throws IOException {
        return getBuildVariable(project.getId(), key);
    }

    /**
     * Creates a new build variable.
     *
     * @param projectId The ID of the project containing the new variable.
     * @param key       The key of the variable.
     * @param value     The value of the variable
     * @return The newly created variable.
     * @throws IOException on gitlab api call error
     */
    public GitlabBuildVariable createBuildVariable(
            Integer projectId,
            String key,
            String value) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabBuildVariable.URL;
        return dispatch().with("key", key)
                .with("value", value)
                .to(tailUrl, GitlabBuildVariable.class);
    }

    /**
     * Creates a new variable.
     *
     * @param projectId The ID of the project containing the variable.
     * @param variable  The variable to create.
     * @return The newly created variable.
     */
    public GitlabBuildVariable createBuildVariable(Integer projectId, GitlabBuildVariable variable)
            throws IOException {
        String key = variable.getKey();
        String value = variable.getValue();
        return createBuildVariable(projectId, key, value);
    }

    /**
     * Deletes an existing variable.
     *
     * @param projectId The ID of the project containing the variable.
     * @param key       The key of the variable to delete.
     * @throws IOException on gitlab api call error
     */
    public void deleteBuildVariable(Integer projectId, String key)
            throws IOException {
        String tailUrl = GitlabProject.URL + "/" +
                projectId +
                GitlabBuildVariable.URL + "/" +
                key;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Deletes an existing variable.
     *
     * @param projectId The ID of the project containing the variable.
     * @param variable  The variable to delete.
     * @throws IOException on gitlab api call error
     */
    public void deleteBuildVariable(Integer projectId, GitlabBuildVariable variable)
            throws IOException {
        deleteBuildVariable(projectId, variable.getKey());
    }

    /**
     * Updates an existing variable.
     *
     * @param projectId The ID of the project containing the variable.
     * @param key       The key of the variable to update.
     * @param newValue  The updated value.
     * @return The updated, deserialized variable.
     * @throws IOException on gitlab api call error
     */
    public GitlabBuildVariable updateBuildVariable(Integer projectId,
                                                   String key,
                                                   String newValue) throws IOException {
        String tailUrl = GitlabProject.URL + "/" +
                projectId +
                GitlabBuildVariable.URL + "/" +
                key;
        GitlabHTTPRequestor requestor = retrieve().method(PUT);
        if (newValue != null) {
            requestor = requestor.with("value", newValue);
        }
        return requestor.to(tailUrl, GitlabBuildVariable.class);
    }

    /**
     * Returns the list of build triggers for a project.
     *
     * @param project the project
     * @return list of build triggers
     * @throws IllegalStateException if jobs are not enabled for the project
     */
    public List<GitlabTrigger> getPipelineTriggers(GitlabProject project) {
        if (!project.isJobsEnabled()) {
            // if the project has not allowed jobs, you will only get a 403 forbidden message which is
            // not helpful.
            throw new IllegalStateException("Jobs are not enabled for " + project.getNameWithNamespace());
        } else {
            return retrieve().getAll(GitlabProject.URL + "/" + project.getId() + GitlabTrigger.URL + PARAM_MAX_ITEMS_PER_PAGE, GitlabTrigger[].class);
        }
    }

    /**
     * Gets email-on-push service setup for a projectId.
     *
     * @param projectId The ID of the project containing the variable.
     * @throws IOException on gitlab api call error
     */
    public GitlabServiceEmailOnPush getEmailsOnPush(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabServiceEmailOnPush.URL;
        return retrieve().to(tailUrl, GitlabServiceEmailOnPush.class);
    }

    /**
     * Update recipients for email-on-push service for a projectId.
     *
     * @param projectId    The ID of the project containing the variable.
     * @param emailAddress The emailaddress of the recipent who is going to receive push notification.
     * @return
     * @throws IOException on gitlab api call error
     */
    public boolean updateEmailsOnPush(Integer projectId, String emailAddress) throws IOException {
        GitlabServiceEmailOnPush emailOnPush = this.getEmailsOnPush(projectId);
        GitlabEmailonPushProperties properties = emailOnPush.getProperties();
        String appendedRecipients = properties.getRecipients();
        if (appendedRecipients != "") {
            if (appendedRecipients.contains(emailAddress))
                return true;
            appendedRecipients = appendedRecipients + " " + emailAddress;
        } else
            appendedRecipients = emailAddress;

        Query query = new Query()
                .appendIf("active", true)
                .appendIf("recipients", appendedRecipients);

        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabServiceEmailOnPush.URL + query.toString();
        return retrieve().method(PUT).to(tailUrl, Boolean.class);
    }

    /**
     * Get JIRA service settings for a project.
     * https://docs.gitlab.com/ce/api/services.html#get-jira-service-settings
     *
     * @param projectId The ID of the project containing the variable.
     * @return
     * @throws IOException on gitlab api call error
     */
    public GitlabServiceJira getJiraService(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabServiceJira.URL;
        return retrieve().to(tailUrl, GitlabServiceJira.class);
    }

    /**
     * Remove all previously JIRA settings from a project.
     * https://docs.gitlab.com/ce/api/services.html#delete-jira-service
     *
     * @param projectId The ID of the project containing the variable.
     * @return
     * @throws IOException on gitlab api call error
     */
    public boolean deleteJiraService(Integer projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabServiceJira.URL;
        return retrieve().method(DELETE).to(tailUrl, Boolean.class);
    }

    /**
     * Set JIRA service for a project.
     * https://docs.gitlab.com/ce/api/services.html#create-edit-jira-service
     *
     * @param projectId    The ID of the project containing the variable.
     * @param jiraPropties
     * @return
     * @throws IOException on gitlab api call error
     */
    public boolean createOrEditJiraService(Integer projectId, GitlabJiraProperties jiraPropties) throws IOException {

        Query query = new Query()
                .appendIf("url", jiraPropties.getUrl())
                .appendIf("project_key", jiraPropties.getProjectKey());

        if (!jiraPropties.getUsername().isEmpty()) {
            query.appendIf("username", jiraPropties.getUsername());
        }

        if (!jiraPropties.getPassword().isEmpty()) {
            query.appendIf("password", jiraPropties.getPassword());
        }

        if (jiraPropties.getIssueTransitionId() != null) {
            query.appendIf("jira_issue_transition_id", jiraPropties.getIssueTransitionId());
        }


        String tailUrl = GitlabProject.URL + "/" + projectId + GitlabServiceJira.URL + query.toString();
        return retrieve().method(PUT).to(tailUrl, Boolean.class);

    }

    /**
     * Get a list of projects accessible by the authenticated user by search.
     *
     * @return A list of gitlab projects
     * @throws IOException on gitlab api call error
     */
    public List<GitlabProject> searchProjects(String search) throws IOException {
        Query query = new Query()
                .append("search", search);
        String tailUrl = GitlabProject.URL + query.toString();
        GitlabProject[] response = retrieve().to(tailUrl, GitlabProject[].class);
        return Arrays.asList(response);
    }

    /**
     * Share a project with a group.
     *
     * @param accessLevel The permissions level to grant the group.
     * @param group       The group to share with.
     * @param project     The project to be shared.
     * @param expiration  Share expiration date in ISO 8601 format: 2016-09-26 or {@code null}.
     * @throws IOException on gitlab api call error
     */
    public void shareProjectWithGroup(GitlabAccessLevel accessLevel, String expiration, GitlabGroup group, GitlabProject project) throws IOException {
        Query query = new Query()
                .append("group_id", group.getId().toString())
                .append("group_access", String.valueOf(accessLevel.accessValue))
                .appendIf("expires_at", expiration);

        String tailUrl = GitlabProject.URL + "/" + project.getId() + "/share" + query.toString();
        dispatch().to(tailUrl, Void.class);
    }

    /**
     * Delete a shared project link within a group.
     *
     * @param group   The group.
     * @param project The project.
     * @throws IOException on gitlab api call error
     */
    public void deleteSharedProjectGroupLink(GitlabGroup group, GitlabProject project) throws IOException {
        deleteSharedProjectGroupLink(group.getId(), project.getId());
    }

    /**
     * Delete a shared project link within a group.
     *
     * @param groupId   The group id number.
     * @param projectId The project id number.
     * @throws IOException on gitlab api call error
     */
    public void deleteSharedProjectGroupLink(int groupId, int projectId) throws IOException {
        String tailUrl = GitlabProject.URL + "/" + projectId + "/share/" + groupId;
        retrieve().method(DELETE).to(tailUrl, Void.class);
    }

    /**
     * Set the User-Agent header for the requests.
     *
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public GitlabVersion getVersion() throws IOException {
        return retrieve().to("version", GitlabVersion.class);
    }

    /**
     * Returns a List of all GitlabRunners.
     *
     * @return List of GitlabRunners
     * @throws IOException on gitlab api call error
     */
    public List<GitlabRunner> getRunners() throws IOException {
        return getRunnersWithPagination(GitlabRunner.RunnerScope.ALL, null);
    }

    /**
     * Returns a List of GitlabRunners.
     *
     * @param scope Can be null. Defines type of Runner to retrieve.
     * @return List of GitLabRunners
     * @throws IOException on Gitlab API call error
     */
    public List<GitlabRunner> getRunners(GitlabRunner.RunnerScope scope) throws IOException {
        return getRunnersWithPagination(scope, null);
    }

    /**
     * Returns a list of runners with perPage elements on the page number specified.
     *
     * @param scope   Can be null. Defines type of Runner to retrieve.
     * @param page    Page to get perPage number of Runners from.
     * @param perPage Number of elements to get per page.
     * @return List of GitlabRunners
     * @throws IOException on Gitlab API call error
     */
    public List<GitlabRunner> getRunnersWithPagination(GitlabRunner.RunnerScope scope, int page, int perPage) throws IOException {
        Pagination pagination = new Pagination()
                .withPage(page)
                .withPerPage(perPage);
        return getRunnersWithPagination(scope, pagination);
    }

    /**
     * Returns a list of runners with perPage elements on the page number specified.
     *
     * @param scope      Can be null. Defines type of Runner to retrieve.
     * @param pagination Can be null. Pagination to query by.
     * @return List of GitlabRunners
     * @throws IOException on Gitlab API call error
     */
    public List<GitlabRunner> getRunnersWithPagination(GitlabRunner.RunnerScope scope, Pagination pagination) throws IOException {
        StringBuilder tailUrl = new StringBuilder(GitlabRunner.URL).append("/all");
        Query query = new Query()
                .appendIf("scope", scope.getScope());

        if (pagination != null) {
            query.mergeWith(pagination.asQuery());
        }

        tailUrl.append(query.toString());
        return Arrays.asList(retrieve().method(GET).to(tailUrl.toString(), GitlabRunner[].class));
    }

    /**
     * Get details information of the runner with the specified id.
     *
     * @param id Runner id.
     * @return Extensive GitlabRunner Details.
     * @throws IOException on gitlab api call error
     */
    public GitlabRunner getRunnerDetail(int id) throws IOException {
        String tailUrl = String.format("%s/%d", GitlabRunner.URL, id);
        return retrieve().to(tailUrl, GitlabRunner.class);
    }
}
