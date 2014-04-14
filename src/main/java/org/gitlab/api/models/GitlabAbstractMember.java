package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public abstract class GitlabAbstractMember extends GitlabUser {

	public static final String URL = "/members";

    @JsonProperty("access_level")
	private int _accessLevel;

	public GitlabAccessLevel getAccessLevel() {
		return GitlabAccessLevel.fromAccessValue(_accessLevel);
	}

	public void setAccessLevel(GitlabAccessLevel accessLevel) {
		_accessLevel = accessLevel.accessValue;
	}

}
