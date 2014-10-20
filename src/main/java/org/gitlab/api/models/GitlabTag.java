package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabTag {

    public static final String URL = "/repository/tags/";

    @JsonProperty("name")
    private String _name;

    @JsonProperty("commit")
    private GitlabCommit _commit;

    @JsonProperty("protected")
    private Boolean _protected;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public GitlabCommit getCommit() {
        return _commit;
    }

    public void setCommit(GitlabCommit commit) {
        this._commit = commit;
    }

    public Boolean getProtected() {
        return _protected;
    }

    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }
}
