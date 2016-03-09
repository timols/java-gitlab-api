package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabTag {

    public final static String URL = "/repository/tags";

    @JsonProperty("commit")
    private GitlabBranchCommit commit;

    @JsonProperty("release")
    private GitlabRelease release;

    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;

    public GitlabBranchCommit getCommit() {
        return commit;
    }

    public void setCommit(GitlabBranchCommit commit) {
        this.commit = commit;
    }

    public GitlabRelease getRelease() {
        return release;
    }

    public void setRelease(GitlabRelease release) {
        this.release = release;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
