
package com.echobox.api.linkedin.client;

import static com.restfb.logging.RestFBLogger.CLIENT_LOGGER;
import static com.restfb.util.StringUtils.toInteger;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import com.echobox.api.linkedin.client.WebRequestor.Response;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.scope.ScopeBuilder;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.version.Version;
import com.restfb.exception.FacebookGraphException;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.exception.FacebookResponseStatusException;
import com.restfb.exception.ResponseErrorJsonParsingException;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class DefaultLinkedInClient extends BaseLinkedInClient implements LinkedInClient {
  
  /**
   * Reserved method override parameter name.
   */
  protected static final String METHOD_PARAM_NAME = "method";
  
  /**
   * Reserved "result format" parameter name.
   */
  protected static final String FORMAT_PARAM_NAME = "format";

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
  protected String LINKEDIN_API_ENDPOINT_URL = "https://api.linkedin.com/";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion = Version.V1;
  
  /**
   * By default this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback = false;

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
    verifyParameterPresence("object", object);
    verifyParameterPresence("objectType", objectType);
    return jsonMapper.toJavaObject(makeRequest(object, parameters), objectType);
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
  
  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we receive from the
   * endpoint.
   * 
   * @param endpoint
   *          Facebook Graph API endpoint.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected String makeRequest(String endpoint, Parameter... parameters) {
    return makeRequest(endpoint, false, false, null, parameters);
  }
  
  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we receive from the
   * endpoint.
   * 
   * @param endpoint
   *          Facebook Graph API endpoint.
   * @param executeAsPost
   *          {@code true} to execute the web request as a {@code POST}, {@code false} to execute as a {@code GET}.
   * @param executeAsDelete
   *          {@code true} to add a special 'treat this request as a {@code DELETE}' parameter.
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no attachment should be
   *          sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to Facebook as part of the API call.
   * @return The JSON returned by Facebook for the API call.
   * @throws FacebookException
   *           If an error occurs while making the Facebook API POST or processing the response.
   */
  protected String makeRequest(String endpoint, final boolean executeAsPost, final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"), parameters);
    }

    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }

    final String fullEndpoint =
        createEndpointForApiCall(endpoint, binaryAttachments != null && !binaryAttachments.isEmpty());
    final String parameterString = toParameterString(parameters);

    return makeRequestAndProcessResponse(new Requestor() {
      /**
       * @see com.restfb.DefaultFacebookClient.Requestor#makeRequest()
       */
      @Override
      public Response makeRequest() throws IOException {
        if (executeAsDelete && !isHttpDeleteFallback()) {
          return webRequestor.executeDelete(fullEndpoint + "?" + parameterString);
        } else {
          return executeAsPost
              ? webRequestor.executePost(fullEndpoint, parameterString,
                binaryAttachments == null ? null
                    : binaryAttachments.toArray(new BinaryAttachment[binaryAttachments.size()]))
              : webRequestor.executeGet(fullEndpoint + "?" + parameterString);
        }
      }
    });
  }
  
  /**
   * Generate the parameter string to be included in the Facebook API request.
   * 
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API request.
   * @throws FacebookJsonMappingException
   *           If an error occurs when building the parameter string.
   */
  protected String toParameterString(Parameter... parameters) {
    return toParameterString(true, parameters);
  }
  
  /**
   * Generate the parameter string to be included in the Facebook API request.
   * 
   * @param withJsonParameter
   *          add additional parameter format with type json
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the Facebook API request.
   * @throws FacebookJsonMappingException
   *           If an error occurs when building the parameter string.
   */
  protected String toParameterString(boolean withJsonParameter, Parameter... parameters) {
    if (!StringUtils.isBlank(accessToken)) {
      parameters = parametersWithAdditionalParameter(Parameter.with(ACCESS_TOKEN_PARAM_NAME, accessToken), parameters);
    }

    if (!StringUtils.isBlank(accessToken) && !StringUtils.isBlank(appSecret)) {
      parameters = parametersWithAdditionalParameter(
        Parameter.with(APP_SECRET_PROOF_PARAM_NAME, obtainAppSecretProof(accessToken, appSecret)), parameters);
    }

    if (withJsonParameter) {
      parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"), parameters);
    }

    StringBuilder parameterStringBuilder = new StringBuilder();
    boolean first = true;

    for (Parameter parameter : parameters) {
      if (first) {
        first = false;
      } else {
        parameterStringBuilder.append("&");
      }

      parameterStringBuilder.append(URLUtils.urlEncode(parameter.name));
      parameterStringBuilder.append("=");
      parameterStringBuilder.append(urlEncodedValueForParameterName(parameter.name, parameter.value));
    }

    return parameterStringBuilder.toString();
  }
  
  /**
   * returns if the fallback post method (<code>true</code>) is used or the http delete (<code>false</code>)
   * 
   * @return
   */
  public boolean isHttpDeleteFallback() {
    return httpDeleteFallback;
  }
  
  protected String makeRequestAndProcessResponse(Requestor requestor) {
    Response response;

    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
    } catch (Exception t) {
      throw new LinkedInNetworkException("Facebook request failed", t);
    }

    // If we get any HTTP response code other than a 200 OK or 400 Bad Request
    // or 401 Not Authorized or 403 Forbidden or 404 Not Found or 500 Internal
    // Server Error or 302 Not Modified
    // throw an exception.
    if (HTTP_OK != response.getStatusCode() && HTTP_BAD_REQUEST != response.getStatusCode()
        && HTTP_UNAUTHORIZED != response.getStatusCode() && HTTP_NOT_FOUND != response.getStatusCode()
        && HTTP_INTERNAL_ERROR != response.getStatusCode() && HTTP_FORBIDDEN != response.getStatusCode()
        && HTTP_NOT_MODIFIED != response.getStatusCode()) {
      throw new LinkedInNetworkException("Facebook request failed", response.getStatusCode());
    }

    String json = response.getBody();

    // If the response contained an error code, throw an exception.
    throwFacebookResponseStatusExceptionIfNecessary(json, response.getStatusCode());

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response.getStatusCode()) {
      throw new LinkedInNetworkException("Facebook request failed", response.getStatusCode());
    }

    return json;
  }
  
  protected interface Requestor {
    Response makeRequest() throws IOException;
  }
  
  /**
   * Throws an exception if Facebook returned an error response. Using the Graph API, it's possible to see both the new
   * Graph API-style errors as well as Legacy API-style errors, so we have to handle both here. This method extracts
   * relevant information from the error JSON and throws an exception which encapsulates it for end-user consumption.
   * <p>
   * For Graph API errors:
   * <p>
   * If the {@code error} JSON field is present, we've got a response status error for this API call.
   * <p>
   * For Legacy errors (e.g. FQL):
   * <p>
   * If the {@code error_code} JSON field is present, we've got a response status error for this API call.
   * 
   * @param json
   *          The JSON returned by Facebook in response to an API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws FacebookGraphException
   *           If the JSON contains a Graph API error response.
   * @throws FacebookResponseStatusException
   *           If the JSON contains an Legacy API error response.
   * @throws FacebookJsonMappingException
   *           If an error occurs while processing the JSON.
   */
  protected void throwFacebookResponseStatusExceptionIfNecessary(String json, Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      // If we have a legacy exception, throw it.
      throwLegacyFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

      // If we have a batch API exception, throw it.
      throwBatchFacebookResponseStatusExceptionIfNecessary(json, httpStatusCode);

      JsonObject errorObject = new JsonObject(json);

      if (!errorObject.has(ERROR_ATTRIBUTE_NAME)) {
        return;
      }

      JsonObject innerErrorObject = errorObject.getJsonObject(ERROR_ATTRIBUTE_NAME);

      // If there's an Integer error code, pluck it out.
      Integer errorCode = innerErrorObject.has(ERROR_CODE_ATTRIBUTE_NAME)
          ? toInteger(innerErrorObject.getString(ERROR_CODE_ATTRIBUTE_NAME)) : null;
      Integer errorSubcode = innerErrorObject.has(ERROR_SUBCODE_ATTRIBUTE_NAME)
          ? toInteger(innerErrorObject.getString(ERROR_SUBCODE_ATTRIBUTE_NAME)) : null;

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(errorCode, errorSubcode, httpStatusCode,
        innerErrorObject.optString(ERROR_TYPE_ATTRIBUTE_NAME), innerErrorObject.getString(ERROR_MESSAGE_ATTRIBUTE_NAME),
        innerErrorObject.optString(ERROR_USER_TITLE_ATTRIBUTE_NAME),
        innerErrorObject.optString(ERROR_USER_MSG_ATTRIBUTE_NAME),
              innerErrorObject.optBoolean(ERROR_IS_TRANSIENT_NAME, false),

              errorObject);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      if (CLIENT_LOGGER.isTraceEnabled()) {
        CLIENT_LOGGER.trace("caught ResponseErrorJsonParsingException - ignoring", ex);
      }
    }
  }

}
