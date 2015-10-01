package org.gitlab.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private boolean _blocked;

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
    private boolean _darkScheme;

    @JsonProperty("theme_id")
    private Integer _themeId;

    @JsonProperty("extern_uid")
    private String _externUid;

    @JsonProperty("is_admin")
    private boolean _isAdmin;

    @JsonProperty("can_create_group")
    private boolean _canCreateGroup;

    @JsonProperty("can_create_project")
    private boolean _canCreateProject;

    @JsonProperty("can_create_team")
    private boolean _canCreateTeam;

    @JsonProperty("avatar_url")
    private String _avatarUrl;

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String userName) {
        _username = userName;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public boolean isBlocked() {
        return _blocked;
    }

    public void setBlocked(boolean blocked) {
        _blocked = blocked;
    }

    public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }

    public String getBio() {
        return _bio;
    }

    public void setBio(String bio) {
        _bio = bio;
    }

    public String getSkype() {
        return _skype;
    }

    public void setSkype(String skype) {
        _skype = skype;
    }

    public String getLinkedin() {
        return _linkedin;
    }

    public void setLinkedin(String linkedin) {
        _linkedin = linkedin;
    }

    public String getTwitter() {
        return _twitter;
    }

    public void setTwitter(String twitter) {
        _twitter = twitter;
    }

    public boolean isDarkScheme() {
        return _darkScheme;
    }

    public void setDarkScheme(boolean darkScheme) {
        _darkScheme = darkScheme;
    }

    public Integer getThemeId() {
        return _themeId;
    }

    public void setThemeId(Integer themeId) {
        _themeId = themeId;
    }

    public String getExternUid() {
        return _externUid;
    }

    public void setExternUid(String externUid) {
        _externUid = externUid;
    }

    public String getProvider() {
        return _provider;
    }

    public void setProvider(String provider) {
        _provider = provider;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public String getExternProviderName() {
        return _externProviderName;
    }

    public void setExternProviderName(String externProviderName) {
        _externProviderName = externProviderName;
    }

    public String getWebsiteUrl() {
        return _websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        _websiteUrl = websiteUrl;
    }

    public boolean isAdmin() {
        return _isAdmin;
    }

    public void setAdmin(boolean admin) {
        _isAdmin = admin;
    }

    public boolean isCanCreateGroup() {
        return _canCreateGroup;
    }

    public void setCanCreateGroup(boolean canCreateGroup) {
        _canCreateGroup = canCreateGroup;
    }

    public boolean isCanCreateProject() {
        return _canCreateProject;
    }

    public void setCanCreateProject(boolean canCreateProject) {
        _canCreateProject = canCreateProject;
    }

    public boolean isCanCreateTeam() {
        return _canCreateTeam;
    }

    public void setCanCreateTeam(boolean canCreateTeam) {
        _canCreateTeam = canCreateTeam;
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
        return _privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this._privateToken = privateToken;
    }
}
