package org.gitlab.api.models;

import java.io.UnsupportedEncodingException;
import org.gitlab.api.http.Query;

/**
 * Model for customized creater-user requests.
 *
 */
public class CreateUserRequest {

  private String email;
  private String password;
  private Boolean resetPassword;
  private String username;
  private String name;
  private String skype;
  private String linkedin;
  private String twitter;
  private String websiteUrl;
  private String organization;
  private Integer projectsLimit;
  private String externUid;
  private String provider;
  private String bio;
  private String location;
  private Boolean admin;
  private Boolean canCreateGroup;
  private Boolean skipConfirmation;
  private Boolean external;
  private String avatar;
  
  /**
   * The only constructor. The constructor demands the required fields for the request.
   * 
   * @param name The user's name.
   * @param username The user's display name.
   * @param email The user's email.
   */
  public CreateUserRequest(String name, String username, String email){
    this.name = name;
    this.username = username;
    this.email = email;
  }
  
  /**
   * Generates a query based on this request's properties.
   * @return {@link Query}
   * @throws UnsupportedEncodingException
   */
  public Query toQuery() throws UnsupportedEncodingException{
    return new Query()
    .appendIf("email", email)
    .appendIf("password", password)
    .appendIf("reset_password", resetPassword)
    .appendIf("username", username)
    .appendIf("name", name)
    .appendIf("skype", skype)
    .appendIf("linkedin", linkedin)
    .appendIf("twitter", twitter)
    .appendIf("website_url", websiteUrl)
    .appendIf("organization", organization)
    .appendIf("projects_limit", projectsLimit)
    .appendIf("extern_uid", externUid)
    .appendIf("provider", provider)
    .appendIf("bio", bio)
    .appendIf("location", location)
    .appendIf("admin", admin)
    .appendIf("can_create_group", canCreateGroup)
    .appendIf("skip_confirmation", skipConfirmation)
    .appendIf("external", external)
    .appendIf("avatar", avatar);
    
  }

  public String getEmail() {
    return email;
  }

  public CreateUserRequest setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public CreateUserRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public Boolean getResetPassword() {
    return resetPassword;
  }

  public CreateUserRequest setResetPassword(Boolean resetPassword) {
    this.resetPassword = resetPassword;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public CreateUserRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getName() {
    return name;
  }

  public CreateUserRequest setName(String name) {
    this.name = name;
    return this;
  }

  public String getSkype() {
    return skype;
  }

  public CreateUserRequest setSkype(String skype) {
    this.skype = skype;
    return this;
  }

  public String getLinkedin() {
    return linkedin;
  }

  public CreateUserRequest setLinkedin(String linkedin) {
    this.linkedin = linkedin;
    return this;
  }

  public String getTwitter() {
    return twitter;
  }

  public CreateUserRequest setTwitter(String twitter) {
    this.twitter = twitter;
    return this;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public CreateUserRequest setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
    return this;
  }

  public String getOrganization() {
    return organization;
  }

  public CreateUserRequest setOrganization(String organization) {
    this.organization = organization;
    return this;
  }

  public Integer getProjectsLimit() {
    return projectsLimit;
  }

  public CreateUserRequest setProjectsLimit(Integer projectsLimit) {
    this.projectsLimit = projectsLimit;
    return this;
  }

  public String getExternUid() {
    return externUid;
  }

  public CreateUserRequest setExternUid(String externUid) {
    this.externUid = externUid;
    return this;
  }

  public String getProvider() {
    return provider;
  }

  public CreateUserRequest setProvider(String provider) {
    this.provider = provider;
    return this;
  }

  public String getBio() {
    return bio;
  }

  public CreateUserRequest setBio(String bio) {
    this.bio = bio;
    return this;
  }

  public String getLocation() {
    return location;
  }

  public CreateUserRequest setLocation(String location) {
    this.location = location;
    return this;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public CreateUserRequest setAdmin(Boolean admin) {
    this.admin = admin;
    return this;
  }

  public Boolean getCanCreateGroup() {
    return canCreateGroup;
  }

  public CreateUserRequest setCanCreateGroup(Boolean canCreateGroup) {
    this.canCreateGroup = canCreateGroup;
    return this;
  }

  public Boolean getSkipConfirmation() {
    return skipConfirmation;
  }

  public CreateUserRequest setSkipConfirmation(Boolean skipConfirmation) {
    this.skipConfirmation = skipConfirmation;
    return this;
  }

  public Boolean getExternal() {
    return external;
  }

  public CreateUserRequest setExternal(Boolean external) {
    this.external = external;
    return this;
  }

  public String getAvatar() {
    return avatar;
  }

  public CreateUserRequest setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }
  
  

}
