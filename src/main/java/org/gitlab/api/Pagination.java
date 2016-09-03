package org.gitlab.api;

import org.gitlab.api.queries.Query;
import org.gitlab.api.queries.QueryBuilder;

import java.io.UnsupportedEncodingException;

public class Pagination implements Query {
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final int MAX_ITEMS_PER_PAGE = 100;
    private final QueryBuilder queryBuilder = new QueryBuilder();

    public void setPage(int page) {
        try {
            queryBuilder.append(PARAM_PAGE, String.valueOf(page));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public void setPerPage(int perPage) {
        if (perPage > MAX_ITEMS_PER_PAGE) {
            throw new IllegalArgumentException("Max value for perPage is " + MAX_ITEMS_PER_PAGE);
        }
        try {
            queryBuilder.append(PARAM_PER_PAGE, String.valueOf(perPage));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public QueryBuilder asBuilder() {
        return queryBuilder;
    }

    @Override
    public String toString() {
        return queryBuilder.toString();
    }
}
