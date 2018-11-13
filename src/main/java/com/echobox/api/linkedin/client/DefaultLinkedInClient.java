
package com.echobox.api.linkedin.client;

import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import com.echobox.api.linkedin.client.WebRequestor.Response;
import com.echobox.api.linkedin.exception.LinkedInAPIException;
import com.echobox.api.linkedin.exception.LinkedInException;
import com.echobox.api.linkedin.exception.LinkedInExceptionMapper;
import com.echobox.api.linkedin.exception.LinkedInJsonMappingException;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.exception.LinkedInOAuthException;
import com.echobox.api.linkedin.exception.LinkedInQueryParseException;
import com.echobox.api.linkedin.exception.ResponseErrorJsonParsingException;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.logging.LinkedInLogger;
import com.echobox.api.linkedin.scope.ScopeBuilder;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.version.Version;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class DefaultLinkedInClient extends BaseLinkedInClient implements LinkedInClient {

  private static final Logger LOGGER = LinkedInLogger.getLoggerInstance();

  /**
   * Reserved method override parameter name.
   */
  protected static final String METHOD_PARAM_NAME = "method";

  /**
   * Reserved "result format" parameter name.
   */
  protected static final String FORMAT_PARAM_NAME = "format";

  /**
   * API error response 'error' attribute name.
   */
  protected static final String ERROR_ATTRIBUTE_NAME = "error";

  /**
   * API error response 'code' attribute name.
   */
  protected static final String ERROR_CODE_ATTRIBUTE_NAME = "code";

  /**
   * API error response 'error_subcode' attribute name.
   */
  protected static final String ERROR_SUBCODE_ATTRIBUTE_NAME = "error_subcode";

  /**
   * API error response 'message' attribute name.
   */
  protected static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "message";

  /**
   * API error response 'type' attribute name.
   */
  protected static final String ERROR_TYPE_ATTRIBUTE_NAME = "type";

  /**
   * API error response 'error_user_msg' attribute name.
   */
  protected static final String ERROR_USER_MSG_ATTRIBUTE_NAME = "error_user_msg";

  protected static final String ERROR_IS_TRANSIENT_NAME = "is_transient";

  /**
   * API error response 'error_user_title' attribute name.
   */
  protected static final String ERROR_USER_TITLE_ATTRIBUTE_NAME = "error_user_title";

  /**
   * Reserved "multiple IDs" parameter name.
   */
  protected static final String IDS_PARAM_NAME = "ids";

  /**
   * Reserved "start" parameter name.
   */
  protected static final String START = "start";

  /**
   * Reserved "end" parameter name.
   */
  protected static final String END = "end";

  /**
   * Graph API access token.
   */
  protected String accessToken;

  /**
   * Knows how to map Graph API exceptions to formal Java exception types.
   */
  protected LinkedInExceptionMapper graphFacebookExceptionMapper;

  /**
   * API endpoint URL.
   */
  protected String LINKEDIN_API_ENDPOINT_URL = "https://api.linkedin.com";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion = Version.V1;

  /**
   * By default this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback = false;

  public DefaultLinkedInClient(String accessToken) throws GeneralSecurityException, IOException {
    this(accessToken, Version.V1);
  }

  public DefaultLinkedInClient(String accessToken, Version apiVersion)
      throws GeneralSecurityException, IOException {
    this(new DefaultWebRequestor(accessToken), new DefaultJsonMapper(), apiVersion);
  }

  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    super();

    verifyParameterPresence("jsonMapper", jsonMapper);
    verifyParameterPresence("webRequestor", webRequestor);

    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    this.apiVersion = apiVersion;
  }

  @Override
  public Version getVersion() {
    return apiVersion;
  }

  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("object", object);
    verifyParameterPresence("objectType", objectType);
    return jsonMapper.toJavaObject(makeRequest(object, parameters), objectType);
  }

  @Override
  public <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters) {
    verifyParameterPresence("ids", ids);
    verifyParameterPresence("connectionType", objectType);

    if (ids.isEmpty()) {
      throw new IllegalArgumentException("The list of IDs cannot be empty.");
    }

    for (Parameter parameter : parameters) {
      if (IDS_PARAM_NAME.equals(parameter.name)) {
        throw new IllegalArgumentException("You cannot specify the '" + IDS_PARAM_NAME
            + "' URL parameter yourself - "
            + "RestFB will populate this for you with "
            + "the list of IDs you passed to this method.");
      }
    }

    // Normalize the IDs
    for (int i = 0; i < ids.size(); i++) {
      String id = ids.get(i).trim();
      if ("".equals(id)) {
        throw new IllegalArgumentException("The list of IDs cannot contain blank strings.");
      }

      ids.set(i, id);
    }

    try {
      String jsonString =
          makeRequest("", parametersWithAdditionalParameter(Parameter.with(IDS_PARAM_NAME,
              StringUtils.join(ids.toArray())), parameters));

      return jsonMapper.toJavaObject(jsonString, objectType);
    } catch (JSONException e) {
      throw new LinkedInJsonMappingException("Unable to map connection JSON to Java objects", e);
    }
  }

  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) {
    verifyParameterPresence("connection", connection);
    verifyParameterPresence("connectionType", connectionType);
    final String fullEndpoint = createEndpointForApiCall(connection, false);
    return new Connection<T>(fullEndpoint, this, makeRequest(connection, parameters),
        connectionType);
  }

  /**
   * @see https://developer.linkedin.com/docs/guide/v2/concepts/paging
   */
  @Override
  public <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType) {
    String connectionJson = makeRequestAndProcessResponse(new Requestor() {
      @Override
      public Response makeRequest() throws IOException {
        String pageURL = URLUtils.replaceOrAddQueryParameter(connectionPageUrl, "format", "json");
        return webRequestor.executeGet(pageURL);
      }
    });

    Parameter[] params = new Parameter[0];
    return new Connection<T>(connectionPageUrl, this, connectionJson, connectionType);
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, Parameter... parameters) {
    throw new UnsupportedOperationException("Publish is not yet implemented");
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    throw new UnsupportedOperationException("Publish is not yet implemented");
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment,
      Parameter... parameters) {
    throw new UnsupportedOperationException("Publish is not yet implemented");
  }

  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    throw new UnsupportedOperationException("Delete is not yet implemented");
  }

  @Override
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode) {
    throw new UnsupportedOperationException("Obtain user access token is not yet implemented");
  }

  @Override
  public AccessToken obtainAppAccessToken(String appId, String appSecret) {
    throw new UnsupportedOperationException("Obtain user access token is not yet implemented");
  }

  @Override
  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  @Override
  public WebRequestor getWebRequestor() {
    return webRequestor;
  }

  @Override
  public String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope,
      Parameter... additionalParameters) {
    throw new UnsupportedOperationException("Get login dialog URL is not yet implemented");
  }

  @Override
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    while (apiCall.startsWith("/"))
      apiCall = apiCall.substring(1);

    String baseUrl = getFacebookGraphEndpointUrl();

    return format("%s/%s", baseUrl, apiCall);
  }

  /**
   * Returns the base endpoint URL for the Graph API.
   * 
   * @return The base endpoint URL for the Graph API.
   */
  protected String getFacebookGraphEndpointUrl() {
    return LINKEDIN_API_ENDPOINT_URL + '/' + apiVersion.getUrlElement();
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
  protected String makeRequest(String endpoint, final boolean executeAsPost,
      final boolean executeAsDelete,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"),
          parameters);
    }

    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }

    final String fullEndpoint =
        createEndpointForApiCall(endpoint, binaryAttachments != null && !binaryAttachments
            .isEmpty());
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
    if (withJsonParameter) {
      parameters = parametersWithAdditionalParameter(Parameter.with(FORMAT_PARAM_NAME, "json"),
          parameters);
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
      parameterStringBuilder.append(urlEncodedValueForParameterName(parameter.name,
          parameter.value));
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
        && HTTP_UNAUTHORIZED != response.getStatusCode() && HTTP_NOT_FOUND != response
            .getStatusCode()
        && HTTP_INTERNAL_ERROR != response.getStatusCode() && HTTP_FORBIDDEN != response
            .getStatusCode()
        && HTTP_NOT_MODIFIED != response.getStatusCode()) {
      throw new LinkedInNetworkException("Facebook request failed", response.getStatusCode());
    }

    String json = response.getBody();

    // If the response contained an error code, throw an exception.
    throwFacebookResponseStatusExceptionIfNecessary(json, response.getStatusCode());

    // If there was no response error information and this was a 500 or 401
    // error, something weird happened on Facebook's end. Bail.
    if (HTTP_INTERNAL_ERROR == response.getStatusCode() || HTTP_UNAUTHORIZED == response
        .getStatusCode()) {
      throw new LinkedInNetworkException("Facebook request failed", response.getStatusCode());
    }

    return json;
  }

  protected interface Requestor {
    Response makeRequest() throws IOException;
  }

  /**
   * TODO: check LinkedIn errors...
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
  protected void throwFacebookResponseStatusExceptionIfNecessary(String json,
      Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      JSONObject errorObject = new JSONObject(json);

      if (!errorObject.has(ERROR_ATTRIBUTE_NAME)) {
        return;
      }

      JSONObject innerErrorObject = errorObject.getJSONObject(ERROR_ATTRIBUTE_NAME);

      // If there's an Integer error code, pluck it out.
      Integer errorCode = innerErrorObject.has(ERROR_CODE_ATTRIBUTE_NAME)
          ? innerErrorObject.getString(ERROR_CODE_ATTRIBUTE_NAME) == null ? null
              : Integer.parseInt(innerErrorObject.getString(ERROR_CODE_ATTRIBUTE_NAME))
          : null;
      Integer errorSubcode = innerErrorObject.has(ERROR_SUBCODE_ATTRIBUTE_NAME)
          ? innerErrorObject.getString(ERROR_SUBCODE_ATTRIBUTE_NAME) == null ? null
              : Integer.parseInt(innerErrorObject.getString(ERROR_SUBCODE_ATTRIBUTE_NAME))
          : null;

      throw graphFacebookExceptionMapper.exceptionForTypeAndMessage(errorCode, errorSubcode,
          httpStatusCode,
          innerErrorObject.optString(ERROR_TYPE_ATTRIBUTE_NAME), innerErrorObject.getString(
              ERROR_MESSAGE_ATTRIBUTE_NAME),
          innerErrorObject.optString(ERROR_USER_TITLE_ATTRIBUTE_NAME),
          innerErrorObject.optString(ERROR_USER_MSG_ATTRIBUTE_NAME),
          innerErrorObject.optBoolean(ERROR_IS_TRANSIENT_NAME, false),

          errorObject);
    } catch (JSONException e) {
      throw new LinkedInJsonMappingException("Unable to process the Facebook API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("caught ResponseErrorJsonParsingException - ignoring", ex);
      }
    }
  }

  /**
   * A canned implementation of {@code FacebookExceptionMapper} that maps Graph API exceptions.
   * <p>
   * Thanks to BatchFB's Jeff Schnitzer for doing some of the legwork to find these exception type names.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.3
   */
  protected static class DefaultGraphFacebookExceptionMapper implements LinkedInExceptionMapper {
    /**
     * @see com.restfb.exception.FacebookExceptionMapper#exceptionForTypeAndMessage(Integer, Integer, Integer, String,
     *      String, String, String, Boolean, JsonObject)
     */
    @Override
    public LinkedInException exceptionForTypeAndMessage(Integer errorCode, Integer errorSubcode,
        Integer httpStatusCode,
        String type, String message, String errorUserTitle, String errorUserMessage,
        Boolean isTransient,
        JSONObject rawError) {
      if ("OAuthException".equals(type) || "OAuthAccessTokenException".equals(type)) {
        return new LinkedInOAuthException(type, message, errorCode, errorSubcode, httpStatusCode,
            errorUserTitle,
            errorUserMessage, isTransient, rawError);
      }

      if ("QueryParseException".equals(type)) {
        return new LinkedInQueryParseException(type, message, errorCode, errorSubcode,
            httpStatusCode, errorUserTitle,
            errorUserMessage, isTransient, rawError);
      }

      // Don't recognize this exception type? Just go with the standard
      // FacebookGraphException.
      return new LinkedInAPIException(type, message, errorCode, errorSubcode, httpStatusCode,
          errorUserTitle,
          errorUserMessage, isTransient, rawError);
    }
  }

}
