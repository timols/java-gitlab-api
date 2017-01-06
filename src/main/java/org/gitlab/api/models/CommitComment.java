package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CommitComment {

	public static final String URL = "/comments";

	private GitlabUser author;
	private String note;
	private String path;
	private String line;

	@JsonProperty("line_type")
	private String lineType;

	@JsonProperty("created_at")
	private Date createdAt;

	public GitlabUser getAuthor() {
		return author;
	}

	public void setAuthor(GitlabUser author) {
		this.author = author;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
