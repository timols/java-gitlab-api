package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public abstract class GitlabAbstractMember extends GitlabUser {

	public static final String URL = "/members";

    @JsonProperty("access_level")
	private int _accessLevel;

	public int getAccessLevel() {
		return _accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		_accessLevel = accessLevel;
	}

}
