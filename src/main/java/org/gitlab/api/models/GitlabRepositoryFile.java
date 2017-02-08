package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kohsuke Kawaguchi
 */
public class GitlabRepositoryFile {
    /*
   "ref" : "master",
   "blob_id" : "17cab971a4202e8342effbb358ebe07c2de8fcc0",
   "commit_id" : "e8e073d98a6f15f8018e3359bf7bce3a6a144227",
   "encoding" : "base64",
   "size" : 21,
   "content" : "VGhpcyBpcyBzdXBlciB3aWRnZXQK",
   "file_path" : "README.md",
   "file_name" : "README.md",
   "last_commit_id" : "c0ad9bcdc43bc7f4050c58c699ff44cb8b916cdb"
     */

    private String ref;

    @JsonProperty("blob_id")
    private String blobId;

    @JsonProperty("commit_id")
    private String commitId;

    @JsonProperty("last_commit_id")
    private String lastCommitId;

    @JsonProperty("file_path")
    private String filePath;

    @JsonProperty("file_name")
    private String fileName;

    private String encoding, content;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getLastCommitId() {
        return lastCommitId;
    }

    public void setLastCommitId(String lastCommitId) {
        this.lastCommitId = lastCommitId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
