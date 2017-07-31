package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for the privacy settings supported by GitLab API v4.
 */
public enum GitlabVisibility {
  PRIVATE, INTERNAL, PUBLIC;
  
  /**
   * Returns lower-case of {@link #name()}. Lower-case is required for requests
   * that accept a visiblity parameter.
   */
  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}