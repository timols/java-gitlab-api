package org.gitlab.api;

import org.gitlab.api.http.Query;
import org.gitlab.api.query.PaginationQuery;

/**
 * @deprecated Use {@link PaginationQuery#PARAM_PAGE} instead.
 */
@Deprecated
public class Pagination extends PaginationQuery {

    /**
     * @deprecated Use {@link PaginationQuery#PARAM_PAGE} instead.
     */
    @Deprecated
    public static final String PARAM_PAGE = PaginationQuery.PARAM_PAGE;

    /**
      @deprecated Use {@link PaginationQuery#PARAM_PER_PAGE} instead.
     */
    @Deprecated
    public static final String PARAM_PER_PAGE = PaginationQuery.PARAM_PER_PAGE;

    /**
      @deprecated Use {@link PaginationQuery#MAX_ITEMS_PER_PAGE} instead.
     */
    @Deprecated
    public static final int MAX_ITEMS_PER_PAGE = PaginationQuery.MAX_ITEMS_PER_PAGE;

    public Pagination withPage(int page) {
        setPage(page);
        return this;
    }

    public Pagination withPerPage(int perPage) {
        setPerPage(perPage);
        return this;
    }

    public Query asQuery() {
        return this;
    }

}
