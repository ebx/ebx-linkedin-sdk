
package com.echobox.api.linkedin.client;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.scope.ScopeBuilder;
import com.echobox.api.linkedin.version.Version;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class DefaultLinkedInClient extends BaseLinkedInClient implements LinkedInClient {

  /**
   * Graph API access token.
   */
  protected String accessToken;

  /**
   * Graph API app secret.
   */
  private String appSecret;

  /**
   * API endpoint URL.
   */
  protected String FACEBOOK_GRAPH_ENDPOINT_URL = "https://graph.facebook.com";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion = Version.V1;

  public DefaultLinkedInClient(String accessToken) {

  }

  public DefaultLinkedInClient(String accessToken, Version apiVersion) {
    this(accessToken, null, new DefaultWebRequestor(), new DefaultJsonMapper(), apiVersion);
  }

  public DefaultLinkedInClient(String accessToken, String appSecret, Version apiVersion) {
    this(accessToken, appSecret, new DefaultWebRequestor(), new DefaultJsonMapper(), apiVersion);
  }

  public DefaultLinkedInClient(String accessToken, WebRequestor webRequestor,
      JsonMapper jsonMapper, Version apiVersion) {
    this(accessToken, null, webRequestor, jsonMapper, apiVersion);
  }

  public DefaultLinkedInClient(String accessToken, String appSecret, WebRequestor webRequestor,
      JsonMapper jsonMapper, Version apiVersion) {
    super();
    
    verifyParameterPresence("jsonMapper", jsonMapper);
    verifyParameterPresence("webRequestor", webRequestor);
    
    this.accessToken = StringUtils.trimToNull(accessToken);
    this.appSecret = StringUtils.trimToNull(appSecret);
    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    this.apiVersion = apiVersion;
  }

  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<AccessToken> convertSessionKeysToAccessTokens(String appId, String secretKey,
      String... sessionKeys) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AccessToken obtainAppAccessToken(String appId, String appSecret) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret, String accessToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String obtainAppSecretProof(String accessToken, String appSecret) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AccessToken obtainExtendedAccessToken(String appId, String appSecret) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T parseSignedRequest(String signedRequest, String appSecret, Class<T> objectType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DebugTokenInfo debugToken(String inputToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public JsonMapper getJsonMapper() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public WebRequestor getWebRequestor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLogoutUrl(String next) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope,
      Parameter... additionalParameters) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected String getFacebookReadOnlyEndpointUrl() {
    // TODO Auto-generated method stub
    return null;
  }

}
