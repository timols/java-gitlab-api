package org.gitlab.api.models;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProjectHook {

	public final static String URL = "/hooks";
	
	private String _id;
	private String _url;
	
	private Integer _projectId;

    	@JsonProperty("push_events")
    	private boolean _pushEvents;

    	@JsonProperty("issues_events")
    	private boolean _issueEvents;

    	@JsonProperty("merge_requests_events")
    	private boolean _mergeRequestsEvents;

	@JsonProperty("created_at")
    private Date _createdAt;
	
	
	public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		this._url = url;
	}
    
	public Integer getProjectId() {
        	return _projectId;
    	}

    	public void setProjectId(Integer projectId) {
        	_projectId = projectId;
    	}

    	public boolean getPushEvents() {
        	return _pushEvents;
    	}

    	public void setPushEvents(boolean pushEvents) {
        	_pushEvents = pushEvents;
    	}

    	public boolean getIssueEvents() {
        	return _issueEvents;
    	}

    	public void setIssueEvents(boolean issueEvents) {
        	_issueEvents = issueEvents;
    	}

    	public boolean isMergeRequestsEvents() {
        	return _mergeRequestsEvents;
    	}

    	public void setMergeRequestsEvents(boolean mergeRequestsEvents) {
        	_mergeRequestsEvents = mergeRequestsEvents;
    	}

	public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }
}