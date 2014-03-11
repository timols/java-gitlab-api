package org.gitlab.api.models;

public class GitlabProjectMember extends GitlabUser {

	public static final String URL = "/members";
	
	private int _accessLevel;

	public int getAccessLevel() {
		return _accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		_accessLevel = accessLevel;
	}
	
}
