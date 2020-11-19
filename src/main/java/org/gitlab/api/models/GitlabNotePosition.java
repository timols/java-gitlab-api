package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabNotePosition {
    @JsonProperty("base_sha")
    private String baseSha;

    @JsonProperty("start_sha")
    private String startSha;

    @JsonProperty("head_sha")
    private String headSha;

    @JsonProperty("old_path")
    private String oldPath;

    @JsonProperty("new_path")
    private String newPath;

    @JsonProperty("position_type")
    private String positionType;

    @JsonProperty("old_line")
    private Integer oldLine;

    @JsonProperty("new_line")
    private Integer newLine;

    public String getBaseSha() {
        return baseSha;
    }

    public void setBaseSha(final String baseSha) {
        this.baseSha = baseSha;
    }

    public String getStartSha() {
        return startSha;
    }

    public void setStartSha(final String startSha) {
        this.startSha = startSha;
    }

    public String getHeadSha() {
        return headSha;
    }

    public void setHeadSha(final String headSha) {
        this.headSha = headSha;
    }

    public String getOldPath() {
        return oldPath;
    }

    public void setOldPath(final String oldPath) {
        this.oldPath = oldPath;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(final String newPath) {
        this.newPath = newPath;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(final String positionType) {
        this.positionType = positionType;
    }

    public Integer getOldLine() {
        return oldLine;
    }

    public void setOldLine(final Integer oldLine) {
        this.oldLine = oldLine;
    }

    public Integer getNewLine() {
        return newLine;
    }

    public void setNewLine(final Integer newLine) {
        this.newLine = newLine;
    }
}
