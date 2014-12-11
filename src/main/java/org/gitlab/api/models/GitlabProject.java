package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class GitlabProject {

    public static final String URL = "/projects";

    private Integer id;
    private String name;

    @JsonProperty("name_with_namespace")
    private String nameWithNamespace;

    private String description;

    @JsonProperty("default_branch")
    private String defaultBranch;

    private GitlabUser owner;
    private boolean publicProject;
    private String path;

    @JsonProperty("visibility_level")
    private Integer visibilityLevel;

    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    @JsonProperty("issues_enabled")
    private boolean issuesEnabled;

    @JsonProperty("merge_requests_enabled")
    private boolean mergeRequestsEnabled;

    @JsonProperty("snippets_enabled")
    private boolean snippetsEnabled;

    @JsonProperty("wall_enabled")
    private boolean wallEnabled;

    @JsonProperty("wiki_enabled")
    private boolean wikiEnabled;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("ssh_url_to_repo")
    private String sshUrl;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("http_url_to_repo")
    private String httpUrl;

    @JsonProperty("last_activity_at")
    private Date lastActivityAt;

    @JsonProperty("archived")
    private boolean archived;

    private GitlabNamespace namespace;

    @JsonProperty("permissions")
    private GitlabPermission permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWithNamespace() {
        return nameWithNamespace;
    }

    public void setNameWithNamespace(String nameWithNamespace) {
        this.nameWithNamespace = nameWithNamespace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public Integer getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public GitlabUser getOwner() {
        return owner;
    }

    public void setOwner(GitlabUser owner) {
        this.owner = owner;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathWithNamespace() {
        return pathWithNamespace;
    }

    public void setPathWithNamespace(String pathWithNamespace) {
        this.pathWithNamespace = pathWithNamespace;
    }

    public boolean isIssuesEnabled() {
        return issuesEnabled;
    }

    public void setIssuesEnabled(boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public boolean isMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    public void setMergeRequestsEnabled(boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public boolean isSnippetsEnabled() {
        return snippetsEnabled;
    }

    public void setSnippetsEnabled(boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public boolean isWallEnabled() {
        return wallEnabled;
    }

    public void setWallEnabled(boolean wallEnabled) {
        this.wallEnabled = wallEnabled;
    }

    public boolean isWikiEnabled() {
        return wikiEnabled;
    }

    public void setWikiEnabled(boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public GitlabNamespace getNamespace() {
        return namespace;
    }

    public void setNamespace(GitlabNamespace namespace) {
        this.namespace = namespace;
    }

    public boolean isPublic() {
        return publicProject;
    }

    public void setPublic(boolean aPublic) {
        publicProject = aPublic;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Date getLastActivityAt() {
        return lastActivityAt;
    }

    public void setLastActivityAt(Date lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public GitlabPermission getPermissions() {
        return permissions;
    }

    public void setPermissions(GitlabPermission permissions) {
        this.permissions = permissions;
    }
}
