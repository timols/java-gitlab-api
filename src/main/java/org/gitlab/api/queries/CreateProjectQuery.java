package org.gitlab.api.queries;

/**
 * Created by cesaraguilar on 9/2/16.
 */
public class CreateProjectQuery extends Query {

    public CreateProjectQuery() {
    }

    public CreateProjectQuery(String name) {
        super();
        setName(name);
    }

    public CreateProjectQuery setName(String name) {
        doAppend("name", name);
        return this;
    }

    public CreateProjectQuery setPath(String path) {
        doAppend("path", path);
        return this;
    }

    public CreateProjectQuery setNamespaceId(Integer namespaceId) {
        doAppend("namespace_id", namespaceId);
        return this;
    }

    public CreateProjectQuery setDescription(String description) {
        doAppend("description", description);
        return this;
    }

    public CreateProjectQuery setIssuesEnabled(boolean issuesEnabled) {
        doAppend("issues_enabled", issuesEnabled);
        return this;
    }

    public CreateProjectQuery setWallEnabled(boolean wallEnabled) {
        doAppend("wall_enabled", wallEnabled);
        return this;
    }

    public CreateProjectQuery setMergeRequestEnabled(boolean mergeRequestEnabled) {
        doAppend("merge_requests_enabled", mergeRequestEnabled);
        return this;
    }

    public CreateProjectQuery setBuildsEnabled(boolean buildsEnabled) {
        doAppend("builds_enabled", buildsEnabled);
        return this;
    }

    public CreateProjectQuery setWikiEnabled(boolean wikiEnabled) {
        doAppend("wiki_enabled", wikiEnabled);
        return this;
    }

    public CreateProjectQuery setSnippetsEnabled(boolean snippetsEnabled) {
        doAppend("snippets_enabled", snippetsEnabled);
        return this;
    }

    public CreateProjectQuery setContainerRegistryEnabled(boolean containerRegistryEnabled) {
        doAppend("container_registry_enabled", containerRegistryEnabled);
        return this;
    }

    public CreateProjectQuery setSharedRunnersEnabled(boolean sharedRunnersEnabled) {
        doAppend("shared_runners_enabled", sharedRunnersEnabled);
        return this;
    }

    public CreateProjectQuery setPublic(boolean publik) {
        doAppend("public", publik);
        return this;
    }

    public CreateProjectQuery setVisibilityLevel(Integer visibilityLevel) {
        doAppend("visibility_level", visibilityLevel);
        return this;
    }

    public CreateProjectQuery setImportUrl(String importUrl) {
        doAppend("import_url", importUrl);
        return this;
    }

    public CreateProjectQuery setPublicBuilds(boolean publicBuilds) {
        doAppend("public_builds", publicBuilds);
        return this;
    }

    public CreateProjectQuery setOnlyAllowMergeIfBuildSucceeds(boolean onlyAllowMergeIfBuildSucceeds) {
        doAppend("only_allow_merge_if_build_succeeds", onlyAllowMergeIfBuildSucceeds);
        return this;
    }

    public CreateProjectQuery setLfsEnabled(boolean lfsEnabled) {
        doAppend("lfs_enabled", lfsEnabled);
        return this;
    }

}
