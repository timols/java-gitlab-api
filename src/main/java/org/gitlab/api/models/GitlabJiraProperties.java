package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabJiraProperties {
	
	private String url;
	
	@JsonProperty("project_key")
	private String projectKey;
	
	private String username;
	private String password;
	
	@JsonProperty("jira_issue_transition_id")
	private Integer issueTransitionId;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIssueTransitionId() {
		return issueTransitionId;
	}
	public void setIssueTransitionId(Integer issueTransitionId) {
		this.issueTransitionId = issueTransitionId;
	}
}
