package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabCommitDiff {

    public final static String URL = "/diff";

    @JsonProperty("diff")
    private String _diff;

    @JsonProperty("new_path")
    private String _newPath;

    @JsonProperty("old_path")
    private String _oldPath;

    @JsonProperty("a_mode")
    private String _aMode;

    @JsonProperty("b_mode")
    private String _bMode;

    @JsonProperty("new_file")
    private boolean _newFile;

    @JsonProperty("renamed_file")
    private boolean _renamedFile;

    @JsonProperty("deleted_file")
    private boolean _deletedFile;

    public String getDiff() {
        return _diff;
    }

    public void setDiff(String diff) {
        _diff = diff;
    }

    public String getNewPath() {
        return _newPath;
    }

    public void setNewPath(String newPath) {
        _newPath = newPath;
    }

    public String getOldPath() {
        return _oldPath;
    }

    public void setOldPath(String oldPath) {
        _oldPath = oldPath;
    }

    public String getAMode() {
        return _aMode;
    }

    public void setAMode(String aMode) {
        _aMode = aMode;
    }

    public String getBMode() {
        return _bMode;
    }

    public void setBMode(String bMode) {
        _bMode = bMode;
    }

    public boolean getNewFile() {
        return _newFile;
    }

    public void setNewFile(boolean newFile) {
        _newFile = newFile;
    }

    public boolean getRenamedFile() {
        return _renamedFile;
    }

    public void setRenamedFile(boolean renamedFile) {
        _renamedFile = renamedFile;
    }

    public boolean getDeletedFile() {
        return _deletedFile;
    }

    public void setDeletedFile(boolean deletedFile) {
        _deletedFile = deletedFile;
    }
}
