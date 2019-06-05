package org.gitlab.api.query;

import java.io.UnsupportedEncodingException;

public class PipelinesQuery extends PaginationQuery {

    public void setScope(String scope) throws UnsupportedEncodingException {
        appendIf("scope", scope);
    }

    public PipelinesQuery withScope(String scope) throws UnsupportedEncodingException {
        this.setScope(scope);
        return this;
    }

    public void setStatus(String status) throws UnsupportedEncodingException {
        appendIf("status", status);
    }

    public PipelinesQuery withStatus(String status) throws UnsupportedEncodingException {
        this.setStatus(status);
        return this;
    }

    public void setRef(String ref) throws UnsupportedEncodingException {
        appendIf("ref", ref);
    }

    public PipelinesQuery withRef(String ref) throws UnsupportedEncodingException {
        this.setRef(ref);
        return this;
    }

    public void setSha(String sha) throws UnsupportedEncodingException {
        appendIf("sha", sha);
    }

    public PipelinesQuery withSha(String sha) throws UnsupportedEncodingException {
        this.setSha(sha);
        return this;
    }

    public void setYamlErrors(Boolean yamlErrors) throws UnsupportedEncodingException {
        appendIf("yaml_errors", yamlErrors);
    }

    public PipelinesQuery withYamlErrors(Boolean yamlErrors) throws UnsupportedEncodingException {
        this.setYamlErrors(yamlErrors);
        return this;
    }

    public void setName(String name) throws UnsupportedEncodingException {
        appendIf("name", name);
    }

    public PipelinesQuery withName(String name) throws UnsupportedEncodingException {
        this.setName(name);
        return this;
    }

    public void setUsername(String username) throws UnsupportedEncodingException {
        appendIf("username", username);
    }

    public PipelinesQuery withUsername(String username) throws UnsupportedEncodingException {
        this.setUsername(username);
        return this;
    }

    public void setOrderBy(String orderBy) throws UnsupportedEncodingException {
        appendIf("order_by", orderBy);
    }

    public PipelinesQuery withOrderBy(String orderBy) throws UnsupportedEncodingException {
        this.setOrderBy(orderBy);
        return this;
    }

    public void setSort(String sort) throws UnsupportedEncodingException {
        appendIf("sort", sort);
    }

    public PipelinesQuery withSort(String sort) throws UnsupportedEncodingException {
        this.setSort(sort);
        return this;
    }

    @Override
    public PipelinesQuery withPage(int page) {
        super.withPage(page);
        return this;
    }

    @Override
    public PipelinesQuery withPerPage(int perPage) {
        super.withPerPage(perPage);
        return this;
    }

}
