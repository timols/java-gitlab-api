package org.gitlab.api.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class GitlabPersmission {

	@JsonProperty("project_access")
	private GitlabProjectAccessLevel _projectAccess;

	@JsonProperty("group_access")
	private GitlabProjectAccessLevel _groupAccess;

	public GitlabProjectAccessLevel getProjectAccess() {
		return _projectAccess;
	}

	public GitlabProjectAccessLevel getProjectGroupAccess() {
		return _groupAccess;
	}
}
