package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vitezslav Zak
 */
public class GitlabBuildVariable {
    public final static String URL = "/variables/";

    public GitlabBuildVariable() {
    }

    public GitlabBuildVariable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
