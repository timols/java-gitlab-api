package org.gitlab.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabNamespace {
    public static final String URL = "/namespaces";

    private Integer id;
    private String name;
    private String path;
    private String kind;
    private String plan;

    @JsonProperty("full_path")
    private String fullPath;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("members_count_with_descendants")
    private Integer membersCountWithDescendants;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getMembersCountWithDescendants() {
        return membersCountWithDescendants;
    }

    public void setMembersCountWithDescendants(Integer membersCountWithDescendants) {
        this.membersCountWithDescendants = membersCountWithDescendants;
    }

}
