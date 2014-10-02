package org.gitlab.api.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProject {

    public static final String URL = "/projects";

    private Integer _id;
    private String _name;

    @JsonProperty("name_with_namespace")
    private String _nameWithNamespace;

    private String _description;

    @JsonProperty("default_branch")
    private String _defaultBranch;

    private GitlabUser _owner;
    private boolean _public;
    private String _path;

    @JsonProperty("visibility_level")
    private Integer _visibilityLevel;

    @JsonProperty("path_with_namespace")
    private String _pathWithNamespace;

    @JsonProperty("issues_enabled")
    private boolean _issuesEnabled;

    @JsonProperty("merge_requests_enabled")
    private boolean _mergeRequestsEnabled;

    @JsonProperty("snippets_enabled")
    private boolean _snippetsEnabled;

    @JsonProperty("wall_enabled")
    private boolean _wallEnabled;

    @JsonProperty("wiki_enabled")
    private boolean _wikiEnabled;

    @JsonProperty("created_at")
    private Date _createdAt;

    @JsonProperty("ssh_url_to_repo")
    private String _sshUrl;

    @JsonProperty("web_url")
    private String _webUrl;

    @JsonProperty("http_url_to_repo")
    private String _httpUrl;

    @JsonProperty("last_activity_at")
    private Date _lastActivityAt;

    @JsonProperty("archived")
    private boolean _archived;

    private GitlabNamespace _namespace;

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getNameWithNamespace() {
        return _nameWithNamespace;
    }

    public void setNameWithNamespace(String nameWithNamespace) {
        this._nameWithNamespace = nameWithNamespace;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getDefaultBranch() {
        return _defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        _defaultBranch = defaultBranch;
    }

    public Integer getVisibilityLevel() {
        return _visibilityLevel;
    }

    public void setVisibilityLevel(Integer visibilityLevel) {
        this._visibilityLevel = visibilityLevel;
    }

    public GitlabUser getOwner() {
        return _owner;
    }

    public void setOwner(GitlabUser owner) {
        _owner = owner;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        _path = path;
    }

    public String getPathWithNamespace() {
        return _pathWithNamespace;
    }

    public void setPathWithNamespace(String pathWithNamespace) {
        _pathWithNamespace = pathWithNamespace;
    }

    public boolean isIssuesEnabled() {
        return _issuesEnabled;
    }

    public void setIssuesEnabled(boolean issuesEnabled) {
        _issuesEnabled = issuesEnabled;
    }

    public boolean isMergeRequestsEnabled() {
        return _mergeRequestsEnabled;
    }

    public void setMergeRequestsEnabled(boolean mergeRequestsEnabled) {
        _mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public boolean isSnippetsEnabled() {
        return _snippetsEnabled;
    }

    public void setSnippetsEnabled(boolean snippetsEnabled) {
        this._snippetsEnabled = snippetsEnabled;
    }

    public boolean isWallEnabled() {
        return _wallEnabled;
    }

    public void setWallEnabled(boolean wallEnabled) {
        _wallEnabled = wallEnabled;
    }

    public boolean isWikiEnabled() {
        return _wikiEnabled;
    }

    public void setWikiEnabled(boolean wikiEnabled) {
        _wikiEnabled = wikiEnabled;
    }

    public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }

    public String getSshUrl() {
        return _sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        _sshUrl = sshUrl;
    }

    public String getWebUrl() {
        return _webUrl;
    }

    public void setWebUrl(String webUrl) {
        _webUrl = webUrl;
    }

    public String getHttpUrl() {
        return _httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        _httpUrl = httpUrl;
    }

    public GitlabNamespace getNamespace() {
        return _namespace;
    }

    public void setNamespace(GitlabNamespace namespace) {
        _namespace = namespace;
    }

    public boolean isPublic() {
        return _public;
    }

    public void setPublic(boolean aPublic) {
        _public = aPublic;
    }

    public boolean isArchived() {
        return _archived;
    }

    public void setArchived(boolean archived) {
        _archived = archived;
    }

    public Date getLastActivityAt() {
        return _lastActivityAt;
    }

    public void setLastActivityAt(Date lastActivityAt) {
        _lastActivityAt = lastActivityAt;
    }
}
