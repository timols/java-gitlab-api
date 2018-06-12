package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GitlabGroup {

    public static final String URL = "/groups";

    private Integer id;
    private String name;
    private String path;
    private String description;

    @JsonProperty("membership_lock")
    private Boolean membershipLock;

    @JsonProperty("share_with_group_lock")
    private Boolean shareWithGroupLock;

    @JsonProperty("visibility")
    private GitlabVisibility visibility;

    @JsonProperty("lfs_enabled")
    private Boolean lfsEnabled;

    @JsonProperty("request_access_enabled")
    private Boolean requestAccessEnabled;

    @JsonProperty("shared_runners_minutes_limit")
    private Integer sharedRunnersMinutesLimit;
   
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("ldap_cn")
    private String ldapCn;

    @JsonProperty("ldap_access")
    private Integer ldapAccess;

    @JsonProperty("shared_projects")
    private List<GitlabProject> sharedProjects;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("full_name")
    private String fullName;
    
    @JsonProperty("full_path")
    private String fullPath;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean isLfsEnabled() {
        return lfsEnabled;
    }
    
    public void setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getFullPath() {
        return fullPath;
    }
    
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
    
    public Boolean isRequestAccessEnabled() {
        return requestAccessEnabled;
    }
    
    public void setRequestAccessEnabled(Boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
    }

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLdapCn() {
        return ldapCn;
    }

    public void setLdapCn(String ldapCn) {
        this.ldapCn = ldapCn;
    }

    public Boolean getMembershipLock() {
        return membershipLock;
    }

    public void setMembershipLock(Boolean membershipLock) {
        this.membershipLock = membershipLock;
    }

    public Boolean getShareWithGroupLock() {
        return shareWithGroupLock;
    }

    public void setShareWithGroupLock(Boolean shareWithGroupLock) {
        this.shareWithGroupLock = shareWithGroupLock;
    }

    public GitlabVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(GitlabVisibility visibility) {
        this.visibility = visibility;
    }

    public Integer getSharedRunnersMinutesLimit() {
        return sharedRunnersMinutesLimit;
    }

    public void setSharedRunnersMinutesLimit(Integer sharedRunnersMinutesLimit) {
        this.sharedRunnersMinutesLimit = sharedRunnersMinutesLimit;
    }

    public GitlabAccessLevel getLdapAccess() {
        if (ldapAccess == null) {
            return null;
        }
        return GitlabAccessLevel.fromAccessValue(ldapAccess);
    }

    public void setLdapAccess(GitlabAccessLevel ldapGitlabAccessLevel) {
        if (ldapGitlabAccessLevel != null) {
            this.ldapAccess = ldapGitlabAccessLevel.accessValue;
        }
    }

    public List<GitlabProject> getSharedProjects() {
        return sharedProjects;
    }

    public void setSharedProjects(List<GitlabProject> sharedProjects) {
        this.sharedProjects = sharedProjects;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
