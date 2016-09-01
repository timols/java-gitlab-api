package org.gitlab.api.queries;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public enum Sort implements QueryParam {
    ASC("asc"),
    DESC("desc");

    private String value;
    private static final String type = "sort";
    Sort(String value) {
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
