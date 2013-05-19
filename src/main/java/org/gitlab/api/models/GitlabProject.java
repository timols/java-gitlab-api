package org.gitlab.api.models;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProject {

    public static final String URL = "/projects";

    private Integer _id;
    private String _name;
    private String _description;

    @JsonProperty("default_branch")
    private String _defaultBranch;

    private GitlabUser _owner;
    private boolean _public;
    private String _path;

    @JsonProperty("path_with_namespace")
    private String _pathWithNamespace;

    @JsonProperty("issues_enabled")
    private boolean _issuesEnabled;

    @JsonProperty("merge_requests_enabled")
    private boolean _mergeRequestsEnabled;

    @JsonProperty("wall_enabled")
    private boolean _wallEnabled;

    @JsonProperty("wiki_enabled")
    private boolean _wikiEnabled;

    @JsonProperty("created_at")
    private Date _createdAt;

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
}
