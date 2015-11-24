package org.gitlab.api;

import org.gitlab.api.http.Query;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class PaginationTest {
    @Test
    public void emptyPagination() {
        Pagination pagination = new Pagination();

        final Query expectedQuery = new Query();
        assertEquals(expectedQuery.toString(), pagination.toString());
        assertEquals(expectedQuery.toString(), pagination.asQuery().toString());
    }

    @Test
    public void pageOnlyPagination() throws UnsupportedEncodingException {
        Pagination pagination = new Pagination();
        pagination.setPage(1);

        final Query expectedQuery = new Query()
                .append(Pagination.PARAM_PAGE, "1");

        assertEquals(expectedQuery.toString(), pagination.toString());
        assertEquals(expectedQuery.toString(), pagination.asQuery().toString());
    }

    @Test
    public void perPageOnlyPagination() throws UnsupportedEncodingException {
        Pagination pagination = new Pagination();
        pagination.setPerPage(50);

        final Query expectedQuery = new Query()
                .append(Pagination.PARAM_PER_PAGE, "50");

        assertEquals(expectedQuery.toString(), pagination.toString());
        assertEquals(expectedQuery.toString(), pagination.asQuery().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void perPageException() {
        Pagination pagination = new Pagination();
        pagination.setPerPage(Pagination.MAX_ITEMS_PER_PAGE + 1);
    }

    @Test
    public void complexPagination() throws UnsupportedEncodingException {
        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setPerPage(50);

        final Query expectedQuery = new Query()
                .append(Pagination.PARAM_PAGE, "1")
                .append(Pagination.PARAM_PER_PAGE, "50");

        assertEquals(expectedQuery.toString(), pagination.toString());
        assertEquals(expectedQuery.toString(), pagination.asQuery().toString());
    }
}
