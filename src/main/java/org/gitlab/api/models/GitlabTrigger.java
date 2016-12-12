package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * [
 * {
 * "token": "7dfb8f45432193abcd54123456090f",
 * "created_at": "2016-07-08T12:33:25.151Z",
 * "updated_at": "2016-07-08T12:33:25.151Z",
 * "deleted_at": null,
 * "last_used": "2016-09-04T23:00:01.681Z"
 * }
 * ]
 */
public class GitlabTrigger {

    public static final String URL = "/triggers";

    private String token;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("deleted_at")
    private Date deletedAt;

    @JsonProperty("last_used")
    private Date lastUsed;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
