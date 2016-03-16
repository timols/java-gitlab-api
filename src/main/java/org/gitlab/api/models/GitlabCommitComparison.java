package org.gitlab.api.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GitlabCommitComparison {
    public final static String URL = "/repository/compare";
    @JsonProperty("commit")
    private GitlabCommit commit;
    @JsonProperty("commits")
    private List<GitlabCommit> commits;
    @JsonProperty("diffs")
    private List<GitlabCommitDiff> diffs;
    @JsonProperty("compare_same_ref")
    private Boolean compareSameRef;
    @JsonProperty("compare_timeout")
    private Boolean compareTimeout;

    public GitlabCommit getCommit() {
        return commit;
    }

    public void setCommit(GitlabCommit commit) {
        this.commit = commit;
    }

    public List<GitlabCommit> getCommits() {
        return commits;
    }

    public void setCommits(List<GitlabCommit> commits) {
        this.commits = commits;
    }

    public List<GitlabCommitDiff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<GitlabCommitDiff> diffs) {
        this.diffs = diffs;
    }

    public Boolean getCompareSameRef() {
        return compareSameRef;
    }

    public void setCompareSameRef(Boolean compareSameRef) {
        this.compareSameRef = compareSameRef;
    }

    public Boolean getCompareTimeout() {
        return compareTimeout;
    }

    public void setCompareTimeout(Boolean compareTimeout) {
        this.compareTimeout = compareTimeout;
    }
}
