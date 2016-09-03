package org.gitlab.api.queries;

import java.io.UnsupportedEncodingException;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public abstract class Query {

    private final QueryBuilder queryBuilder = new QueryBuilder();

    protected void doAppend(String key, String value) {
        try {
            queryBuilder.append(key, value);
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    protected void doAppend(String key, Boolean value) {
        try {
            queryBuilder.appendIf(key, value);
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    protected void doAppend(String key, Integer value) {
        try {
            queryBuilder.appendIf(key, value);
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public QueryBuilder asBuilder() {
        return queryBuilder;
    }

    public String toString() {
        return queryBuilder.toString();
    }
}
