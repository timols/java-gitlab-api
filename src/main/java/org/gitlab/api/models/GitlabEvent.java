package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class GitlabEvent {

    public final static String URL = "/events";

    private String title;

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("action_name")
    private String actionName;

    // nullable
    @JsonProperty("target_id")
    private Integer targetId;

    // nullable
    @JsonProperty("target_iid")
    private Integer targetIid;

    @JsonProperty("target_type")
    private TargetType targetType;

    // It's not clear if this is nullable
    @JsonProperty("author_id")
    private Integer authorId;

    // nullable
    @JsonProperty("target_title")
    private String targetTitle;

    @JsonProperty("created_at")
    private Date createdAt;

    // see above re "author"
    private GitlabUser author;

    // see above re "author"
    @JsonProperty("author_username")
    private String authorUsername;

    @JsonProperty("push_data")
    private GitlabPushData pushData;

    public String getTitle() {
        return title;
    }

    public String getProjectId() {
        return projectId;
    }

    /**
     * It would be reasonable to expect that this matches up with
     * ActionType, below, but it doesn't.  The action type "pushed" is
     * spelled "pushed to" in action_name (but it's spelled "pushed"
     * in GitlabPushData).
     */
    public String getActionName() {
        return actionName;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Integer getTargetIid() {
        return targetIid;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    /** See() {@link #getAuthor()} for note */
    public Integer getAuthorId() {
        return authorId;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * For many events, this seem to have nothing to do with any
     * "author", but is in fact the user who performed the action.
     * e.g. a push's "author" is not necessarily the author or
     * committer of any commit, but the user doing the pushing.
     */
    public GitlabUser getAuthor() {
        return author;
    }

    /** See {@link #getAuthor()} for note */
    public String getAuthorUsername() {
        return authorUsername;
    }

    public GitlabPushData getPushData() {
        return pushData;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public void setTargetIid(Integer targetIid) {
        this.targetIid = targetIid;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setPushData(GitlabPushData pushData) {
        this.pushData = pushData;
    }

    public enum ActionType {
        created,
        updated,
        closed,
        reopened,
        pushed,
        commented,
        merged,
        joined,
        left,
        destroyed,
        expired
    }

    public enum TargetType {
        issue,
        milestone,
        merge_request,
        note,
        project,
        snippet,
        user
    }
}
