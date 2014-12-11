package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabTag {

    public static final String URL = "/repository/tags/";

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private GitlabCommit commit;

    @JsonProperty("protected")
    private Boolean protectedFlag; // protected is a keyword in java

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GitlabCommit getCommit() {
        return commit;
    }

    public void setCommit(GitlabCommit commit) {
        this.commit = commit;
    }

    public Boolean getProtected() {
        return protectedFlag;
    }

    public void setProtected(Boolean protectedFlag) {
        this.protectedFlag = protectedFlag;
    }
}
