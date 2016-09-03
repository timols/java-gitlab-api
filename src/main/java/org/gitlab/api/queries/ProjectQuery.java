package org.gitlab.api.queries;

import java.io.UnsupportedEncodingException;

/**
 * Created by cesaraguilar on 9/1/16.
 */
public class ProjectQuery extends Query {

    private final static String SEARCH = "search";
    private final static String ARCHIVED = "archived";

    public ProjectQuery setArchived(boolean showArchived) {
        doAppend(ARCHIVED, showArchived);
        return this;
    }

    public ProjectQuery setVisibility(Visibility visibility) {
        doAppend(visibility.getType(), visibility.getValue());
        return this;
    }

    public ProjectQuery setOrderBy(OrderBy orderBy) {
        doAppend(orderBy.getType(), orderBy.getValue());
        return this;
    }

    public ProjectQuery setSort(Sort sort) {
        doAppend(sort.getType(), sort.getValue());
        return this;
    }

    public ProjectQuery setSearch(String search) {
        doAppend(SEARCH, search);
        return this;
    }

}
