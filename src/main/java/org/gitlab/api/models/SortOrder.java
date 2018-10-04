package org.gitlab.api.models;

public enum SortOrder {
    ASC, DESC;

    public static final SortOrder DEFAULT = DESC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
