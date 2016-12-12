package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabUserIdentity {

    @JsonProperty("provider")
    private String _provider;

    @JsonProperty("extern_uuid")
    private String _externUuid;

    public String getProvider() {
        return _provider;
    }

    public void setProvider(String provider) {
        this._provider = provider;
    }

    public String getExternUuid() {
        return _externUuid;
    }

    public void setExternUuid(String externUuid) {
        this._externUuid = externUuid;
    }
}
