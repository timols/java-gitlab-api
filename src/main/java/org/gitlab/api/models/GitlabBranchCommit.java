package org.gitlab.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabBranchCommit {
    public static String URL = "/users";

    private String id;
    private String tree;
    private String message;
    private GitlabUser author;
    private GitlabUser committer;

    @JsonProperty("authored_date")
    private Date authoredDate;

    @JsonProperty("committed_date")
    private Date committedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GitlabUser getAuthor() {
        return author;
    }

    public void setAuthor(GitlabUser author) {
        this.author = author;
    }

    public GitlabUser getCommitter() {
        return committer;
    }

    public void setCommitter(GitlabUser committer) {
        this.committer = committer;
    }

    public Date getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(Date authoredDate) {
        this.authoredDate = authoredDate;
    }

    public Date getCommittedDate() {
        return committedDate;
    }

    public void setCommittedDate(Date committedDate) {
        this.committedDate = committedDate;
    }
}
