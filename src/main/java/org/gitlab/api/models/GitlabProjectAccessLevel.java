package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProjectAccessLevel {

	@JsonProperty("access_level")
	private int _accessLevel;

	@JsonProperty("notification_level")
	private int _notificationLevel;

	
	public GitlabAccessLevel getAccessLevel() {
		return GitlabAccessLevel.fromAccessValue(_accessLevel);
	}

	public void setAccessLevel(GitlabAccessLevel accessLevel) {
		_accessLevel = accessLevel.accessValue;
	}

	
	public int getNoficationLevel() {
		return _notificationLevel;
	}
	
	public void  setNoficationLevel(int notificationLevel) {
		this._accessLevel=notificationLevel;
	}
	
	
}
