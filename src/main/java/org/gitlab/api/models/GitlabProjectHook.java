package org.gitlab.api.models;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProjectHook {

	public final static String URL = "/hooks";
	
	private String _id;
	private String _url;
	
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
    
	public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }
}
