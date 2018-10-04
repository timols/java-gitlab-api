package org.gitlab.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabMergeRequest {
    public static final String URL = "/merge_requests";
    public static final String STATUS_OPENED = "opened";
    public static final String STATUS_MERGED = "merged";
    public static final String STATUS_CLOSED = "closed";

    private Integer id;
    private Integer iid;
    private String title;
    private String state;
    private String description;
    private boolean closed;
    private boolean merged;
    private GitlabUser author;
    private GitlabUser assignee;
    private GitlabMilestone milestone;

    private String[] labels;

    private List<GitlabCommitDiff> changes;

    private Integer upvotes;

    private Integer downvotes;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("target_branch")
    private String targetBranch;

    @JsonProperty("source_branch")
    private String sourceBranch;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("source_project_id")
    private Integer sourceProjectId;

    @JsonProperty("target_project_id")
    private Integer targetProjectId;

    @JsonProperty("milestone_id")
    private Integer milestoneId;

    @JsonProperty("work_in_progress")
    private Boolean workInProgress;

    @JsonProperty("merge_when_pipeline_succeeds")
    private Boolean mergeWhenPipelineSucceeds;

    @JsonProperty("merge_status")
    private String mergeStatus;

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("merge_commit_sha")
    private String mergeCommitSHA;

    @JsonProperty("user_notes_count")
    private Integer userNotesCount;

    @JsonProperty("discussion_locked")
    private Boolean discussionLocked;

    @JsonProperty("should_remove_source_branch")
    private Boolean shouldRemoveSourceBranch;

    @JsonProperty("force_remove_source_branch")
    private Boolean forceRemoveSourceBranch;

    @JsonProperty("web_url")
    private String webUrl;

    private Boolean squash;

    @JsonProperty("changes_count")
    private Integer changesCount;

    @JsonProperty("merged_by")
    private GitlabUser mergedBy;

    @JsonProperty("merged_at")
    private Date mergedAt;

    @JsonProperty("closed_by")
    private GitlabUser closedBy;

    @JsonProperty("closed_at")
    private Date closedAt;

    @JsonProperty("latest_build_started_at")
    private Date latestBuildStartedAt;

    @JsonProperty("latest_build_finished_at")
    private Date latestBuildFinishedAt;

    @JsonProperty("first_deployed_to_production_at")
    private Date firstDeployedToProductionAt;

    @JsonProperty("diff_refs")
    private DiffRefs diffRefs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Deprecated
    public Integer getMilestoneId() {
        return milestoneId;
    }

    @Deprecated
    public void setMilestoneId(Integer id) {
        milestoneId = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getTargetBranch() {
        return targetBranch;
    }

    public void setTargetBranch(String targetBranch) {
        this.targetBranch = targetBranch;
    }

    public String getSourceBranch() {
        return sourceBranch;
    }

    public void setSourceBranch(String sourceBranch) {
        this.sourceBranch = sourceBranch;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    @Deprecated
    public boolean isClosed() {
        return closed;
    }

    @Deprecated
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Deprecated
    public boolean isMerged() {
        return merged;
    }

    @Deprecated
    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public GitlabUser getAssignee() {
        return assignee;
    }

    public void setAssignee(GitlabUser assignee) {
        this.assignee = assignee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        if (state != null) {
            closed = state.equals("closed");
            merged = state.equals("merged");
        }
    }

    public GitlabMilestone getMilestone() {
        return milestone;
    }

    public void setMilestone(GitlabMilestone milestone) {
        this.milestone = milestone;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public Integer getTargetProjectId() {
        return targetProjectId;
    }

    public void setTargetProjectId(Integer targetProjectId) {
        this.targetProjectId = targetProjectId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<GitlabCommitDiff> getChanges() {
        return changes;
    }

    public void setChanges(List<GitlabCommitDiff> changes) {
        this.changes = changes;
    }

    public String getMergeCommitSHA() {
        return mergeCommitSHA;
    }

    public void setMergeCommitSHA(String mergeCommitSHA) {
        this.mergeCommitSHA = mergeCommitSHA;
    }

    public String getMergeStatus() { 
        return mergeStatus; 
    }

    public void setMergeStatus(String mergeStatus) { 
        this.mergeStatus = mergeStatus; 
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Boolean isWorkInProgress() {
        return workInProgress;
    }


    public Boolean isMergeWhenPipelineSucceeds() {
        return mergeWhenPipelineSucceeds;
    }

    public Integer getUserNotesCount() {
        return userNotesCount;
    }

    public Boolean isDiscussionLocked() {
        return discussionLocked;
    }

    public Boolean isShouldRemoveSourceBranch() {
        return shouldRemoveSourceBranch;
    }

    public boolean isForceRemoveSourceBranch() {
        return forceRemoveSourceBranch;
    }

    public Boolean isSquash() {
        return squash;
    }

    public Integer getChangesCount() {
        return changesCount;
    }

    public GitlabUser getMergedBy() {
        return mergedBy;
    }

    public Date getMergedAt() {
        return mergedAt;
    }

    public GitlabUser getClosedBy() {
        return closedBy;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public Date getLatestBuildStartedAt() {
        return latestBuildStartedAt;
    }

    public Date getLatestBuildFinishedAt() {
        return latestBuildFinishedAt;
    }

    public Date getFirstDeployedToProductionAt() {
        return firstDeployedToProductionAt;
    }

    public String getBaseSha() {
        return diffRefs == null ? null : diffRefs.baseSha;
    }

    public String getHeadSha() {
        return diffRefs == null ? null : diffRefs.headSha;
    }

    public String getStartSha() {
        return diffRefs == null ? null : diffRefs.startSha;
    }

    /**
     * Class representing the diff_refs json object, which is just a collection
     * of sha references of the merge request. It is currently not provided by
     * GitLab when fetching multiple merge requests.
     *
     * @author Patrizio Bonzani
     */
    private static class DiffRefs {

        @JsonProperty("base_sha")
        private String baseSha;

        @JsonProperty("head_sha")
        private String headSha;

        @JsonProperty("start_sha")
        private String startSha;
    }
}
