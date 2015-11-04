package org.gitlab.api;

public enum TokenType {
    PRIVATE_TOKEN("private_token", "PRIVATE-TOKEN", "%s"),
    ACCESS_TOKEN("access_token", "Authorization", "Bearer %s");

    private final String tokenParamName;
    private final String tokenHeaderName;
    private final String tokenHeaderFormat;

    /**
     * Constructor
     *
     * @param tokenParamName    The url parameter name when using AuthMethod.URL_PARAMETER
     * @param tokenHeaderName   The header name when using AuthMethod.HEADER
     * @param tokenHeaderFormat The header format for String.format when using AuthMethod.HEADER
     */
    TokenType(String tokenParamName, String tokenHeaderName, String tokenHeaderFormat) {
        this.tokenParamName = tokenParamName;
        this.tokenHeaderName = tokenHeaderName;
        this.tokenHeaderFormat = tokenHeaderFormat;
    }

    public String getTokenParamName() {
        return tokenParamName;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public String getTokenHeaderFormat() {
        return tokenHeaderFormat;
    }
}
