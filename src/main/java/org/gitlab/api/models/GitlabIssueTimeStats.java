package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabIssueTimeStats {

    public static final String URL = "/time_stats";

    @JsonProperty("time_estimate")
    private long timeEstimate = 0;

    @JsonProperty("total_time_spent")
    private long totalTimeSpent = 0;

    @JsonProperty("human_time_estimate")
    private String humanTimeEstimate;

    @JsonProperty("human_total_time_spent")
    private String humanTotalTimeSpent;

    public static String getURL() {
        return URL;
    }

    public long getTimeEstimate() {
        return timeEstimate;
    }

    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public String getHumanTimeEstimate() {
        return humanTimeEstimate;
    }

    public String getHumanTotalTimeSpent() {
        return humanTotalTimeSpent;
    }
}
