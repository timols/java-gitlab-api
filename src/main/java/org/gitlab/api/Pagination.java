package org.gitlab.api;

import org.gitlab.api.http.Query;

import java.io.UnsupportedEncodingException;

public class Pagination {
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final int MAX_ITEMS_PER_PAGE = 100;
    private final Query paginationQuery = new Query();

    public void setPage(int page) {
        try {
            paginationQuery.append(PARAM_PAGE, String.valueOf(page));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public void setPerPage(int perPage) {
        if (perPage > MAX_ITEMS_PER_PAGE) {
            throw new IllegalArgumentException("Max value for perPage is " + MAX_ITEMS_PER_PAGE);
        }
        try {
            paginationQuery.append(PARAM_PER_PAGE, String.valueOf(perPage));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public Query asQuery() {
        return paginationQuery;
    }

    @Override
    public String toString() {
        return paginationQuery.toString();
    }
}
