package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabSimpleRepositoryFile {
    /*
   "file_name": "app/project.rb",
   "branch_name": "master"
     */

    @JsonProperty("file_path")
    private String filePath;

    @JsonProperty("branch")
    private String branchName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
