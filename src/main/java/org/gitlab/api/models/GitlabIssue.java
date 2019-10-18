package org.gitlab.api.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitlabIssue {

    public enum Action {
        LEAVE, CLOSE, REOPEN
    }

    public static final String STATE_CLOSED = "closed";
    public static final String STATE_OPENED = "opened";

    public static final String URL = "/issues";

    private int id;
    private int iid;

    @JsonProperty("project_id")
    private int projectId;

    private String title;
    private String description;
    private String[] labels;
    private GitlabMilestone milestone;

    private List<GitlabUser> assignees;
    private GitlabUser assignee;
    private GitlabUser author;

    @JsonProperty("user_notes_count")
    private Integer userNotesCount;

    @JsonProperty("upvotes")
    private Integer upVotes;

    @JsonProperty("downvotes")
    private Integer downVotes;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("due_date")
    private Date dueDate;

    private Boolean confidential;

    @JsonProperty("discussion_locked")
    private Boolean discussionLocked;

    @JsonProperty("time_stats")
    private GitlabIssueTimeStats timeStats;

    private String state;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("closed_at")
    private Date closedAt;

    @JsonProperty("web_url")
    private String webUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public GitlabMilestone getMilestone() {
        return milestone;
    }

    public void setMilestone(GitlabMilestone milestone) {
        this.milestone = milestone;
    }

    public List<GitlabUser> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<GitlabUser> assignees) {
        this.assignees = assignees;
    }
    public GitlabUser getAssignee() {
        return assignee;
    }

    public void setAssignee(GitlabUser assignee) {
        this.assignee = assignee;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public Integer getUserNotesCount() {
        return userNotesCount;
    }

    public void setUserNotesCount(Integer userNotesCount) {
        this.userNotesCount = userNotesCount;
    }

    public Integer getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(Integer upVotes) {
        this.upVotes = upVotes;
    }

    public Integer getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(Integer downVotes) {
        this.downVotes = downVotes;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getConfidential() {
        return confidential;
    }

    public void setConfidential(Boolean confidential) {
        this.confidential = confidential;
    }

    public Boolean getDiscussionLocked() {
        return discussionLocked;
    }

    public void setDiscussionLocked(Boolean discussionLocked) {
        this.discussionLocked = discussionLocked;
    }

    public GitlabIssueTimeStats getTimeStats() {
        return timeStats;
    }

    public void setTimeStats(GitlabIssueTimeStats timeStats) {
        this.timeStats = timeStats;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

}
