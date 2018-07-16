package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabCustomIssueTracker {
    
    public static final String URL = "/services/custom-issue-tracker/";
    
    private String title;
    private String description;
    
    @JsonProperty("project_url")
    private String projectUrl;
    
    @JsonProperty("issues_url")
    private String issuesUrl;
    
    @JsonProperty("new_issue_url")
    private String newIssueUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getIssuesUrl() {
        return issuesUrl;
    }

    public void setIssuesUrl(String issuesUrl) {
        this.issuesUrl = issuesUrl;
    }

    public String getNewIssueUrl() {
        return newIssueUrl;
    }

    public void setNewIssueUrl(String newIssueUrl) {
        this.newIssueUrl = newIssueUrl;
    }

}
