package org.gitlab.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabCommit {

    public final static String URL = "/commits";

    private String id;
    private String title;
    private String description;

    @JsonProperty("short_id")
    private String shortId;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("committed_date")
    private Date committedDate;

    @JsonProperty("authored_date")
    private Date authoredDate;

    @JsonProperty("parent_ids")
    private List<String> parentIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public boolean equals(Object obj) {
        // we say that two commit objects are equal iff they have the same ID
        // this prevents us from having to do clever workarounds for
        // https://gitlab.com/gitlab-org/gitlab-ce/issues/759
        try {
            GitlabCommit commitObj = (GitlabCommit) obj;
            return (this.getId().compareTo(commitObj.getId()) == 0);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
