package org.gitlab.api.queries;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public enum OrderBy implements QueryParam {
    ID("id"),
    NAME("name"),
    PATH("path"),
    CREATED_AT("created_at"),
    UPDATED_AT("updated_at"),
    LAST_ACTIVITY_AT("last_activity_at");

    private String value;
    private static final String type = "order_by";
    OrderBy(String value) {
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
