package org.gitlab.api.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabServiceEmailOnPush {

    public static final String URL = "/services/emails-on-push/";

    private Integer id;
    private String title;
    
    @JsonProperty("created_at")
    private Date createdAt;
    
    @JsonProperty("updated_at")
    private Date updatedAt;
    
    private boolean active;
    private boolean push_events;
    private boolean issues_events;
    private boolean merge_requests_events;
    private boolean tag_push_events;
    private boolean note_events;
    private boolean build_events;
    private GitlabEmailonPushProperties properties;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPushEvents() {
        return push_events;
    }

    public void setPushEvents(boolean push_events) {
        this.push_events = push_events;
    }
    
    public boolean isIssuesEvents() {
        return issues_events;
    }

    public void setIssuesEvents(boolean issues_events) {
        this.issues_events = issues_events;
    }
    
    public boolean isMergeRequestsEvents() {
        return merge_requests_events;
    }

    public void setMergeRequestsEvents(boolean merge_requests_events) {
        this.merge_requests_events = merge_requests_events;
    }
    
    public boolean isTagPushEvents() {
        return tag_push_events;
    }

    public void setTagPushEvents(boolean tag_push_events) {
        this.tag_push_events = tag_push_events;
    }
    
    public boolean isNoteEvents() {
        return note_events;
    }

    public void setNoteEvents(boolean note_events) {
        this.note_events = note_events;
    }
    
    public boolean isBuildEvents() {
        return build_events;
    }

    public void setBuildEvents(boolean build_events) {
        this.build_events = build_events;
    }
    
    public GitlabEmailonPushProperties getProperties() {
        return properties;
    }

    public void setProperties(GitlabEmailonPushProperties properties) {
        this.properties = properties;
    }
}
