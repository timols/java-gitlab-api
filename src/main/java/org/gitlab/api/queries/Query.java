package org.gitlab.api.queries;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public interface Query {
    QueryBuilder asBuilder();
    String toString();
}
