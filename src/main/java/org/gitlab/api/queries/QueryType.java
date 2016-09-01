package org.gitlab.api.queries;

import org.gitlab.api.http.Query;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public interface QueryType {
    Query asQuery();
    String toString();
}
