package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class GitlabPushData {

    @JsonProperty("commit_count")
    private int commitCount;

    @JsonProperty("action")
    private GitlabEvent.ActionType action;

    @JsonProperty("ref_type")
    private String refType;

    @JsonProperty("commit_from")
    private String commitFrom;

    @JsonProperty("commit_to")
    private String commitTo;

    private String ref;

    @JsonProperty("commit_title")
    private String commitTitle;

    public int getCommitCount() {
        return commitCount;
    }

    public GitlabEvent.ActionType getAction() {
        return action;
    }

    public String getRefType() {
        return refType;
    }

    public String getCommitFrom() {
        return commitFrom;
    }

    public String getCommitTo() {
        return commitTo;
    }

    public String getRef() {
        return ref;
    }

    public String getCommitTitle() {
        return commitTitle;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    public void setAction(GitlabEvent.ActionType action) {
        this.action = action;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public void setCommitFrom(String commitFrom) {
        this.commitFrom = commitFrom;
    }

    public void setCommitTo(String commitTo) {
        this.commitTo = commitTo;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setCommitTitle(String commitTitle) {
        this.commitTitle = commitTitle;
    }
}
