package org.gitlab.api.models;

import org.gitlab.api.models.GitlabBranchCommit;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabBranch {
    public static String URL = "/repository/branches/";

    private String _name;
    private GitlabBranchCommit _commit;
    private boolean _protected;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public GitlabBranchCommit getCommit() {
        return _commit;
    }

    public void setCommit(GitlabBranchCommit commit) {
        _commit = commit;
    }

    public boolean getProtected() {
        return _protected;
    }

    public void setProtected(boolean isProtected) {
        _protected = isProtected;
    }
}