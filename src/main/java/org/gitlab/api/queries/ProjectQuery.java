package org.gitlab.api.queries;

import org.gitlab.api.http.Query;

import java.io.UnsupportedEncodingException;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public class ProjectQuery implements QueryType {

    private final static String SEARCH = "search";
    private final static String ARCHIVED = "archived";
    private final Query paginationQuery = new Query();

    public ProjectQuery setArchived(boolean showArchived) {
        try {
            paginationQuery.append(ARCHIVED, Boolean.toString(showArchived));
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setVisibility(Visibility visibility) {
        try {
            paginationQuery.append(visibility.getType(), visibility.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setOrderBy(OrderBy orderBy) {
        try {
            paginationQuery.append(orderBy.getType(), orderBy.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setSort(Sort sort) {
        try {
            paginationQuery.append(sort.getType(), sort.getValue());
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public ProjectQuery setSearch(String search) {
        try {
            paginationQuery.append(SEARCH, search);
        } catch (UnsupportedEncodingException ignored) {
        }
        return this;
    }

    public Query asQuery() {
        return paginationQuery;
    }

    @Override
    public String toString() {
        return paginationQuery.toString();
    }
}
