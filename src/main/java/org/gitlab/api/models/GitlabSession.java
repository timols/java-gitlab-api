package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabSession extends GitlabUser {
	public static final String URL = "/session";
	
	@JsonProperty("private_token")
	private String _privateToken;

	public String getPrivateToken() {
		return _privateToken;
	}

	public void setPrivateToken(String privateToken) {
		_privateToken = privateToken;
	}
	
}
