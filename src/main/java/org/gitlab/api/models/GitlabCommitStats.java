package org.gitlab.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @ClassName GitlabCommitStats
 * @Description
 * @Author mochen
 * @Date 2021/6/3 14:47
 * @Version 1.0
 **/
public class GitlabCommitStats {

    @JsonProperty("additions")
    private Integer additions;

    @JsonProperty("deletions")
    private Integer deletions;

    @JsonProperty("total")
    private Integer total;

    public Integer getAdditions() {
        return additions;
    }

    public void setAdditions(Integer additions) {
        this.additions = additions;
    }

    public Integer getDeletions() {
        return deletions;
    }

    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
