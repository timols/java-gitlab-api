package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommitComment {

	public static final String URL = "/comments";

	private GitlabUser author;
	private String note;
	private String path;
	private String line;

	@JsonProperty("line_type")
	private String lineType;

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
}
