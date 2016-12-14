package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabUserIdentity {

    @JsonProperty("provider")
    private String _provider;

    @JsonProperty("extern_uid")
    private String _externUid;

    public String getProvider() {
        return _provider;
    }

    public void setProvider(String provider) {
        this._provider = provider;
    }

    public String getExternUid() {
        return _externUid;
    }

    public void setExternUuid(String externUid) {
        this._externUid = externUid;
    }
}
