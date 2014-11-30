package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class GitlabNote {

    public static final String URL = "/notes";

    private Integer id;
    private String body;
    private String attachment;
    private GitlabUser author;

    @JsonProperty("created_at")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
