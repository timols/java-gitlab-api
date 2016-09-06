package org.gitlab.api;

import org.gitlab.api.queries.Query;
import org.gitlab.api.queries.QueryBuilder;

import java.io.UnsupportedEncodingException;

public class Pagination extends Query {
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final int MAX_ITEMS_PER_PAGE = 100;

    public void setPage(int page) {
        doAppend(PARAM_PAGE, String.valueOf(page));
    }

    public void setPerPage(int perPage) {
        if (perPage > MAX_ITEMS_PER_PAGE) {
            throw new IllegalArgumentException("Max value for perPage is " + MAX_ITEMS_PER_PAGE);
        }
        doAppend(PARAM_PER_PAGE, String.valueOf(perPage));
    }
}
