package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

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

    @JsonProperty("target_branch")
    private String targetBranch;

    @JsonProperty("source_branch")
    private String sourceBranch;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("source_project_id")
    private Integer sourceProjectId;

    @JsonProperty("milestone_id")
    private Integer milestoneId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isMerged() {
        return merged;
    }

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
}
