package org.gitlab.api.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a GitLab discussion. A discussion is a collection of
 * notes.
 *
 * @author Patrizio Bonzani
 */
public class GitlabDiscussion {

	public static final String URL = "/discussions";

	/**
	 * The ID of a discussion.
	 */
	private String id;

	/**
	 * The notes contained in this discussion.
	 */
	private List<GitlabNote> notes = new ArrayList<GitlabNote>();

	@JsonProperty("individual_note")
	private boolean individualNote;

	@SuppressWarnings("unused")
	private GitlabDiscussion() {}

	public GitlabDiscussion(String id) {
		this.id = id;
	}

	/**
	 * Get the id of this discussion.
	 *
	 * @return The id of the discussion.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the notes of this discussion.
	 *
	 * @return The notes contained in this discussion.
	 */
	public List<GitlabNote> getNotes() {
		return Collections.unmodifiableList(notes);
	}

	/**
	 * Add a note to the discussion.
	 *
	 * @param note The note to add to the discussion.
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 */
	public boolean addNote(GitlabNote note) {
		return notes.add(note);
	}

	public boolean isIndividualNote() {
		return individualNote;
	}
}
