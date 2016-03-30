package org.gitlab.api.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabBuild {

    public final static String URL = "/builds";

    private GitlabCommit commit;
    private Float coverage;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("download_url")
    private String downloadUrl;
    @JsonProperty("finishedAt")
    private String finishedAt;
    private Integer id;
    private String name;
    private String ref;
    private GitlabRunner runner;
    private String stage;
    @JsonProperty("started_at")
    private String startedAt;
    private String status;
    private Boolean tag;
    private GitlabUser user;

    public GitlabUser getUser() {
        return user;
    }

    public void setUser(GitlabUser user) {
        this.user = user;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public GitlabRunner getRunner() {
        return runner;
    }

    public void setRunner(GitlabRunner runner) {
        this.runner = runner;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Float getCoverage() {
        return coverage;
    }

    public void setCoverage(Float coverage) {
        this.coverage = coverage;
    }

    public GitlabCommit getCommit() {
        return commit;
    }

    public void setCommit(GitlabCommit commit) {
        this.commit = commit;
    }
}
