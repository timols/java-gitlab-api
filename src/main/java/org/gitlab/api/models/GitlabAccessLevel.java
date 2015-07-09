package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GitlabAccessLevel {
    Guest(10),
    Reporter(20),
    Developer(30),
    Master(40),
    Owner(50);

    public final int accessValue;

    GitlabAccessLevel(int accessValue) {
        this.accessValue = accessValue;
    }

    // http://fasterxml.github.io/jackson-annotations/javadoc/2.2.0/index.html?com/fasterxml/jackson/annotation/JsonCreator.html
    @JsonCreator
    public static GitlabAccessLevel fromAccessValue(final int accessValue) throws IllegalArgumentException {
        for (final GitlabAccessLevel gitlabAccessLevel : GitlabAccessLevel.values()) {
            if (gitlabAccessLevel.accessValue == accessValue) {
                return gitlabAccessLevel;
            }
        }
        throw new IllegalArgumentException("No GitLab Access Level enum constant with access value: " + accessValue);
    }
}
