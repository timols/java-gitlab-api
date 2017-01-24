package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabProjectSharedGroup {
    @JsonProperty("group_id")
    private int groupId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("group_access_level")
    private int groupAccessLevel;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public GitlabAccessLevel getAccessLevel() {
        return GitlabAccessLevel.fromAccessValue(groupAccessLevel);
    }

    public void setAccessLevel(GitlabAccessLevel accessLevel) {
        this.groupAccessLevel = accessLevel.accessValue;
    }
}
