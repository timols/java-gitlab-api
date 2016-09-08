package org.gitlab.api.models;

public class GitlabEmailonPushProperties {

    private Integer disable_diffs;
    private String recipients;
    private Integer send_from_committer_email;
    
    public Integer getDisableDiffs() {
        return disable_diffs;
    }

    public void setDisableDiffs(Integer disable_diffs) {
        this.disable_diffs = disable_diffs;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public Integer getSendFromCommitterEmail() {
        return send_from_committer_email;
    }

    public void setSendFromCommitterEmail(Integer send_from_committer_email) {
        this.send_from_committer_email = send_from_committer_email;
    }
}

