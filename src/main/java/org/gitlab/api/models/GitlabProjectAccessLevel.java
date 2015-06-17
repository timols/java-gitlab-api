package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabProjectAccessLevel {

    @JsonProperty("access_level")
    private int accessLevel;

    @JsonProperty("notification_level")
    private int notificationLevel;


    public GitlabAccessLevel getAccessLevel() {
        return GitlabAccessLevel.fromAccessValue(accessLevel);
    }

    public void setAccessLevel(GitlabAccessLevel accessLevel) {
        this.accessLevel = accessLevel.accessValue;
    }


    public int getNoficationLevel() {
        return notificationLevel;
    }

    public void setNoficationLevel(int notificationLevel) {
        this.accessLevel = notificationLevel;
    }


}
