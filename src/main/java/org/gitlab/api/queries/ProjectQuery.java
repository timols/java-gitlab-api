package org.gitlab.api.queries;

import java.io.UnsupportedEncodingException;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public class ProjectQuery implements Query {

    private final static String SEARCH = "search";
    private final static String ARCHIVED = "archived";
    private final QueryBuilder queryBuilder = new QueryBuilder();

    public ProjectQuery setArchived(boolean showArchived) {
        try {
            queryBuilder.append(ARCHIVED, Boolean.toString(showArchived));
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setVisibility(Visibility visibility) {
        try {
            queryBuilder.append(visibility.getType(), visibility.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setOrderBy(OrderBy orderBy) {
        try {
            queryBuilder.append(orderBy.getType(), orderBy.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setSort(Sort sort) {
        try {
            queryBuilder.append(sort.getType(), sort.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setSearch(String search) {
        try {
            queryBuilder.append(SEARCH, search);
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public QueryBuilder asBuilder() {
        return queryBuilder;
    }

    @Override
    public String toString() {
        return queryBuilder.toString();
    }
}
