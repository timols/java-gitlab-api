package org.gitlab.api.models;

import java.io.UnsupportedEncodingException;
import org.gitlab.api.http.Query;

/**
 * The model for custom group-creation requests.
 *
 */
public class CreateGroupRequest {

    public CreateGroupRequest(String name) {
      this(name, name);
    }

    public CreateGroupRequest(String name, String path) {
      this.name = name;
      this.path = path;
    }

    private String name;
    private String path;
    private String ldapCn;
    private String description;
    private Boolean membershipLock;
    private Boolean shareWithGroupLock;
    private GitlabVisibility visibility;
    private Boolean lfsEnabled;
    private Boolean requestAccessEnabled;
    private Integer parentId;
    
    /**
     * Generates query representing this request's properties.
     * @return {@link Query}
     * @throws UnsupportedEncodingException 
     */
    public Query toQuery() throws UnsupportedEncodingException{
      return new Query()
          .append("name", name)
          .append("path", path)
          .appendIf("ldap_cn", ldapCn)
          .appendIf("description", description)
          .appendIf("membershipLock", membershipLock)
          .appendIf("share_with_group_lock", shareWithGroupLock)
          .appendIf("visibility", visibility != null ? visibility.toString() : null)
          .appendIf("lfs_enabled", lfsEnabled)
          .appendIf("request_access_enabled", requestAccessEnabled)
          .appendIf("parent_id", parentId);
    }

    public String getName() {
        return name;
    }

    public CreateGroupRequest setName(String name) {
        this.name = name;
        return this;
    }
    public String getPath() {
        return path;
    }

    public CreateGroupRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getLdapCn() {
        return ldapCn;
    }

    public CreateGroupRequest setLdapCn(String ldapCn) {
        this.ldapCn = ldapCn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateGroupRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getMembershipLock() {
        return membershipLock;
    }

    public CreateGroupRequest setMembershipLock(Boolean membershipLock) {
        this.membershipLock = membershipLock;
        return this;
    }

    public Boolean getShareWithGroupLock() {
        return shareWithGroupLock;
    }

    public CreateGroupRequest setShareWithGroupLock(Boolean shareWithGroupLock) {
        this.shareWithGroupLock = shareWithGroupLock;
        return this;
    }

    public GitlabVisibility getVisibility() {
        return visibility;
    }

    public CreateGroupRequest setVisibility(GitlabVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public Boolean getLfsEnabled() {
        return lfsEnabled;
    }

    public CreateGroupRequest setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
        return this;
    }

    public Boolean getRequestAccessEnabled() {
        return requestAccessEnabled;
    }

    public CreateGroupRequest setRequestAccessEnabled(Boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public CreateGroupRequest setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
}