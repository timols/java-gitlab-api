package org.gitlab.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Boolean publicProject;
    private String path;

    @JsonProperty("visibility")
    private String visibility;

    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    @JsonProperty("issues_enabled")
    private Boolean issuesEnabled;

    @JsonProperty("merge_requests_enabled")
    private Boolean mergeRequestsEnabled;

    @JsonProperty("snippets_enabled")
    private Boolean snippetsEnabled;

    @JsonProperty("wall_enabled")
    private boolean wallEnabled;

    @JsonProperty("wiki_enabled")
    private Boolean wikiEnabled;

    @JsonProperty("jobs_enabled")
    private Boolean jobsEnabled;

    @JsonProperty("shared_runners_enabled")
    private Boolean sharedRunnersEnabled;

    @JsonProperty("public_jobs")
    private Boolean publicJobs;

    @JsonProperty("runners_token")
    private String runnersToken;

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

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("creator_id")
    private Integer creatorId;

    @JsonProperty("star_count")
    private Integer starCount;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("tag_list")
    private List<String> tagList;

    @JsonProperty("shared_with_groups")
    private List<GitlabProjectSharedGroup> sharedWithGroups;

    @JsonProperty("container_registry_enabled")
    private boolean containerRegistryEnabled;

    @JsonProperty("only_allow_merge_if_pipeline_succeeds")
    private Boolean onlyAllowMergeIfPipelineSucceeds;

    @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
    private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;

    @JsonProperty("lfs_enabled")
    private Boolean lfsEnabled;

    @JsonProperty("request_access_enabled")
    private Boolean requestAccessEnabled;

    @JsonProperty("repository_storage")
    private String repositoryStorage;

    @JsonProperty("approvals_before_merge")
    private Integer approvalsBeforeMerge;

    @JsonProperty("import_url")
    private String importUrl;

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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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

    public boolean isJobsEnabled() {
        return jobsEnabled;
    }

    public void setJobsEnabled(boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
    }

    public Boolean isRequestAccessEnabled() {
        return requestAccessEnabled;
    }

    public void setRequestAccessEnabled(Boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
    }

    public Boolean isLfsEnabled() {
        return lfsEnabled;
    }

    public void setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
    }

    public Boolean isSharedRunnersEnabled() {
        return sharedRunnersEnabled;
    }

    public void setSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
    }

    public boolean getOnlyAllowMergeIfPipelineSucceeds() {
        return onlyAllowMergeIfPipelineSucceeds;
    }

    public void setOnlyAllowMergeIfPipelineSucceeds(boolean onlyAllowMergeIfPipelineSucceeds) {
        this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
    }

    public boolean getOnlyAllowMergeIfAllDiscussionsAreResolved() {
        return onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public void setOnlyAllowMergeIfAllDiscussionsAreResolved(boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
        this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public boolean isContainerRegistryEnabled() {
        return containerRegistryEnabled;
    }

    public void setContainerRegistryEnabled(boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
    }

    public boolean hasPublicJobs() {
        return publicJobs;
    }

    public void setPublicJobs(boolean publicJobs) {
        this.publicJobs = publicJobs;
    }

    public String getRunnersToken() {
        return runnersToken;
    }

    public void setRunnersToken(String runnersToken) {
        this.runnersToken = runnersToken;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<GitlabProjectSharedGroup> getSharedWithGroups() {
        return sharedWithGroups;
    }

    public void setSharedWithGroups(List<GitlabProjectSharedGroup> sharedWithGroups) {
        this.sharedWithGroups = sharedWithGroups;
    }

    public String getRepositoryStorage() {
        return repositoryStorage;
    }

    public void setRepositoryStorage(String repositoryStorage) {
        this.repositoryStorage = repositoryStorage;
    }

    public Integer getApprovalsBeforeMerge() {
        return approvalsBeforeMerge;
    }

    public void setApprovalsBeforeMerge(Integer approvalsBeforeMerge) {
        this.approvalsBeforeMerge = approvalsBeforeMerge;
    }

    public String getImportUrl() {
        return importUrl;
    }

    public void setImportUrl(String importUrl) {
        this.importUrl = importUrl;
    }
}
