package org.gitlab.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabProjectHook {

    public final static String URL = "/hooks";

    private String id;
    private String url;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("push_events")
    private boolean pushEvents;

    @JsonProperty("issues_events")
    private boolean issueEvents;

    @JsonProperty("merge_requests_events")
    private boolean mergeRequestsEvents;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("enable_ssl_verification")
    private boolean sslVerificationEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public boolean getPushEvents() {
        return pushEvents;
    }

    public void setPushEvents(boolean pushEvents) {
        this.pushEvents = pushEvents;
    }

    public boolean getIssueEvents() {
        return issueEvents;
    }

    public void setIssueEvents(boolean issueEvents) {
        this.issueEvents = issueEvents;
    }

    public boolean isMergeRequestsEvents() {
        return mergeRequestsEvents;
    }

    public void setMergeRequestsEvents(boolean mergeRequestsEvents) {
        this.mergeRequestsEvents = mergeRequestsEvents;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSslVerificationEnabled() {
        return sslVerificationEnabled;
    }

    public void setSslVerificationEnabled(boolean sslVerificationEnabled) {
        this.sslVerificationEnabled = sslVerificationEnabled;
    }

}
