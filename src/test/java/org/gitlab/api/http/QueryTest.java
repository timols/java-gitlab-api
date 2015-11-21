package org.gitlab.api.http;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryTest {

    @Test
    public void mutableStyle_append() throws UnsupportedEncodingException {
        Query query = new Query();

        query.append("p1", "v1");
        query.append("p2", "v2");

        assertEquals("?p1=v1&p2=v2", query.toString());
    }

    @Test
    public void fluentStyle_append() throws UnsupportedEncodingException {
        Query query = new Query()
                .append("p1", "v1")
                .append("p2", "v2");

        assertEquals("?p1=v1&p2=v2", query.toString());
    }

    @Test
    public void mixedStyle_append() throws UnsupportedEncodingException {
        Query query = new Query()
                .append("p1", "v1");


        query.append("p2", "v2");

        query = query.append("p3", "v3");

        assertEquals("?p1=v1&p2=v2&p3=v3", query.toString());
    }

    @Test
    public void conditionalAppend_notNull() throws UnsupportedEncodingException {
        Query query = new Query()
                .appendIf("p1", "v1")
                .appendIf("p2", "v2");

        assertEquals("?p1=v1&p2=v2", query.toString());
    }

    @Test
    public void conditionalAppend_null() throws UnsupportedEncodingException {
        Query query = new Query()
                .appendIf("p1", (String) null);

        assertEquals("", query.toString());
    }

    @Test
    public void conditionalAppend_null_notNull() throws UnsupportedEncodingException {
        Query query = new Query()
                .appendIf("p1", (String) null)
                .appendIf("p2", "v2");

        assertEquals("?p2=v2", query.toString());
    }

    @Test
    public void append_encodes_values() throws UnsupportedEncodingException {
        Query query = new Query()
                .append("p1", "v 1")
                .append("p2", "v 2");

        assertEquals("?p1=v+1&p2=v+2", query.toString());
    }

    @Test
    public void merge() throws UnsupportedEncodingException{
        Query sourceQuery= new Query()
                .append("p1", "v1")
                .append("p2", "v2");

        Query targetQuery = new Query()
                .append("p3", "v3")
                .append("p2", "v22");

        boolean mergeResult = targetQuery.mergeWith(sourceQuery);

        assertTrue(mergeResult);
        assertEquals("?p3=v3&p2=v22&p1=v1&p2=v2", targetQuery.toString());
    }
}
