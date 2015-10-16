package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GitlabCommitStatus {

    public final static String URL = "/statuses";

    private String id;
    private String sha;
    private String ref;
    private String status;
    private String name;
    private String description;
    private GitlabUser author;

    @JsonProperty("target_url")
    private String targetUrl;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("started_at")
    private Date startedAt;

    @JsonProperty("finished_at")
    private Date finishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public boolean equals(Object obj) {
        // we say that two commit objects are equal iff they have the same ID
        // this prevents us from having to do clever workarounds for
        // https://gitlab.com/gitlab-org/gitlab-ce/issues/759
        try {
            GitlabCommitStatus commitObj = (GitlabCommitStatus) obj;
            return (this.getId().compareTo(commitObj.getId()) == 0);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
