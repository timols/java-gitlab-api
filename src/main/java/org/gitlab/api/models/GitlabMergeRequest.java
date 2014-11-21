package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class GitlabMergeRequest {
    public static final String URL = "/merge_requests";

    private Integer _id;
    private Integer _iid;

    @JsonProperty("project_id")
    private Integer _projectId;

    private String _title;
    private String _description;
    private String _state;

    @JsonProperty("created_at")
    private Date _createdAt;

    @JsonProperty("updated_at")
    private Date _updatedAt;

    @JsonProperty("target_branch")
    private String _targetBranch;

    @JsonProperty("source_branch")
    private String _sourceBranch;

    private int _upvotes;
    private int _downvotes;
    private GitlabUser _author;
    private GitlabUser _assignee;

    @JsonProperty("source_project_id")
    private Integer _sourceProjectId;

    @JsonProperty("target_project_id")
    private Integer _targetProjectId;

    private String[] _labels;

    private GitlabMilestone _milestone;

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public Integer getIid() {
        return _iid;
    }

    public void setIid(Integer iid) {
        _iid = iid;
    }

    public Integer getProjectId() {
        return _projectId;
    }

    public void setProjectId(Integer projectId) {
        _projectId = projectId;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String d) {
        _description = d;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return _updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        _updatedAt = updatedAt;
    }

    public String getTargetBranch() {
        return _targetBranch;
    }

    public void setTargetBranch(String targetBranch) {
        _targetBranch = targetBranch;
    }

    public String getSourceBranch() {
        return _sourceBranch;
    }

    public void setSourceBranch(String sourceBranch) {
        _sourceBranch = sourceBranch;
    }

    public int getUpvotes() {
        return _upvotes;
    }

    public void setUpvotes(int upvotes) {
        _upvotes = upvotes;
    }

    public int getDownvotes() {
        return _downvotes;
    }

    public void setDownvotes(int downvotes) {
        _downvotes = downvotes;
    }

    public GitlabUser getAuthor() {
        return _author;
    }

    public void setAuthor(GitlabUser author) {
        _author = author;
    }

    public GitlabUser getAssignee() {
        return _assignee;
    }

    public void setAssignee(GitlabUser assignee) {
        _assignee = assignee;
    }

    public Integer getSourceProjectId() {
        return _sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        _sourceProjectId = sourceProjectId;
    }

    public Integer getTargetProjectId() {
        return _targetProjectId;
    }

    public void setTargetProjectId(int targetProjectId) {
        _targetProjectId = targetProjectId;
    }

    public String[] getLabels() {
        return _labels;
    }

    public void setLabels(String[] labels) {
        _labels = labels;
    }

    public GitlabMilestone getMilestone() {
        return _milestone;
    }

    public void setMilestone(GitlabMilestone milestone) {
        _milestone = milestone;
    }
}
