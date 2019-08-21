package org.gitlab.api.query;

import org.gitlab.api.http.Query;

import java.io.UnsupportedEncodingException;

public class PaginationQuery extends Query {

    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final int MAX_ITEMS_PER_PAGE = 100;

    public void setPage(int page) {
        try {
            append(PARAM_PAGE, String.valueOf(page));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public void setPerPage(int perPage) {
        if (perPage > MAX_ITEMS_PER_PAGE) {
            throw new IllegalArgumentException("Max value for perPage is " + MAX_ITEMS_PER_PAGE);
        }
        try {
            append(PARAM_PER_PAGE, String.valueOf(perPage));
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    public PaginationQuery withPage(int page) {
        setPage(page);
        return this;
    }

    public PaginationQuery withPerPage(int perPage) {
        setPerPage(perPage);
        return this;
    }

}
