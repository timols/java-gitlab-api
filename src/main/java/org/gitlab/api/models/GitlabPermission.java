package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabPermission {

    @JsonProperty("project_access")
    private GitlabProjectAccessLevel projectAccess;

    @JsonProperty("group_access")
    private GitlabProjectAccessLevel groupAccess;

    public GitlabProjectAccessLevel getProjectAccess() {
        return projectAccess;
    }

    public GitlabProjectAccessLevel getProjectGroupAccess() {
        return groupAccess;
    }
}
