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
    private Boolean wallEnabled;

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
    private Boolean archived;

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
    private Boolean containerRegistryEnabled;

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

    @JsonProperty("forked_from_project")
    private GitlabProject forkedFrom;

    @JsonProperty("printing_merge_request_link_enabled")
    private Boolean printingMergeRequestLinkEnabled;

    @JsonProperty("import_status")
    private String importStatus;

    @JsonProperty("initialize_with_readme")
    private Boolean initializeWithReadme;

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

    public Boolean isIssuesEnabled() {
        return issuesEnabled;
    }

    public void setIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public Boolean isMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    public void setMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public Boolean isSnippetsEnabled() {
        return snippetsEnabled;
    }

    public void setSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public Boolean isWallEnabled() {
        return wallEnabled;
    }

    public void setWallEnabled(Boolean wallEnabled) {
        this.wallEnabled = wallEnabled;
    }

    public Boolean isWikiEnabled() {
        return wikiEnabled;
    }

    public void setWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public Boolean isJobsEnabled() {
        return jobsEnabled;
    }

    public void setJobsEnabled(Boolean jobsEnabled) {
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

    public Boolean getOnlyAllowMergeIfPipelineSucceeds() {
        return onlyAllowMergeIfPipelineSucceeds;
    }

    public void setOnlyAllowMergeIfPipelineSucceeds(Boolean onlyAllowMergeIfPipelineSucceeds) {
        this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
    }

    public Boolean getOnlyAllowMergeIfAllDiscussionsAreResolved() {
        return onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public void setOnlyAllowMergeIfAllDiscussionsAreResolved(Boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
        this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public Boolean isContainerRegistryEnabled() {
        return containerRegistryEnabled;
    }

    public void setContainerRegistryEnabled(Boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
    }

    public Boolean hasPublicJobs() {
        return publicJobs;
    }

    public void setPublicJobs(Boolean publicJobs) {
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

    public Boolean isPublic() {
        return publicProject;
    }

    public void setPublic(Boolean aPublic) {
        publicProject = aPublic;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
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

    public GitlabProject getForkedFrom() {
        return forkedFrom;
    }

    public void setForkedFrom(GitlabProject forkedFrom) {
        this.forkedFrom = forkedFrom;
    }

    public Boolean isPrintingMergeRequestLinkEnabled() {
        return printingMergeRequestLinkEnabled;
    }

    public void setPrintingMergeRequestLinkEnabled(Boolean printingMergeRequestLinkEnabled) {
        this.printingMergeRequestLinkEnabled = printingMergeRequestLinkEnabled;
    }

    public Boolean isInitializeWithReadme() {
	    return initializeWithReadme;
    }

    public void setInitializeWithReadme(Boolean initializeWithReadme) {
		this.initializeWithReadme = initializeWithReadme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitlabProject that = (GitlabProject) o;

        if (id != null || that.id != null) {
            return id != null && id.equals(that.id);
        } else {
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            return namespace != null ? namespace.equals(that.namespace) : that.namespace == null;
        }
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (namespace != null ? namespace.hashCode() : 0);
        return result;
    }
}
