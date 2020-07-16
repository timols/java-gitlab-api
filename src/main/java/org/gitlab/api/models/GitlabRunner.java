package org.gitlab.api.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class GitlabRunner {
    public static final String URL = "/runners";

    public enum RunnerScope {
        SPECIFIC("specific"),
        SHARED("shared"),
        ACTIVE("active"),
        PAUSED("paused"),
        ONLINE("online"),
        ALL(null);

        private final String scope;

        RunnerScope(String scope) {
            this.scope = scope;
        }

        public String getScope() {
            return this.scope;
        }
    }

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("is_shared")
    private Boolean isShared;
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonProperty("revision")
    private String revision;
    @JsonProperty("contacted_at")
    private Date contactedAt;
    @JsonProperty("tag_list")
    private List<String> tagList;
    @JsonProperty("run_untagged")
    private Boolean runUntagged;
    @JsonProperty("locked")
    private Boolean locked;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("architecture")
    private String architecture;
    @JsonProperty("projects")
    private List<GitlabProject> projects;
    @JsonProperty("online")
    private Boolean online;
    @JsonProperty("status")
    private String status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getShared() {
        return this.isShared;
    }

    public void setShared(Boolean shared) {
        this.isShared = shared;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRevision() {
        return this.revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Date getContactedAt() {
        return this.contactedAt;
    }

    public void setContactedAt(Date contactedAt) {
        this.contactedAt = contactedAt;
    }

    public List<String> getTagList() {
        return this.tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public Boolean isRunUntagged() {
        return this.runUntagged;
    }

    public void setRunUntagged(boolean runUntagged) {
        this.runUntagged = runUntagged;
    }

    public Boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getArchitecture() {
        return this.architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public Boolean getOnline() {
        return this.online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
