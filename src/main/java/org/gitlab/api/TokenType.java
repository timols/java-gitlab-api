package org.gitlab.api;

public enum TokenType {
	  PRIVATE_TOKEN("private_token")
	, ACCESS_TOKEN("access_token"),
	;

	private final String tokenParamName;

	TokenType(String tokenParamName) {
		this.tokenParamName = tokenParamName;
	}

	public String getTokenParamName() {
		return tokenParamName;
	}
}
