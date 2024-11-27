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

import com.echobox.api.linkedin.exception.DefaultLinkedInExceptionMapper;
import com.echobox.api.linkedin.exception.LinkedInAccessTokenException;
import com.echobox.api.linkedin.exception.LinkedInException;
import com.echobox.api.linkedin.exception.LinkedInExceptionMapper;
import com.echobox.api.linkedin.exception.LinkedInJsonMappingException;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.exception.LinkedInOAuthException;
import com.echobox.api.linkedin.exception.ResponseErrorJsonParsingException;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.util.ValidationUtils;
import com.echobox.api.linkedin.version.Version;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of a LinkedIn API client.
 * @author Kenneth Wong
 *
 */
public class DefaultLinkedInClient extends BaseLinkedInClient
    implements LinkedInClient {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(DefaultLinkedInClient.class);

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
   * Request header to put API version
   */
  public static final String HEADER_NAME_VERSION = "Linkedin-Version";

  /**
   * Default LinkedIn-version header
   */
  public static final String DEFAULT_VERSIONED_MONTH = "202411";
  
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
  protected Version apiVersion;
  
  /**
   * LinkedIn-Version header of the API to be used (format: YYYYMM)
   */
  protected String versionedMonth;
  
  /**
   * By default, this is <code>false</code>, so real http DELETE is used
   */
  protected boolean httpDeleteFallback = false;
  
  private final Map<String, String> defaultHeaders;
  
  /**
   * Creates a LinkedIn API client with the given {@code accessToken}.
   *
   * @param accessToken
   *          A LinkedIn OAuth access token.
   */
  public DefaultLinkedInClient(String accessToken) {
    this(accessToken, Version.VERSIONED);
  }
  
  /**
   * Creates a LinkedIn API client with the given {@code accessToken} and {@code apiVersion}
   *
   * @param accessToken
   *          A LinkedIn OAuth access token.
   * @param apiVersion
   *          Version of the API endpoint
   */
  public DefaultLinkedInClient(String accessToken, Version apiVersion) {
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
   * Creates a LinkedIn API client with the given {@code accessToken}, {@code apiVersion}
   *   and {@code versionedMonth}
   *
   * @param accessToken
   *          A LinkedIn OAuth access token.
   * @param apiVersion
   *          Version of the API endpoint
   * @param versionedMonth
   *          LinkedIn-version of the API (in format YYYYMM)
   */
  public DefaultLinkedInClient(String accessToken, Version apiVersion,
      String versionedMonth) {
    this(new DefaultWebRequestor(accessToken), new DefaultJsonMapper(),
        apiVersion, versionedMonth);
  }
  
  /**
   * Creates a LinkedIn API client
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
   * Creates a LinkedIn API client
   *
   * @param webRequestor
   *          The {@link WebRequestor} implementation to use for sending requests to the API
   *          endpoint.
   * @param jsonMapper
   *          The {@link JsonMapper} implementation to use for mapping API response JSON to Java
   *          objects.
   * @param apiVersion
   *          Version of the API endpoint
   * @param versionedMonth
   *          LinkedIn-version of the API (in format YYYYMM)
   */
  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion, String versionedMonth) {
    this(webRequestor, jsonMapper, apiVersion, new DefaultLinkedInExceptionMapper(),
        versionedMonth);
    ValidationUtils.verifyParameterPresence("webRequestor", webRequestor);
  }
  
  /**
   * Creates a LinkedIn API client
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
  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion, LinkedInExceptionMapper linkedinExceptionMapper) {
    this(webRequestor, jsonMapper, apiVersion, linkedinExceptionMapper, DEFAULT_VERSIONED_MONTH);
  }
  
  /**
   * Creates a LinkedIn API client
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
   * @param versionedMonth
   *          LinkedIn-version of the API (in format YYYYMM)
   */
  public DefaultLinkedInClient(WebRequestor webRequestor, JsonMapper jsonMapper,
      Version apiVersion, LinkedInExceptionMapper linkedinExceptionMapper,
      String versionedMonth) {
    ValidationUtils.verifyParameterPresence("jsonMapper", jsonMapper);
    ValidationUtils.verifyParameterPresence("apiVersion", apiVersion);
    ValidationUtils.verifyParameterPresence("linkedinExceptionMapper", linkedinExceptionMapper);
    ValidationUtils.verifyParameterPresence("versionedMonth", versionedMonth);
    
    this.webRequestor = webRequestor;
    this.jsonMapper = jsonMapper;
    this.apiVersion = apiVersion;
    this.linkedinExceptionMapper = linkedinExceptionMapper;
    this.versionedMonth = versionedMonth;
    this.defaultHeaders = new HashMap<>();
    this.defaultHeaders.put(HEADER_NAME_VERSION, versionedMonth);
  }
  
  @Override
  public Version getVersion() {
    return apiVersion;
  }
  
  @Override
  public <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters) {
    ValidationUtils.verifyParameterPresence("object", object);
    ValidationUtils.verifyParameterPresence("objectType", objectType);
    WebRequestor.Response response = makeRequest(object, parameters);
    return jsonMapper.toJavaObject(response.getBody(), objectType);
  }
  
  @Override
  public <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters) {
    ValidationUtils.verifyParameterPresence("connection", connection);
    ValidationUtils.verifyParameterPresence("connectionType", connectionType);
    final String fullEndpoint = createEndpointForApiCall(connection, false);
    
    List<Parameter> parametersToAdd = new ArrayList<>(Arrays.asList(parameters));
    Parameter[] queryParams = parametersToAdd.toArray(new Parameter[parametersToAdd.size()]);
    String parameterString = toParameterString(queryParams);
    final String finalParameterString =
        StringUtils.isBlank(parameterString) ? "" : ("?" + parameterString);
    WebRequestor.Response response = makeRequest(connection, queryParams);
    return new Connection<>(fullEndpoint + finalParameterString, this, response.getBody(),
        connectionType);
  }
  
  @Override
  public <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType) {
    String connectionJson = makeRequestAndProcessResponseJSON(() -> {
      String pageURL = apiVersion.isSpecifyFormat()
          ? URLUtils.replaceOrAddQueryParameter(connectionPageUrl, "format", "json")
          : connectionPageUrl;
      return webRequestor.executeGet(pageURL, defaultHeaders);
    });
    
    return new Connection<T>(connectionPageUrl, this, connectionJson, connectionType);
  }
  
  @Override
  public WebRequestor.Response put(String connection, Object jsonBody,
      BinaryAttachment binaryAttachment, Parameter... parameters) {

    List<BinaryAttachment> attachments = new ArrayList<>();
    if (binaryAttachment != null) {
      attachments.add(binaryAttachment);
    }
  
    return makeRequest(connection, RequestType.PUT, jsonBody, attachments, parameters);
  }
  
  @Override
  public WebRequestor.Response publish(String connection, Object jsonBody,
      Parameter... parameters) {
    return makeRequest(connection, RequestType.POST, jsonBody, new ArrayList<>(), parameters);
  }
  
  @Override
  public <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      Parameter... parameters) {
    return publish(connection, objectType, jsonBody, new ArrayList<>(), parameters);
  }
  
  @Override
  public <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    
    WebRequestor.Response response = makeRequest(connection, RequestType.POST, jsonBody,
        binaryAttachments, parameters);
    return jsonMapper.toJavaObject(response.getBody(), objectType);
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
    
    WebRequestor.Response response = makeRequest(object, RequestType.DELETE,
        null, null, parameters);
    String responseBody = response.getBody();
  
    try {
      JsonValue jObj = Json.parse(responseBody);
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
    
    return "true".equals(responseBody);
  }
  
  @Override
  public AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode) {
    ValidationUtils.verifyParameterPresence("appId", appId);
    ValidationUtils.verifyParameterPresence("appSecret", appSecret);
    ValidationUtils.verifyParameterPresence("redirectUri", redirectUri);
    ValidationUtils.verifyParameterPresence("verificationCode", verificationCode);
    
    try {
      this.webRequestor = new DefaultWebRequestor();
      
      Map<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/x-www-form-urlencoded");
      
      final WebRequestor.Response response = makeRequestFull(ENDPOINT_ACCESS_TOKEN,
          RequestType.POST, null, headers, Collections.emptyList(),
          Parameter.with(GRANT_TYPE_PARAM_NAME, "authorization_code"),
          Parameter.with(CODE_PARAM_NAME, verificationCode),
          Parameter.with(REDIRECT_URI_PARAM_NAME, redirectUri),
          Parameter.with(CLIENT_ID_PARAM_NAME, appId),
          Parameter.with(CLIENT_SECRET_PARAM_NAME, appSecret));
      
      return getAccessTokenFromResponse(response.getBody());
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
  public String getVersionedMonth() {
    return versionedMonth == null ? DEFAULT_VERSIONED_MONTH : versionedMonth;
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
  protected WebRequestor.Response makeRequest(String endpoint, Parameter... parameters) {
    return makeRequest(endpoint, RequestType.GET, null, null, parameters);
  }
  
  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we
   * receive from the endpoint, will append this endpoint on to the base LinkedIn API endpoint
   * before calling.
   *
   * @param endpoint
   *          LinkedIn Graph API endpoint.
   * @param requestType
   *          the web request type to execute.
   * @param jsonBody
   *          Post JSON body
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no
   *          attachment should be sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to LinkedIn as part of the API call.
   * @return The WebRequestor response returned by LinkedIn for the API call.
   * @throws LinkedInException
   *           If an error occurs while making the LinkedIn API POST or processing the response.
   */
  protected WebRequestor.Response makeRequest(String endpoint, RequestType requestType,
      Object jsonBody, final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    
    if (!endpoint.startsWith("/")) {
      endpoint = "/" + endpoint;
    }
    
    final String fullEndpoint = createEndpointForApiCall(endpoint,
        binaryAttachments != null && !binaryAttachments.isEmpty());

    return makeRequestFull(fullEndpoint, requestType, jsonBody,
        defaultHeaders, binaryAttachments, parameters);
  }
  
  /**
   * Coordinates the process of executing the API request GET/POST and processing the response we
   * receive from a full endpoint, will call this endpoint directly without further processing
   * the URL.
   *
   * @param fullEndpoint
   *          LinkedIn Graph API endpoint.
   * @param requestType
   *          the web request type to execute.
   * @param jsonBody
   *          The POST JSON body
   * @param headers
   *          The headers for the request
   * @param binaryAttachments
   *          A list of binary files to include in a {@code POST} request. Pass {@code null} if no
   *          attachment should be sent.
   * @param parameters
   *          Arbitrary number of parameters to send along to LinkedIn as part of the API call.
   * @return The WebRequestor response returned by LinkedIn for the API call.
   */
  protected WebRequestor.Response makeRequestFull(String fullEndpoint, RequestType requestType,
      Object jsonBody, Map<String, String> headers,
      final List<BinaryAttachment> binaryAttachments, Parameter... parameters) {
    verifyParameterLegality(parameters);
    
    if (RequestType.DELETE == requestType && isHttpDeleteFallback()) {
      parameters = parametersWithAdditionalParameter(Parameter.with(METHOD_PARAM_NAME, "delete"),
          parameters);
    }
    
    String parameterString = toParameterString(parameters);
    final String finalParameterString =
        StringUtils.isBlank(parameterString) ? "" : ("?" + parameterString);
    
    return makeRequestAndProcessResponse(new Requestor() {
      /**
       * Make the request
       * @see DefaultLinkedInClient.Requestor#makeRequest()
       */
      @Override
      public WebRequestor.Response makeRequest() throws IOException {
        if (RequestType.GET == requestType) {
          return webRequestor.executeGet(fullEndpoint + finalParameterString, headers);
        }

        String body = jsonBody == null ? null : jsonMapper.toJson(jsonBody, true);
        BinaryAttachment[] attachments = binaryAttachments == null ? null
            : binaryAttachments.toArray(new BinaryAttachment[binaryAttachments.size()]);

        if (RequestType.PUT == requestType) {
          BinaryAttachment attachment = attachments == null || attachments.length == 0
              ? null : attachments[0];
          return webRequestor.executePut(fullEndpoint, parameterString, body, headers, attachment);
        }

        if (RequestType.POST == requestType) {
          return webRequestor.executePost(fullEndpoint, parameterString, body, headers,
              attachments);
        }

        if (RequestType.DELETE == requestType && !isHttpDeleteFallback()) {
          return webRequestor.executeDelete(fullEndpoint + finalParameterString, headers);
        }
        
        throw new IllegalArgumentException("The request type parameter is required");
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
    WebRequestor.Response response = makeRequestAndProcessResponse(requestor);
    return response.getBody();
  }
  
  /**
   * Make request and process the response
   *
   * @param requestor Requestor interface to make requests to the LinkedIn API
   * @return the response
   */
  protected WebRequestor.Response makeRequestAndProcessResponse(Requestor requestor) {
    WebRequestor.Response response;
    
    // Perform a GET or POST to the API endpoint
    try {
      response = requestor.makeRequest();
    } catch (Exception t) {
      throw new LinkedInNetworkException("LinkedIn request failed", t);
    }
    
    // If we get any HTTP response code other than a 200 OK or 400 Bad Request
    // or 401 Not Authorized or 403 Forbidden or 404 Not Found or 500 Internal
    // Server Error or 302 Not Modified or 504 Gateway Timeout or 422 Unprocessable Entity or 429
    // Rate Limit throw an exception.
    if (HttpStatus.SC_OK != response.getStatusCode()
        && HttpStatus.SC_CREATED != response.getStatusCode()
        && HttpStatus.SC_NO_CONTENT != response.getStatusCode()
        && HttpStatus.SC_NOT_MODIFIED != response.getStatusCode()
        && HttpStatus.SC_BAD_REQUEST != response.getStatusCode()
        && HttpStatus.SC_UNAUTHORIZED != response.getStatusCode()
        && HttpStatus.SC_FORBIDDEN != response.getStatusCode()
        && HttpStatus.SC_NOT_FOUND != response.getStatusCode()
        && HttpStatus.SC_UNPROCESSABLE_ENTITY != response.getStatusCode()
        && HttpStatus.SC_INTERNAL_SERVER_ERROR != response.getStatusCode()
        && HttpStatus.SC_GATEWAY_TIMEOUT != response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
    
    String json = response.getBody();
    
    // If the response is 2XX then we do not need to throw an error response
    if (HttpStatus.SC_OK != response.getStatusCode()
        && HttpStatus.SC_CREATED != response.getStatusCode()
        && HttpStatus.SC_NO_CONTENT != response.getStatusCode()) {
      // If the response contained an error code, throw an exception.
      throwLinkedInResponseStatusExceptionIfNecessary(json, response.getStatusCode());
    }
    
    // If there was no response error information and this was a 500
    // error, something weird happened on LinkedIn's end. Bail.
    if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
    
    // If there was no response error information and this was a 401
    // error, something weird happened on LinkedIn's end. Assume it is a Oauth error.
    if (HttpStatus.SC_UNAUTHORIZED == response.getStatusCode()) {
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
    WebRequestor.Response makeRequest() throws IOException;
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
  
}
