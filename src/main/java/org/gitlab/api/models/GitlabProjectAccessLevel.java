package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabProjectAccessLevel {

    @JsonProperty("access_level")
    private int accessLevel;

    @JsonProperty("notification_level")
    private int notificationLevel;


    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }


    public int getNoficationLevel() {
        return notificationLevel;
    }

    public void setNoficationLevel(int notificationLevel) {
        this.accessLevel = notificationLevel;
    }


}
