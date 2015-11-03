package org.gitlab.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabMergeRequest {
    public static final String URL = "/merge_requests";

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

    private int upvotes;
    private int downvotes;

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

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("created_at")
    private Date createdAt;

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
}
