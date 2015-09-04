package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GitlabSystemHook {

    public final static String URL = "/hooks";

    private Integer id;
    private String url;

    @JsonProperty("created_at")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
