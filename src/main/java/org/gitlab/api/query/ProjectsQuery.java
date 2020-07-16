package org.gitlab.api.query;

import org.gitlab.api.models.GitlabAccessLevel;

import java.io.UnsupportedEncodingException;

public class ProjectsQuery extends PaginationQuery {

    public static final String PARAM_ARCHIVED = "archived";
    public static final String PARAM_VISIBILITY = "visibility";
    public static final String PARAM_ORDER_BY = "order_by";
    public static final String PARAM_SORT = "sort";
    public static final String PARAM_SEARCH = "search";
    public static final String PARAM_SIMPLE = "simple";
    public static final String PARAM_OWNED = "owned";
    public static final String PARAM_MEMBERSHIP = "membership";
    public static final String PARAM_STARRED = "starred";
    public static final String PARAM_STATISTICS = "statistics";
    public static final String PARAM_WITH_CUSTOM_ATTRIBUTES = "with_custom_attributes";
    public static final String PARAM_WITH_ISSUES_ENABLED = "with_issues_enabled";
    public static final String PARAM_WITH_MERGE_REQUESTS_ENABLED = "with_merge_requests_enabled";
    public static final String PARAM_WITH_PROGRAMMING_LANGUAGE = "with_programming_language";
    public static final String PARAM_WIKI_CHECKSUM_FAILED = "wiki_checksum_failed";
    public static final String PARAM_REPOSITORY_CHECKSUM_FAILED = "repository_checksum_failed";
    public static final String PARAM_MIN_ACCESS_LEVEL = "min_access_level";

    public void setArchived(Boolean archived) throws UnsupportedEncodingException {
        appendIf(PARAM_ARCHIVED, archived);
    }

    public ProjectsQuery withArchived(Boolean archived) throws UnsupportedEncodingException {
        setArchived(archived);
        return this;
    }

    public void setVisibility(String visibility) throws UnsupportedEncodingException {
        appendIf(PARAM_VISIBILITY, visibility);
    }

    public ProjectsQuery withVisibility(String visibility) throws UnsupportedEncodingException {
        setVisibility(visibility);
        return this;
    }

    public void setOrderBy(String orderBy) throws UnsupportedEncodingException {
        appendIf(PARAM_ORDER_BY, orderBy);
    }

    public ProjectsQuery withOrderBy(String orderBy) throws UnsupportedEncodingException {
        setOrderBy(orderBy);
        return this;
    }

    public void setSort(String sort) throws UnsupportedEncodingException {
        appendIf(PARAM_SORT, sort);
    }

    public ProjectsQuery withSort(String sort) throws UnsupportedEncodingException {
        setSort(sort);
        return this;
    }

    public void setSearch(String search) throws UnsupportedEncodingException {
        appendIf(PARAM_SEARCH, search);
    }

    public ProjectsQuery withSearch(String search) throws UnsupportedEncodingException {
        setSearch(search);
        return this;
    }

    public void setSimple(Boolean simple) throws UnsupportedEncodingException {
        appendIf(PARAM_SIMPLE, simple);
    }

    public ProjectsQuery withSimple(Boolean simple) throws UnsupportedEncodingException {
        setSimple(simple);
        return this;
    }

    public void setOwned(Boolean owned) throws UnsupportedEncodingException {
        appendIf(PARAM_OWNED, owned);
    }

    public ProjectsQuery withOwned(Boolean owned) throws UnsupportedEncodingException {
        setOwned(owned);
        return this;
    }

    public void setMembership(Boolean membership) throws UnsupportedEncodingException {
        appendIf(PARAM_MEMBERSHIP, membership);
    }

    public ProjectsQuery withMembership(Boolean membership) throws UnsupportedEncodingException {
        setMembership(membership);
        return this;
    }

    public void setStarred(Boolean starred) throws UnsupportedEncodingException {
        appendIf(PARAM_STARRED, starred);
    }

    public ProjectsQuery withStarred(Boolean starred) throws UnsupportedEncodingException {
        setStarred(starred);
        return this;
    }

    public void setStatistics(Boolean statistics) throws UnsupportedEncodingException {
        appendIf(PARAM_STATISTICS, statistics);
    }

    public ProjectsQuery withStatistics(Boolean statistics) throws UnsupportedEncodingException {
        setStatistics(statistics);
        return this;
    }

    public void setWithCustomAttributes(Boolean withCustomAttributes) throws UnsupportedEncodingException {
        appendIf(PARAM_WITH_CUSTOM_ATTRIBUTES, withCustomAttributes);
    }

    public ProjectsQuery withWithCustomAttributes(Boolean withCustomAttributes) throws UnsupportedEncodingException {
        setWithCustomAttributes(withCustomAttributes);
        return this;
    }

    public void setWithIssuesEnabled(Boolean withIssuesEnabled) throws UnsupportedEncodingException {
        appendIf(PARAM_WITH_ISSUES_ENABLED, withIssuesEnabled);
    }

    public ProjectsQuery withWithIssuesEnabled(Boolean withIssuesEnabled) throws UnsupportedEncodingException {
        setWithIssuesEnabled(withIssuesEnabled);
        return this;
    }

    public void setWithMergeRequestsEnabled(Boolean withMergeRequestsEnabled) throws UnsupportedEncodingException {
        appendIf(PARAM_WITH_MERGE_REQUESTS_ENABLED, withMergeRequestsEnabled);
    }

    public ProjectsQuery withWithMergeRequestsEnabled(Boolean withMergeRequestsEnabled) throws UnsupportedEncodingException {
        setWithMergeRequestsEnabled(withMergeRequestsEnabled);
        return this;
    }

    public void setWithProgrammingLanguage(String withProgrammingLanguage) throws UnsupportedEncodingException {
        appendIf(PARAM_WITH_PROGRAMMING_LANGUAGE, withProgrammingLanguage);
    }

    public ProjectsQuery withWithProgrammingLanguage(String withProgrammingLanguage) throws UnsupportedEncodingException {
        setWithProgrammingLanguage(withProgrammingLanguage);
        return this;
    }

    public void setWikiChecksumFailed(Boolean wikiChecksumFailed) throws UnsupportedEncodingException {
        appendIf(PARAM_WIKI_CHECKSUM_FAILED, wikiChecksumFailed);
    }

    public ProjectsQuery withWikiChecksumFailed(Boolean wikiChecksumFailed) throws UnsupportedEncodingException {
        setWikiChecksumFailed(wikiChecksumFailed);
        return this;
    }

    public void setRepositoryChecksumFailed(Boolean repositoryChecksumFailed) throws UnsupportedEncodingException {
        appendIf(PARAM_REPOSITORY_CHECKSUM_FAILED, repositoryChecksumFailed);
    }

    public ProjectsQuery withRepositoryChecksumFailed(Boolean repositoryChecksumFailed) throws UnsupportedEncodingException {
        setRepositoryChecksumFailed(repositoryChecksumFailed);
        return this;
    }

    public void setMinAccessLevel(GitlabAccessLevel minAccessLevel) throws UnsupportedEncodingException {
        appendIf(PARAM_MIN_ACCESS_LEVEL, minAccessLevel);
    }

    public ProjectsQuery withMinAccessLevel(GitlabAccessLevel minAccessLevel) throws UnsupportedEncodingException {
        setMinAccessLevel(minAccessLevel);
        return this;
    }

    @Override
    public ProjectsQuery withPage(int page) {
        super.withPage(page);
        return this;
    }

    @Override
    public ProjectsQuery withPerPage(int perPage) {
        super.withPerPage(perPage);
        return this;
    }

}
