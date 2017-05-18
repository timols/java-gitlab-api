package org.gitlab.api.models;


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
    private Boolean visibility;
    private Boolean lfsEnabled;
    private Boolean requestAccessEnabled;
    private Integer parentId;

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

    public Boolean getVisibility() {
        return visibility;
    }

    public CreateGroupRequest setVisibility(Boolean visibility) {
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