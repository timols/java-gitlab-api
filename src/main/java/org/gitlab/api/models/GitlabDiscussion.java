package org.gitlab.api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Patrizio Bonzani
 */
public class GitlabDiscussion {

	public static final String URL = "/discussions";

	/**
	 * The ID of a discussion.
	 */
	private int id;

	/**
	 * The notes contained in this discussion.
	 */
	private List<GitlabNote> notes = new ArrayList<GitlabNote>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<GitlabNote> getNotes() {
		return notes;
	}

	public void setNotes(List<GitlabNote> notes) {
		this.notes = notes;
	}
}
