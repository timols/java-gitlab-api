package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class GitlabSSHKey {
    public static String KEYS_URL = "/keys";

    private Integer _id;
    private String _title;
    private String _key;

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String key) {
        _key = key;
    }
}
