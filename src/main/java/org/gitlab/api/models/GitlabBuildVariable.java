package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vitezslav Zak
 */
public class GitlabBuildVariable {
    public final static String URL = "/variables";

    public GitlabBuildVariable() {
    }

    public GitlabBuildVariable(String key, String value) {
        this.key = key;
        this.value = value;
        this.variableType = VariableType.env_var;
    }

    public GitlabBuildVariable(String key, String value, VariableType variableType) {
        this.key = key;
        this.value = value;
        this.variableType = variableType;
    }

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @JsonProperty("variable_type")
    private VariableType variableType;

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

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public enum VariableType {
        env_var,
        file
    }

}
