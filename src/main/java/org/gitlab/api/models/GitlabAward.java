package org.gitlab.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabAward {

    public static final String THUMBSUP = "thumbsup";

    public static final String THUMBSDOWN = "thumbsdown";

    public static final String ISSUE = "Issue";

    public static final String NOTE = "Note";
    
	public static final String URL = "/award_emoji";

    private Integer id;
    
    private String name;
    
    private GitlabUser user;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("awardable_id")
    private Integer awardableId;

    @JsonProperty("awardable_type")
    private String awardableType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setBody(String body) {
        this.name = body;
    }

    public GitlabUser getUser() {
        return user;
    }

    public void setUser(GitlabUser user) {
        this.user = user;
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
    
    public Integer getAwardableId() {
        return awardableId;
    }

    public void setAwardableId(Integer awardableId) {
        this.awardableId = awardableId;
    }
    
    public String getAwardableType() {
        return awardableType;
    }

    public void setAwardableType(String awardableType) {
        this.awardableType = awardableType;
    }
}
