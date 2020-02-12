/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echobox.api.linkedin.client;

import com.echobox.api.linkedin.client.WebRequestor.Response;
import com.echobox.api.linkedin.exception.LinkedInAPIException;
import com.echobox.api.linkedin.exception.LinkedInAccessTokenException;
import com.echobox.api.linkedin.exception.LinkedInException;
import com.echobox.api.linkedin.exception.LinkedInExceptionMapper;
import com.echobox.api.linkedin.exception.LinkedInGatewayTimeoutException;
import com.echobox.api.linkedin.exception.LinkedInInteralServerException;
import com.echobox.api.linkedin.exception.LinkedInJsonMappingException;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.exception.LinkedInOAuthException;
import com.echobox.api.linkedin.exception.LinkedInQueryParseException;
import com.echobox.api.linkedin.exception.LinkedInRateLimitException;
import com.echobox.api.linkedin.exception.LinkedInResourceNotFoundException;
import com.echobox.api.linkedin.exception.ResponseErrorJsonParsingException;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.logging.LinkedInLogger;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.util.ValidationUtils;
import com.echobox.api.linkedin.version.Version;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of a LinkedIn API client.
 * @author Joanna
 *
 */
public class DefaultLinkedInClient extends BaseLinkedInClient implements LinkedInClient {

  private static final Logger LOGGER = LinkedInLogger.getLoggerInstance();

  /**
   * HTTP parameter names.
   */
  protected static final String METHOD_PARAM_NAME = "method";
  /**
   * The grant type parameter name.
   */
  protected static final String GRANT_TYPE_PARAM_NAME = "grant_type";
  /**
   * The code parameter name.
   */
  protected static final String CODE_PARAM_NAME = "code";
  /**
   * The redirect URI parameter name
   */
  protected static final String REDIRECT_URI_PARAM_NAME = "redirect_uri";
  /**
   * The client's ID parameter name
   */
  protected static final String CLIENT_ID_PARAM_NAME = "client_id";
  /**
   * The client's secret parameter name
   */
  protected static final String CLIENT_SECRET_PARAM_NAME = "client_secret";

  /**
   * Reserved "result format" parameter name.
   */
  protected static final String FORMAT_PARAM_NAME = "format";

  /**
   * API error response 'code' attribute name.
   */
  protected static final String ERROR_CODE_ATTRIBUTE_NAME = "serviceErrorCode";

  /**
   * API error response 'message' attribute name.
   */
  protected static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "message";

  /**
   * Endpoint to fetch the access token query.
   */
  protected static final String ENDPOINT_ACCESS_TOKEN =
      "https://www.linkedin.com/oauth/v2/accessToken";

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
  protected LinkedInExceptionMapper linkedinExceptionMapper;

  /**
   * API endpoint URL.
   */
  protected static final String LINKEDIN_API_ENDPOINT_URL = "https://api.linkedin.com";

  /**
   * API endpoint URL.
   */
  protected static final String LINKEDIN_MEDIA_API_ENDPOINT_URL =
      "https://api.linkedin.com/media/upload";

  /**
   * Version of API endpoint.
   */
  protected Version apiVersion = Version.DEFAULT_VERSION;

  /**
   * By default this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback = false;

  /**
   * Creates a LinkedIn API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A LinkedIn OAuth access token.
   * @throws GeneralSecurityException
   *          If the DefaultWebRequestor fails to initialise
   * @throws IOException
   *          If the DefaultWebRequestor fails to initialise
   */
  public DefaultLinkedInClient(String accessToken) throws GeneralSecurityException, IOException {
    this(accessToken, Version.V2);
  }

  /**
   * Creates a LinkedIn API client with the given {@code accessToken}.
   * 
   * @param accessToken
   *          A LinkedIn OAuth access token.
   * @param apiVersion
   *          Version of the API endpoint
   * @throws GeneralSecurityException
   *          If the DefaultWebRequestor fails to initialise
   * @throws IOException
   *          If the DefaultWebRequestor fails to initialise
   */
  public DefaultLinkedInClient(String accessToken, Version apiVersion)
      throws GeneralSecurityException, IOException {
    this(new DefaultWebRequestor(accessToken), new DefaultJsonMapper(), apiVersion);
  }

  /**
   * Creates a LinkedIn API client with the given {@code apiVersion}.
   *
   * @param apiVersion
   *          Version of the api endpoint
   */
  public DefaultLinkedInClient(Version apiVersion) {
    this(null, new DefaultJsonMapper(), apiVersion, new DefaultLinkedInExceptionMapper());
  }

  /**
   * Creates a LinkedIn API client with the given {@code accessToken}.
   * 
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API
   *          endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java
   *          objects.
   * @param apiVersion
   *          Version of the API endpoint
   */
  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion) {
    this(webRequestor, jsonMapper, apiVersion, new DefaultLinkedInExceptionMapper());
    ValidationUtils.verifyParameterPresence("webRequestor", webRequestor);
  }

  /**
   * Creates a LinkedIn API client with the given {@code accessToken}.
   *
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API
   *          endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java
   *          objects.
   * @param apiVersion
   *          Version of the API endpoint
   * @param linkedinExceptionMapper
   *          Mapper class to handle LinkedIn exceptions
   */
  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper, Version apiVersion,
      LinkedInExceptionMapper linkedinExceptionMapper) {
    ValidationUtils.verifyParameterPresence("jsonMapper", jsonMapper);
    ValidationUtils.verifyParameterPresence("apiVersion", apiVersion);
    ValidationUtils.verifyParameterPresence("linkedinExceptionMapper", linkedinExceptionMapper);

    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    this.apiVersion = apiVersion;
    this.linkedinExceptionMapper = linkedinExceptionMapper;
  }

  @Override
  public Version getVersion() {
    return apiVersion;
  }

  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    ValidationUtils.verifyParameterPresence("object", object);
    ValidationUtils.verifyParameterPresence("objectType", objectType);
    return jsonMapper.toJavaObject(makeRequest(object, parameters), objectType);
  }

  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) {
    ValidationUtils.verifyParameterPresence("connection", connection);
    ValidationUtils.verifyParameterPresence("connectionType", connectionType);
    final String fullEndpoint = createEndpointForApiCall(connection, false);
    
    List<Parameter> parametersToAdd = new ArrayList<>(Arrays.asList(parameters));
    if (parametersToAdd.stream().noneMatch(parameter -> parameter.name.equals("start"))) {
      parametersToAdd.add(Parameter.with("start", 0));
    }
    if (parametersToAdd.stream().noneMatch(parameter -> parameter.name.equals("count"))) {
      parametersToAdd.add(Parameter.with("count", 10));
    }
    Parameter[] queryParams = parametersToAdd.toArray(new Parameter[parametersToAdd.size()]);
    String parameterString = toParameterString(queryParams);
    final String finalParameterString =
        StringUtils.isBlank(parameterString) ? "" : ("?" + parameterString);
    return new Connection<T>(fullEndpoint + finalParameterString, this,
        makeRequest(connection, queryParams), connectionType);
  }

  @Override
  public <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType) {
    String connectionJson = makeRequestAndProcessResponseJSON(new Requestor() {
      @Override
      public Response makeRequest() throws IOException {
        String pageURL = apiVersion.isSpecifyFormat()
            ? URLUtils.replaceOrAddQueryParameter(connectionPageUrl, "format", "json")
            : connectionPageUrl;
        return webRequestor.executeGet(pageURL);
      }
    });

    return new Connection<T>(connectionPageUrl, this, connectionJson, connectionType);
  }
  
  @Override
  public <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      Parameter... parameters) {
    return publish(connection, objectType, jsonBody, new ArrayList<>(), parameters);
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters) {

    return jsonMapper.toJavaObject(makeRequest(connection, true, false, jsonBody,
        binaryAttachments, parameters), objectType);
  }

  @Override
  public <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      BinaryAttachment binaryAttachment, Parameter... parameters) {
    List<BinaryAttachment> attachments = null;
    if (binaryAttachment != null) {
      attachments = new ArrayList<>();
      attachments.add(binaryAttachment);
    }

    return publish(connection, objectType, jsonBody, attachments, parameters);
  }

  @Override
  public boolean deleteObject(String object, Parameter... parameters) {
    ValidationUtils.verifyParameterPresence("object", object);

    String responseString = makeRequest(object, false, true, null, null, parameters);
    try {
      JsonValue jObj = Json.parse(responseString);
      if (jObj.isObject()) {
        if (jObj.asObject().get("result") != null) {
          return jObj.asObject().get("result").asString().contains("Successfully deleted");
        }
        if (jObj.asObject().get("success") != null) {
          return jObj.asObject().get("success").asBoolean();
        }
        return false;
      }
    } catch (ParseException jex) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("no valid JSON returned while deleting a object, using returned String "
            + "instead", jex);
      }
    }

    return "true".equals(responseString);
  }

  @Override
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode) {
    ValidationUtils.verifyParameterPresence("appId", appId);
    ValidationUtils.verifyParameterPresence("appSecret", appSecret);
    ValidationUtils.verifyParameterPresence("redirectUri", redirectUri);
    ValidationUtils.verifyParameterPresence("verificationCode", verificationCode);

    try {
      this.webRequestor = new DefaultWebRequestor(appId, appSecret);

      final String response = makeRequestFull(ENDPOINT_ACCESS_TOKEN, true, false, new JsonObject(),
          null, Collections.emptyList(), Parameter.with(GRANT_TYPE_PARAM_NAME,
          "authorization_code"),
          Parameter.with(CODE_PARAM_NAME, verificationCode),
          Parameter.with(REDIRECT_URI_PARAM_NAME, redirectUri),
          Parameter.with(CLIENT_ID_PARAM_NAME, appId),
          Parameter.with(CLIENT_SECRET_PARAM_NAME, appSecret));

      return getAccessTokenFromResponse(response);
    } catch (Exception ex) {
      throw new LinkedInAccessTokenException(ex);
    }
  }

  private AccessToken getAccessTokenFromResponse(String response) {
    try {
      return getJsonMapper().toJavaObject(response, AccessToken.class);
    } catch (LinkedInJsonMappingException ex) {
      LOGGER.trace("could not map response to access token class try to fetch directly from String",
          ex);
      return AccessToken.fromQueryString(response);
    }
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
  protected String createEndpointForApiCall(String apiCall, boolean hasAttachment) {
    while (apiCall.startsWith("/")) {
      apiCall = apiCall.substring(1);
    }

    if (hasAttachment) {
      return getLinkedInMediaEndpointUrl();
    }

    String baseUrl = getLinkedInEndpointUrl();

    return String.format("%s/%s", baseUrl, apiCall);
  }

  /**
   * Returns the base endpoint URL for the LinkedIn API.
   * 
   * @return The base endpoint URL for the LinkedIn API.
   */
  protected String getLinkedInEndpointUrl() {
    return LINKEDIN_API_ENDPOINT_URL + '/' + apiVersion.getUrlElement();
  }

  /**
   * Gets LinkedIn media endpoint URL
   *
   * @return LinkedIn media endpoint URL
   */
  protected String getLinkedInMediaEndpointUrl() {
    return LINKEDIN_MEDIA_API_ENDPOINT_URL;
  }

  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we 
   * receive from the endpoint.
   * 
   * @param endpoint
   *          LinkedIn Graph API endpoint.
   * @param parameters
   *          Arbitrary number of parameters to send along to LinkedIn as part of the API call.
   * @return The JSON returned by LinkedIn for the API call.
   * @throws LinkedInException
   *           If an error occurs while making the LinkedIn API POST or processing the response.
   */
  protected String makeRequest(String endpoint, Parameter... parameters) {
    return makeRequest(endpoint, false, false, null, null, parameters);
  }

  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we 
   * receive from the endpoint, will append this endpoint on to the base LinkedIn API endpoint 
   * before calling.
   * 
   * @param endpoint
   *          LinkedIn Graph API endpoint.
   * @param executeAsPost
   *          {@code true} to execute the web request as a {@code POST}, {@code false} to execute 
   *          as a {@code GET}.
   * @param executeAsDelete
   *          {@code true} to add a special 'treat this request as a {@code DELETE}' parameter.
   * @param jsonBody
   *          Post JSON body
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no 
   *          attachment should be sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to LinkedIn as part of the API call.
   * @return The JSON returned by LinkedIn for the API call.
   * @throws LinkedInException
   *           If an error occurs while making the LinkedIn API POST or processing the response.
   */
  protected String makeRequest(String endpoint, final boolean executeAsPost,
      final boolean executeAsDelete, Object jsonBody,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {

    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }

    final String fullEndpoint = createEndpointForApiCall(endpoint,
        binaryAttachments != null && !binaryAttachments.isEmpty());

    return makeRequestFull(fullEndpoint, executeAsPost, executeAsDelete, jsonBody,
        null, binaryAttachments, parameters);
  }

  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we 
   * receive from a full endpoint, will call this endpoint directly without further processing 
   * the URL.
   *
   * @param fullEndpoint
   *          LinkedIn Graph API endpoint.
   * @param executeAsPost
   *          {@code true} to execute the web request as a {@code POST}, {@code false} to execute 
   *          as a {@code GET}.
   * @param executeAsDelete
   *          {@code true} to add a special 'treat this request as a {@code DELETE}' parameter.
   * @param jsonBody
   *          The POST JSON body
   * @param headers
   *          The headers for the request
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no 
   *          attachment should be sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to LinkedIn as part of the API call.
   * @return The JSON returned by LinkedIn for the API call.
   */
  protected String makeRequestFull(String fullEndpoint, final boolean executeAsPost,
      final boolean executeAsDelete, Object jsonBody, Map<String, String> headers,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);

    if (executeAsDelete && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"),
          parameters);
    }

    String parameterString = toParameterString(parameters);
    final String finalParameterString =
        StringUtils.isBlank(parameterString) ? "" : ("?" + parameterString);

    return makeRequestAndProcessResponseJSON(new Requestor() {
      /**
       * Make the request
       * @see com.echobox.api.linkedin.client.DefaultLinkedInClient.Requestor#makeRequest()
       */
      @Override
      public Response makeRequest() throws IOException {
        if (executeAsDelete && !isHttpDeleteFallback()) {
          return webRequestor.executeDelete(fullEndpoint + finalParameterString);
        } else {
          return executeAsPost
              ? webRequestor.executePost(fullEndpoint, parameterString,
                  jsonBody == null ? null : jsonMapper.toJson(jsonBody, true),
                  headers,
                  binaryAttachments == null ? null
                      : binaryAttachments.toArray(new BinaryAttachment[binaryAttachments.size()]))
              : webRequestor.executeGet(fullEndpoint + finalParameterString);
        }
      }
    });
  }

  /**
   * Generate the parameter string to be included in the LinkedIn API request.
   * 
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the LinkedIn API request.
   * @throws LinkedInJsonMappingException
   *           If an error occurs when building the parameter string.
   */
  protected String toParameterString(Parameter... parameters) {
    return toParameterString(apiVersion.isSpecifyFormat(), parameters);
  }

  /**
   * Generate the parameter string to be included in the LinkedIn API request.
   * 
   * @param withJsonParameter
   *          add additional parameter format with type json
   * @param parameters
   *          Arbitrary number of extra parameters to include in the request.
   * @return The parameter string to include in the LinkedIn API request.
   * @throws LinkedInJsonMappingException
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
      parameterStringBuilder.append(urlEncodedValueForParameterName(parameter.value));
    }

    return parameterStringBuilder.toString();
  }

  /**
   * returns if the fallback post method (<code>true</code>) is used or the http delete
   * (<code>false</code>)
   * 
   * @return a flag whether HTTP delete is a fallback
   */
  public boolean isHttpDeleteFallback() {
    return httpDeleteFallback;
  }

  /**
   * Make request and process response (json).
   *
   * @param requestor Requestor interface to make requests to the LinkedIn API
   * @return the JSON response
   */
  protected String makeRequestAndProcessResponseJSON(Requestor requestor) {
    Response response = makeRequestAndProcessResponse(requestor);
    return response.getBody();
  }

  /**
   * Make request and process the response
   *
   * @param requestor Requestor interface to make requests to the LinkedIn API
   * @return the response
   */
  protected Response makeRequestAndProcessResponse(Requestor requestor) {
    Response response;
    
    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
    } catch (Exception t) {
      throw new LinkedInNetworkException("LinkedIn request failed", t);
    }
    
    // If we get any HTTP response code other than a 200 OK or 400 Bad Request
    // or 401 Not Authorized or 403 Forbidden or 404 Not Found or 500 Internal
    // Server Error or 302 Not Modified or 504 Gateway Timeout or 429 Rate Limit
    // throw an exception.
    if (HttpURLConnection.HTTP_OK != response.getStatusCode()
        && HttpURLConnection.HTTP_CREATED != response.getStatusCode()
        && HttpURLConnection.HTTP_NO_CONTENT != response.getStatusCode()
        && HttpURLConnection.HTTP_BAD_REQUEST != response.getStatusCode()
        && HttpURLConnection.HTTP_UNAUTHORIZED != response.getStatusCode()
        && HttpURLConnection.HTTP_NOT_FOUND != response.getStatusCode()
        && HttpURLConnection.HTTP_INTERNAL_ERROR != response.getStatusCode()
        && HttpURLConnection.HTTP_FORBIDDEN != response.getStatusCode()
        && HttpURLConnection.HTTP_NOT_MODIFIED != response.getStatusCode()
        && HttpURLConnection.HTTP_GATEWAY_TIMEOUT != response.getStatusCode()
        && 429 != response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
    
    String json = response.getBody();
    
    // If the response is 2XX then we do not need to throw an error response
    if (HttpURLConnection.HTTP_OK != response.getStatusCode()
        && HttpURLConnection.HTTP_CREATED != response.getStatusCode()
        && HttpURLConnection.HTTP_NO_CONTENT != response.getStatusCode()) {
      // If the response contained an error code, throw an exception.
      throwLinkedInResponseStatusExceptionIfNecessary(json, response.getStatusCode());
    }
    
    // If there was no response error information and this was a 500
    // error, something weird happened on LinkedIn's end. Bail.
    if (HttpURLConnection.HTTP_INTERNAL_ERROR == response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
  
    // If there was no response error information and this was a 401
    // error, something weird happened on LinkedIn's end. Assume it is a Oauth error.
    if (HttpURLConnection.HTTP_UNAUTHORIZED == response.getStatusCode()) {
      throw new LinkedInOAuthException("LinkedIn request failed", response.getStatusCode());
    }
    
    return response;
  }

  /**
   * Requestor interface to make requests to the LinkedIn API
   * @author Joanna
   *
   */
  protected interface Requestor {
    /**
     * Make a request
     * @return the received response
     * @throws IOException the IO exception
     */
    Response makeRequest() throws IOException;
  }

  /**
   * Throws an exception if LinkedIn returned an error response.
   * This method extracts relevant information from the error JSON and throws an exception which
   * encapsulates it for end-user consumption.
   * For API errors:
   * If the {@code error} JSON field is present, we've got a response status error for this API
   * call.
   *
   * @param json
   *          The JSON returned by LinkedIn in response to an API call.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @throws LinkedInJsonMappingException
   *           If an error occurs while processing the JSON.
   */
  protected void throwLinkedInResponseStatusExceptionIfNecessary(String json,
      Integer httpStatusCode) {
    try {
      skipResponseStatusExceptionParsing(json);

      JsonObject errorObject = Json.parse(json).asObject();

      // If there's an Integer error code, pluck it out.
      Integer errorCode = errorObject.get(ERROR_CODE_ATTRIBUTE_NAME) != null
          ? Integer.parseInt(errorObject.get(ERROR_CODE_ATTRIBUTE_NAME).toString())
          : null;

      if (linkedinExceptionMapper != null) {
        throw linkedinExceptionMapper.exceptionForTypeAndMessage(errorCode, httpStatusCode,
            errorObject.getString(ERROR_MESSAGE_ATTRIBUTE_NAME, ""), false, errorObject);
      }
    } catch (ParseException e) {
      throw new LinkedInJsonMappingException("Unable to process the LinkedIn API response", e);
    } catch (ResponseErrorJsonParsingException ex) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("caught ResponseErrorJsonParsingException - ignoring", ex);
      }
    }
  }

  /**
   * Implementation of {@code LinkedInExceptionMapper} that maps LinkedIn API exceptions.
   * @author Joanna
   */
  protected static class DefaultLinkedInExceptionMapper implements LinkedInExceptionMapper {
    @Override
    public LinkedInException exceptionForTypeAndMessage(Integer errorCode, Integer httpStatusCode,
        String message, Boolean isTransient, JsonObject rawError) {
      // Bad Request - client mistakes
      if (new Integer(400).equals(httpStatusCode)) {
        return new LinkedInQueryParseException(message, errorCode, httpStatusCode, rawError);
      }

      // Unauthorised
      if (new Integer(401).equals(httpStatusCode)) {
        return new LinkedInOAuthException(message, errorCode, httpStatusCode, rawError);
      }

      // Resource not found
      if (new Integer(404).equals(httpStatusCode)) {
        return new LinkedInResourceNotFoundException(message, errorCode, httpStatusCode,
            rawError);
      }

      // 429 Rate limit
      if (new Integer(429).equals(httpStatusCode)) {
        return new LinkedInRateLimitException(message, errorCode, httpStatusCode,
            rawError);
      }

      // Internal Server Error
      if (new Integer(500).equals(httpStatusCode)) {
        return new LinkedInInteralServerException(message, errorCode, httpStatusCode, rawError);
      }

      // Gateway timeout
      if (new Integer(504).equals(httpStatusCode)) {
        return new LinkedInGatewayTimeoutException(message, errorCode, httpStatusCode, rawError);
      }

      // Don't recognize this exception type? Just go with the standard LinkedInAPIException.
      return new LinkedInAPIException(message, errorCode, httpStatusCode, rawError);
    }
  }

}
