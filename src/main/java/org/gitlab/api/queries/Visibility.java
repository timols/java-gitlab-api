package org.gitlab.api.queries;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public enum Visibility implements QueryParam {
    PUBLIC("public"),
    INTERNAL("internal"),
    PRIVATE("private");

    private String value;
    private static final String type = "visibility";
    Visibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getQueryParam() {
        return type + "=" + value;
    }
}
