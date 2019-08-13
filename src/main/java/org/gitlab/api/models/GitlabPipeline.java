package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.gitlab.api.jackson.InstantDeserializer;

import java.time.Instant;

public class GitlabPipeline {
    public static final String URL = "/pipelines";

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("ref")
    private String ref;

    @JsonProperty("status")
    private String status;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("before_sha")
    private String beforeSha;

    @JsonProperty("tag")
    private boolean tag;

    @JsonProperty("yaml_errors")
    private String yamlErrors;

    @JsonProperty("user")
    private GitlabUser user;

    @JsonProperty("created_at")
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant createdAt;

    @JsonProperty("updated_at")
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant updatedAt;

    @JsonProperty("started_at")
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant startedAt;

    @JsonProperty("finished_at")
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant finishedAt;

    @JsonProperty("committed_at")
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant committedAt;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("coverage")
    private String coverage;

    @JsonProperty("detailed_status")
    private DetailedStatus detailedStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getBeforeSha() {
        return beforeSha;
    }

    public void setBeforeSha(String beforeSha) {
        this.beforeSha = beforeSha;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getYamlErrors() {
        return yamlErrors;
    }

    public void setYamlErrors(String yamlErrors) {
        this.yamlErrors = yamlErrors;
    }

    public GitlabUser getUser() {
        return user;
    }

    public void setUser(GitlabUser user) {
        this.user = user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Instant getCommittedAt() {
        return committedAt;
    }

    public void setCommittedAt(Instant committedAt) {
        this.committedAt = committedAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public DetailedStatus getDetailedStatus() {
        return detailedStatus;
    }

    public void setDetailedStatus(DetailedStatus detailedStatus) {
        this.detailedStatus = detailedStatus;
    }

    public static class DetailedStatus {

        private String icon;

        private String text;

        private String label;

        private String group;

        private String tooltip;

        @JsonProperty("has_details")
        private String hasDetails;

        @JsonProperty("details_path")
        private String detailsPath;

        private String illustration;

        private String favicon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getTooltip() {
            return tooltip;
        }

        public void setTooltip(String tooltip) {
            this.tooltip = tooltip;
        }

        public String getHasDetails() {
            return hasDetails;
        }

        public void setHasDetails(String hasDetails) {
            this.hasDetails = hasDetails;
        }

        public String getDetailsPath() {
            return detailsPath;
        }

        public void setDetailsPath(String detailsPath) {
            this.detailsPath = detailsPath;
        }

        public String getIllustration() {
            return illustration;
        }

        public void setIllustration(String illustration) {
            this.illustration = illustration;
        }

        public String getFavicon() {
            return favicon;
        }

        public void setFavicon(String favicon) {
            this.favicon = favicon;
        }

    }

}
