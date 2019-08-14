package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitlabUser {

    public static String URL = "/users";
    public static String USERS_URL = "/users";
    public static String USER_URL = "/user"; // for sudo based ops
    public static String BLOCK_URL = "/block";
    public static String UNBLOCK_URL = "/unblock";

    private Integer _id;
    private String _username;
    private String _email;
    private String _name;
    private String _skype;
    private String _linkedin;
    private String _twitter;
    private String _provider;
    private String _state;
    private Boolean _blocked;
    private List<GitlabUserIdentity> _identities;

    @JsonProperty("private_token")
    private String _privateToken;

    @JsonProperty("color_scheme_id")
    private Integer _colorSchemeId;

    @JsonProperty("provider")
    private String _externProviderName;

    @JsonProperty("website_url")
    private String _websiteUrl;

    @JsonProperty("created_at")
    private Date _createdAt;

    @JsonProperty("bio")
    private String _bio;

    @JsonProperty("dark_scheme")
    private Boolean _darkScheme;

    @JsonProperty("theme_id")
    private Integer _themeId;

    @JsonProperty("extern_uid")
    private String _externUid;

    @JsonProperty("is_admin")
    private Boolean _isAdmin;

    @JsonProperty("can_create_group")
    private Boolean _canCreateGroup;

    @JsonProperty("can_create_project")
    private Boolean _canCreateProject;

    @JsonProperty("can_create_team")
    private Boolean _canCreateTeam;

    @JsonProperty("external")
    private boolean _external;

    @JsonProperty("avatar_url")
    private String _avatarUrl;

    @JsonProperty("last_sign_in_at")
    private Date _lastSignInAt;

    @JsonProperty("current_sign_in_at")
    private Date _currentSignInAt;

    @JsonProperty("projects_limit")
    private Integer _projectsLimit;

    @JsonProperty("last_activity_on")
    private Date _lastActivityOn;

    public Integer getId() {
        return this._id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getUsername() {
        return this._username;
    }

    public void setUsername(String userName) {
        this._username = userName;
    }

    public String getEmail() {
        return this._email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Boolean isBlocked() {
        return this._blocked;
    }

    public void setBlocked(Boolean blocked) {
       this._blocked = blocked;
    }

    public Date getCreatedAt() {
        return this._createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this._createdAt = createdAt;
    }

    public String getBio() {
        return this._bio;
    }

    public void setBio(String bio) {
        this._bio = bio;
    }

    public String getSkype() {
        return this._skype;
    }

    public void setSkype(String skype) {
        this._skype = skype;
    }

    public String getLinkedin() {
        return this._linkedin;
    }

    public void setLinkedin(String linkedin) {
        this._linkedin = linkedin;
    }

    public String getTwitter() {
        return this._twitter;
    }

    public void setTwitter(String twitter) {
        this._twitter = twitter;
    }

    public Boolean isDarkScheme() {
        return this._darkScheme;
    }

    public void setDarkScheme(boolean darkScheme) {
        this._darkScheme = darkScheme;
    }

    public Integer getThemeId() {
        return this._themeId;
    }

    public void setThemeId(Integer themeId) {
        this._themeId = themeId;
    }

    public String getExternUid() {
        return this._externUid;
    }

    public void setExternUid(String externUid) {
        this._externUid = externUid;
    }

    public String getProvider() {
        return this._provider;
    }

    public void setProvider(String provider) {
        this._provider = provider;
    }

    public String getState() {
        return this._state;
    }

    public void setState(String state) {
        this._state = state;
    }

    public String getExternProviderName() {
        return this._externProviderName;
    }

    public void setExternProviderName(String externProviderName) {
        this._externProviderName = externProviderName;
    }

    public String getWebsiteUrl() {
        return this._websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this._websiteUrl = websiteUrl;
    }

    public Boolean isAdmin() {
        return this._isAdmin;
    }

    public void setAdmin(boolean admin) {
        this._isAdmin = admin;
    }

    public Boolean isCanCreateGroup() {
        return this._canCreateGroup;
    }

    public void setCanCreateGroup(boolean canCreateGroup) {
        this._canCreateGroup = canCreateGroup;
    }

    public Boolean isCanCreateProject() {
        return this._canCreateProject;
    }

    public void setCanCreateProject(boolean canCreateProject) {
        this._canCreateProject = canCreateProject;
    }

    public Boolean isCanCreateTeam() {
        return this._canCreateTeam;
    }

    public void setCanCreateTeam(boolean canCreateTeam) {
        this._canCreateTeam = canCreateTeam;
    }

    public boolean isExternal() {
        return _external;
    }

    public void setExternal(boolean external) {
        _external = external;
    }

    public String getAvatarUrl() {
        return _avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this._avatarUrl = avatarUrl;
    }

    public Integer getColorSchemeId() {
        return _colorSchemeId;
    }

    public void setColorSchemeId(Integer colorSchemeId) {
        this._colorSchemeId = colorSchemeId;
    }

    public String getPrivateToken() {
        return this._privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this._privateToken = privateToken;
    }

    public Date getLastSignInAt() {
        return this._lastSignInAt;
    }

    public void setLastSignInAt(Date lastSignInAt) {
        this._lastSignInAt = lastSignInAt;
    }

    public Date getCurrentSignInAt() {
        return this._currentSignInAt;
    }

    public void setCurrentSignInAt(Date currentSignInAt) {
        this._currentSignInAt = currentSignInAt;
    }

    public Integer getProjectsLimit() {
        return this._projectsLimit;
    }

    public void setProjectsLimit(Integer projectsLimit) {
        this._projectsLimit = projectsLimit;
    }

    public List<GitlabUserIdentity> getIdentities() {
        return this._identities;
    }

    public void setIdentities(List<GitlabUserIdentity> identities) {
        this._identities = identities;
    }

    public Date getLastActivityOn() {
        return this._lastActivityOn;
    }

    public void setLastActivityOn(Date _lastActivityOn) {
        this._lastActivityOn = _lastActivityOn;
    }
}
