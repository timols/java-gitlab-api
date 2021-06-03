package org.gitlab.api.query;

import org.gitlab.api.http.Query;

import java.io.UnsupportedEncodingException;

/**
 * @author yh
 */
public class CommitsQuery extends Query {

    public static final String PARAM_REF_NAME = "ref_name";
    public static final String PARAM_SINCE = "since";
    public static final String PARAM_UNTIL = "until";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_ALL = "all";
    public static final String PARAM_WITH_STATS = "with_stats";
    public static final String PARAM_FIRST_PARENT = "first_parent";
    public static final String PARAM_ORDER = "order";

    public void setRefName(String refName) throws UnsupportedEncodingException {
        appendIf(PARAM_REF_NAME, refName);
    }

    public CommitsQuery withRefName(String refName) throws UnsupportedEncodingException {
        setRefName(refName);
        return this;
    }

    public void setSince(String since) throws UnsupportedEncodingException {
        appendIf(PARAM_SINCE, since);
    }

    public CommitsQuery withSince(String since) throws UnsupportedEncodingException {
        setSince(since);
        return this;
    }

    public void setUntil(String until) throws UnsupportedEncodingException {
        appendIf(PARAM_UNTIL, until);
    }

    public CommitsQuery withUntil(String until) throws UnsupportedEncodingException {
        setUntil(until);
        return this;
    }

    public void setPath(String path) throws UnsupportedEncodingException {
        appendIf(PARAM_PATH, path);
    }

    public CommitsQuery withPath(String path) throws UnsupportedEncodingException {
        setPath(path);
        return this;
    }

    public void setAll(Boolean all) throws UnsupportedEncodingException {
        appendIf(PARAM_ALL, all);
    }

    public CommitsQuery withAll(Boolean all) throws UnsupportedEncodingException {
        setAll(all);
        return this;
    }

    public void setWithStats(Boolean withStats) throws UnsupportedEncodingException {
        appendIf(PARAM_WITH_STATS, withStats);
    }

    public CommitsQuery withWithStats(Boolean withStats) throws UnsupportedEncodingException {
        setWithStats(withStats);
        return this;
    }

    public void setFirstParent(Boolean firstParent) throws UnsupportedEncodingException {
        appendIf(PARAM_FIRST_PARENT, firstParent);
    }

    public CommitsQuery withFirstParent(Boolean firstParent) throws UnsupportedEncodingException {
        setFirstParent(firstParent);
        return this;
    }

 public void setOrder(String order) throws UnsupportedEncodingException {
        appendIf(PARAM_ORDER, order);
    }

    public CommitsQuery withOrder(String order) throws UnsupportedEncodingException {
        setOrder(order);
        return this;
    }


}
