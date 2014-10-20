package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabFile {

    public static final String URL = "/repository/files/";

    @JsonProperty("file_name")
    private String _file_name;

    @JsonProperty("file_path")
    private String _file_path;

    @JsonProperty("size")
    private int _size;

    @JsonProperty("encoding")
    private String _encoding;

    @JsonProperty("content")
    private String _content;

    @JsonProperty("ref")
    private String _ref;

    @JsonProperty("blob_id")
    private String _blob_id;

    @JsonProperty("commit_id")
    private String _commit_id;

    public String getFileName() {
        return _file_name;
    }

    public void setFileName(String fileName) {
        this._file_name = fileName;
    }

    public String getFilePath() {
        return _file_path;
    }

    public void setFilePath(String filePath) {
        this._file_path = filePath;
    }

    public int getSize() {
        return _size;
    }

    public void setSize(int size) {
        this._size = size;
    }

    public String getEncoding() {
        return _encoding;
    }

    public void setEncoding(String encoding) {
        this._encoding = encoding;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        this._content = content;
    }

    public String getRef() {
        return _ref;
    }

    public void setRef(String ref) {
        this._ref = ref;
    }

    public String getBlobId() {
        return _blob_id;
    }

    public void setBlobId(String blobId) {
        this._blob_id = blobId;
    }

    public String getCommitId() {
        return _commit_id;
    }

    public void setCommitId(String commitId) {
        this._commit_id = commitId;
    }
}
