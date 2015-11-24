package org.gitlab.api.http;

import org.gitlab.api.models.GitlabAccessLevel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Models the Query
 * aspect of a URL
 */
public class Query {

    private class Tuple<T1, T2> {
        T1 _1;
        T2 _2;

        public Tuple(T1 _1, T2 _2) {
            this._1 = _1;
            this._2 = _2;
        }
    }

    /**
     * The type of params is:
     * Tuple<name, Tuple<value, URLEncoder.encode(value, "UTF-8")>>
     */
    private final List<Tuple<String, Tuple<String, String>>> params = new ArrayList<Tuple<String, Tuple<String, String>>>();

    /**
     * Appends a parameter to the query
     *
     * @param name  Parameter name
     * @param value Parameter value
     * @return this
     * @throws java.io.UnsupportedEncodingException If the provided value cannot be URL Encoded
     */
    public Query append(final String name, final String value) throws UnsupportedEncodingException {
        params.add(new Tuple<String, Tuple<String, String>>(name, new Tuple<String, String>(value, URLEncoder.encode(value, "UTF-8"))));
        return this;
    }

    /**
     * Conditionally append a parameter to the query
     * if the value of the parameter is not null
     *
     * @param name  Parameter name
     * @param value Parameter value
     * @return this
     * @throws java.io.UnsupportedEncodingException If the provided value cannot be URL Encoded
     */
    public Query appendIf(final String name, final String value) throws UnsupportedEncodingException {
        if (value != null) {
            append(name, value);
        }
        return this;
    }

    /**
     * Conditionally append a parameter to the query
     * if the value of the parameter is not null
     *
     * @param name  Parameter name
     * @param value Parameter value
     * @return this
     * @throws java.io.UnsupportedEncodingException If the provided value cannot be URL Encoded
     */
    public Query appendIf(final String name, final Integer value) throws UnsupportedEncodingException {
        if (value != null) {
            append(name, value.toString());
        }
        return this;
    }

    /**
     * Conditionally append a parameter to the query
     * if the value of the parameter is not null
     *
     * @param name  Parameter name
     * @param value Parameter value
     * @return this
     * @throws java.io.UnsupportedEncodingException If the provided value cannot be URL Encoded
     */
    public Query appendIf(final String name, final Boolean value) throws UnsupportedEncodingException {
        if (value != null) {
            append(name, value.toString());
        }
        return this;
    }

    /**
     * Conditionally append a parameter to the query
     * if the value of the parameter is not null
     *
     * @param name  Parameter name
     * @param value Parameter value
     * @return this
     * @throws java.io.UnsupportedEncodingException If the provided value cannot be URL Encoded
     */
    public Query appendIf(final String name, final GitlabAccessLevel value) throws UnsupportedEncodingException {
        if (value != null) {
            append(name, Integer.toString(value.accessValue));
        }
        return this;
    }

    public boolean mergeWith(Query query) {
        return params.addAll(query.params);
    }

    /**
     * Returns a Query suitable for appending
     * to a URI
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final Tuple<String, Tuple<String, String>> param : params) {
            if (builder.length() == 0) {
                builder.append('?');
            } else {
                builder.append('&');
            }
            builder.append(param._1);
            builder.append('=');
            builder.append(param._2._2);
        }

        return builder.toString();
    }
}
