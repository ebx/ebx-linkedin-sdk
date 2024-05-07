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

import static java.lang.String.format;

import com.echobox.api.linkedin.util.JsonUtils;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Default implementation of a service that sends HTTP requests to the LinkedIn API endpoint.
 * 
 * @author Joanna
 *
 */
public class DefaultWebRequestor implements WebRequestor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWebRequestor.class);
  
  private static final int DEFAULT_CONNECT_TIMEOUT_MS = 10000;
  private static final int DEFAULT_READ_TIMEOUT_MS = 30000;
  
  private final List<String> headers;
  private final HttpClient httpClient;
  private final int readTimeout;

  /**
   * Arbitrary unique boundary marker for multipart {@code POST}s.
   */
  private static final String MULTIPART_BOUNDARY =
      "**boundarystringwhichwill**neverbeencounteredinthewild**";

  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final String CLIENT_ID_KEY = "client_id";

  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final String CLIENT_SECRET_KEY = "client_secret";

  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final String INSTALLED_KEY = "installed";

  /**
   * Default charset to use for encoding/decoding strings.
   */
  public static final String ENCODING_CHARSET = "UTF-8";
  
  private static final String FORMAT_HEADER = "x-li-format";

  /**
   * By default, how long should we wait for a response (in ms)?
   */
  private static final int DEFAULT_READ_TIMEOUT_IN_MS = 180000;

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  private Map<String, Object> currentHeaders;
  
  private Map<String, List<String>> currentHttpHeaders;

  private DebugHeaderInfo debugHeaderInfo;

  private HttpRequestFactory requestFactory;

  /**
   * By default, this is true, to prevent breaking existing usage
   */
  private boolean autocloseBinaryAttachmentStream = true;

  /**
   * HTTP methods available
   * @author Joanna
   *
   */
  protected enum HttpMethod {
    /**
     * Get http method.
     */
    GET,
    /**
     * Delete http method.
     */
    DELETE,
    /**
     * Post http method.
     */
    POST
  }

  /**
   * Initialise the default web requestor which uses OAuth2
   * @param accessToken A LinkedIn OAuth access token
   * @throws GeneralSecurityException Thrown when the OAuth2 client fails to initialise
   * @throws IOException Thrown when the OAuth2 client fails to initialise
   */
  public DefaultWebRequestor(String accessToken) throws GeneralSecurityException, IOException {
    this(null, null, accessToken);
  }

  /**
   * Initialise the default web requestor which uses OAuth2
   * @param clientId A LinkedIn client id
   * @param clientSecret A LinkedIn client secret
   * @throws GeneralSecurityException thrown when the OAuth2 client fails to initialise
   * @throws IOException thrown when the OAuth2 client fails to initialise
   */
  public DefaultWebRequestor(String clientId, String clientSecret) throws GeneralSecurityException,
      IOException {
    this(clientId, clientSecret, null);
  }

  /**
   * Initialise the default web requestor which uses OAuth2
   * @param clientId A LinkedIn client id
   * @param clientSecret A LinkedIn client secret
   * @param accessToken A LinkedIn OAuth access token
   * @throws GeneralSecurityException thrown when the OAuth2 client fails to initialise
   * @throws IOException thrown when the OAuth2 client fails to initialise
   */
  public DefaultWebRequestor(String clientId, String clientSecret, String accessToken)
      throws GeneralSecurityException, IOException {
    this.requestFactory = authorize(clientId, clientSecret, accessToken);
    this.headers = accessToken != null ? Arrays.asList("Authorization",
        String.format("Bearer %s", accessToken)) : Collections.emptyList();
    this.httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofMillis(DEFAULT_CONNECT_TIMEOUT_MS)).build();
    this.readTimeout = DEFAULT_READ_TIMEOUT_MS;
  }

  private HttpRequestFactory authorize(String clientId, String clientSecret, String accessToken)
      throws GeneralSecurityException, IOException {
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

    GoogleClientSecrets clientSecrets = null;
    if (clientId != null && clientSecret != null) {
      // Load client secrets
      JsonObject appTokens = new JsonObject().add(CLIENT_ID_KEY, clientId)
          .add(CLIENT_SECRET_KEY, clientSecret);
      String installedAppTokens = new JsonObject().add(INSTALLED_KEY, appTokens).toString();
      clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
          new InputStreamReader(new ByteArrayInputStream(installedAppTokens.getBytes())));
    }

    // Set up authorization code flow
    Builder credentiaBuilder =
        new GoogleCredential.Builder()
            .setJsonFactory(JSON_FACTORY)
            .setTransport(httpTransport);

    if (clientSecrets != null) {
      credentiaBuilder.setClientSecrets(clientSecrets);
    }

    GoogleCredential credential = credentiaBuilder.build();

    if (accessToken != null) {
      credential.setAccessToken(accessToken);
    }

    HttpRequestFactory requestFactory =
        httpTransport.createRequestFactory(new HttpRequestInitializer() {
          @Override
          public void initialize(HttpRequest request) throws IOException {
            credential.initialize(request);
            request.setParser(new JsonObjectParser(JSON_FACTORY));
          }
        });

    return requestFactory;
  }

  @Override
  public Response executeGet(String url) throws IOException {
    return executeGet(url, null);
  }
  
  @Override
  public Response executeGet(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, java.net.http.HttpRequest.Builder::GET, headers);
  }

  @Override
  public Response executePost(String url, String parameters, String jsonBody) throws IOException {
    return executePost(url, parameters, jsonBody, null, new BinaryAttachment[0]);
  }

  // CPD-OFF
  @Override
  public Response executePost(String url, String parameters, String jsonBody,
      Map<String, String> headers,
      BinaryAttachment... binaryAttachments)
      throws IOException {
    
    if (binaryAttachments == null) {
      binaryAttachments = new BinaryAttachment[0];
    }

    try {
      GenericUrl genericUrl = getGenericURL(url, parameters);

      HttpRequest request;
      HttpHeaders httpHeaders = new HttpHeaders();
  
      // If we have binary attachments, the body is just the attachments and the
      // other parameters are passed in via the URL.
      // Otherwise the body is the URL parameter string.
      if (binaryAttachments.length > 0) {
        // Set the media type
        MultipartContent content = new MultipartContent()
            .setMediaType(new HttpMediaType("multipart/form-data")
                .setParameter("boundary", MULTIPART_BOUNDARY));
        
        for (BinaryAttachment binaryAttachment : binaryAttachments) {
          MultipartContent.Part part = new MultipartContent.Part(
              new ByteArrayContent(binaryAttachment.getContentType(), binaryAttachment.getData()));
          HttpHeaders set = new HttpHeaders().set("Content-Disposition",
              format("form-data; name=\"%s\"; filename=\"%s\"",
                  createFormFieldName(binaryAttachment), binaryAttachment.getFilename()));
  
          part.setHeaders(set);
          content.addPart(part);
  
          httpHeaders.set("Connection", "Keep-Alive");
        }
        
        request = requestFactory.buildPostRequest(genericUrl, content);
      } else {
        if (jsonBody != null) {
          request = requestFactory.buildPostRequest(genericUrl, getJsonHttpContent(jsonBody));

          // Ensure the response headers are also set to JSON
          request.setResponseHeaders(new HttpHeaders().set(FORMAT_HEADER, "json"));
        } else {
          // Plain old POST request
          request = requestFactory.buildPostRequest(genericUrl, new EmptyContent());
        }
      }

      request.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set their own headers,
      // timeouts, etc.
      customizeConnection(request);
  
      addHeadersToRequest(request, httpHeaders, headers);
  
      if (LOGGER.isTraceEnabled()) {
        String body = StringUtils.isEmpty(jsonBody) ? "no payload"
            : format("payload: %s", jsonBody);
        LOGGER.trace(format("Executing a POST to %s with %s and headers: %s.",
            request.getUrl().toString(), body, request.getHeaders().toString()));
      }

      return getResponse(request);
    } catch (HttpResponseException ex) {
      return handleException(ex);
    } finally {
      if (autocloseBinaryAttachmentStream && binaryAttachments.length > 0) {
        for (BinaryAttachment binaryAttachment : binaryAttachments) {
          closeQuietly(binaryAttachment.getDataInputStream());
        }
      }
    }
  }
  
  @Override
  public Response executePut(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment binaryAttachment)
      throws IOException {
  
    try {
      GenericUrl genericUrl = getGenericURL(url, parameters);
    
      HttpRequest request;
      HttpHeaders httpHeaders = new HttpHeaders();
    
      // If we have binary attachments, the body is just the attachments and the
      // other parameters are passed in via the URL.
      // Otherwise the body is the URL parameter string.
      if (binaryAttachment != null) {
        // Set the content type
        // If it's not provided assume it's application/octet-stream
        String contentType =
            headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase("content-type"))
                .map(Map.Entry::getValue).findFirst().orElse("application/octet-stream");
        ByteArrayContent fileContent =
            new ByteArrayContent(contentType, binaryAttachment.getData());
        request = requestFactory.buildPutRequest(genericUrl, fileContent);
        httpHeaders.set("Connection", "Keep-Alive");
      } else {
        if (jsonBody != null) {
          request = requestFactory.buildPutRequest(genericUrl, getJsonHttpContent(jsonBody));
        
          // Ensure the response headers are also set to JSON
          request.setResponseHeaders(new HttpHeaders().set(FORMAT_HEADER, "json"));
        } else {
          // Plain old PUT request
          request = requestFactory.buildPutRequest(genericUrl, null);
        }
      }
    
      request.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
    
      // Allow subclasses to customize the connection if they'd like to - set their own headers,
      // timeouts, etc.
      customizeConnection(request);
  
      addHeadersToRequest(request, httpHeaders, headers);
    
      if (LOGGER.isTraceEnabled()) {
        String body = StringUtils.isEmpty(jsonBody) ? "no payload"
            : format("payload: %s", jsonBody);
        LOGGER.trace(format("Executing a PUT to %s with %s and headers: %s.",
            request.getUrl().toString(), body, request.getHeaders().toString()));
      }
  
      return getResponse(request);
    } catch (HttpResponseException ex) {
      return handleException(ex);
    } finally {
      if (autocloseBinaryAttachmentStream && binaryAttachment != null) {
        closeQuietly(binaryAttachment.getDataInputStream());
      }
    }
  }
  // CPD-ON
  
  private Response getResponse(HttpRequest request) throws IOException {
    HttpResponse httpResponse = request.execute();

    fillHeaderAndDebugInfo(httpResponse.getHeaders());

    Response response = fetchResponse(httpResponse);

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(format("LinkedIn responded with %s", response));
    }

    return response;
  }
  
  private Response getResponse(java.net.http.HttpRequest request)
      throws IOException, InterruptedException {
    java.net.http.HttpResponse<String> httpResponse =
        httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
  
    fillHeaderAndDebugInfo(httpResponse.headers());
  
    Response response =
        fetchResponse(httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
  
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(format("LinkedIn responded with %s", response));
    }
    return response;
  }

  private Response handleException(HttpResponseException ex) {
    fillHeaderAndDebugInfo(ex.getHeaders());

    Response response = fetchResponse(ex.getStatusCode(), ex.getHeaders(), ex.getContent());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(format("LinkedIn responded with an error %s", response));
    }

    return response;
  }

  /**
   * Creates the form field name for the binary attachment filename by stripping off the 
   * file extension - for example,
   * the filename "test.png" would return "test".
   * 
   * @param binaryAttachment
   *          The binary attachment for which to create the form field name.
   * @return The form field name for the given binary attachment.
   */
  protected String createFormFieldName(BinaryAttachment binaryAttachment) {
    if (binaryAttachment.getFieldName() != null) {
      return binaryAttachment.getFieldName();
    }

    String name = binaryAttachment.getFilename();
    int fileExtensionIndex = name.lastIndexOf('.');
    return fileExtensionIndex > 0 ? name.substring(0, fileExtensionIndex) : name;
  }

  /**
   * Hook method which allows subclasses to easily customise the HTTP request connection
   * This implementation is a no-op.
   * 
   * @param connection The connection to customize.
   */
  protected void customizeConnection(HttpRequest connection) {
    // This implementation is a no-op
  }
  
  /**
   * Hook method which allows subclasses to easily customise the HTTP request connection
   * This implementation is a no-op.
   *
   * @param connection The connection to customize.
   */
  protected void customizeConnection(java.net.http.HttpRequest.Builder connection) {
    // This implementation is a no-op
  }

  /**
   * Attempts to cleanly close a resource, swallowing any exceptions that might occur since 
   * there's no way to recover
   * anyway.
   * <p>
   * It's OK to pass {@code null} in, this method will no-op in that case.
   * 
   * @param closeable
   *          The resource to close.
   */
  protected void closeQuietly(Closeable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (Exception t) {
      LOGGER.warn(format("Unable to close %s: ", closeable), t);
    }
  }

  /**
   * Attempts to cleanly disconnect the response, swallowing any exceptions that might occur since
   * there's no way to recover anyway.
   * 
   * @param response The HTTP response to close.
   */
  protected void closeQuietly(HttpResponse response) {
    if (response == null) {
      return;
    }
    try {
      response.disconnect();
    } catch (Exception t) {
      LOGGER.warn(format("Unable to disconnect %s: ", response), t);
    }
  }

  /**
   * Writes the contents of the {@code source} stream to the {@code destination} stream using the 
   * given {@code bufferSize}.
   * 
   * @param source The source stream to copy from.
   * @param destination The destination stream to copy to.
   * @param bufferSize The size of the buffer to use during the copy operation.
   * @throws IOException If an error occurs when reading from {@code source} or writing to
   * {@code destination}.
   * @throws NullPointerException If either {@code source} or @{code destination} is {@code null}.
   */
  protected void write(InputStream source, OutputStream destination, int bufferSize)
      throws IOException {
    if (source == null || destination == null) {
      throw new NullPointerException("Must provide non-null source and destination streams.");
    }

    int read;
    byte[] chunk = new byte[bufferSize];
    while ((read = source.read(chunk)) > 0) {
      destination.write(chunk, 0, read);
    }
  }

  /**
   * Access to the current response headers
   * 
   * @return the current response header map
   */
  public Map<String, Object> getCurrentHeaders() {
    return currentHeaders;
  }
  
  /**
   * Access to the current response headers
   *
   * @return the current response header map
   */
  public Map<String, List<String>> getCurrentHttpHeaders() {
    return currentHttpHeaders;
  }
  
  
  @Override
  public Response executeDelete(String url) throws IOException {
    return executeDelete(url, null);
  }

  @Override
  public Response executeDelete(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, java.net.http.HttpRequest.Builder::DELETE, headers);
  }
  
  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return debugHeaderInfo;
  }
  
  
  private Response execute(String url, String parameters,
      Consumer<java.net.http.HttpRequest.Builder> requestBuilder, Map<String, String> customHeaders)
      throws IOException {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Executing request {} with parameters: {} and headers: {}", url, parameters,
          headers);
    }
    
    try {
      URI uri = getURI(url, parameters);
      
      java.net.http.HttpRequest.Builder builder =
          java.net.http.HttpRequest.newBuilder(uri).timeout(Duration.ofMillis(readTimeout));
      
      if (customHeaders != null && !customHeaders.isEmpty()) {
        customHeaders.forEach(builder::header);
      }
      if (headers != null && !headers.isEmpty()) {
        builder.headers(headers.toArray(new String[0]));
      }
      
      customizeConnection(builder);
      requestBuilder.accept(builder);
      
      return getResponse(builder.build());
    } catch (URISyntaxException | InterruptedException ex) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("LinkedIn responded with an error {}", ex.getMessage());
      }
      throw new IOException(ex);
    }
  }
  
  private URI getURI(String url, String parameters) throws URISyntaxException {
    String parametersToAppend = "";
    if (StringUtils.isNotEmpty(parameters)) {
      parametersToAppend = parameters.startsWith("?") ? parameters : "?" + parameters;
    }
    return new URI(url + parametersToAppend);
  }
  

  /**
   * Fill header and debug info.
   *
   * @param httpHeaders the http headers
   */
  protected void fillHeaderAndDebugInfo(HttpHeaders httpHeaders) {
    currentHeaders = httpHeaders;

    String liFabric = StringUtils.trimToEmpty(httpHeaders.getFirstHeaderStringValue("x-li-fabric"));
    String liFormat = StringUtils.trimToEmpty(httpHeaders.getFirstHeaderStringValue("x-li-format"));
    String liRequestId = StringUtils.trimToEmpty(httpHeaders.getFirstHeaderStringValue(
        "x-li-request-id"));
    String liUUID = StringUtils.trimToEmpty(httpHeaders.getFirstHeaderStringValue("x-li-uuid"));
    debugHeaderInfo = new DebugHeaderInfo(liFabric, liFormat, liRequestId, liUUID);
  }
  
  private void fillHeaderAndDebugInfo(java.net.http.HttpHeaders httpHeaders) {
    currentHttpHeaders = httpHeaders.map();
  
    String liFabric = httpHeaders.firstValue("x-li-fabric").orElse("");
    String liFormat = httpHeaders.firstValue("x-li-format").orElse("");
    String liRequestId = httpHeaders.firstValue("x-li-request-id").orElse("");
    String liUUID = httpHeaders.firstValue("x-li-uuid").orElse("");
    debugHeaderInfo = new DebugHeaderInfo(liFabric, liFormat, liRequestId, liUUID);
  }
  
  
  private Response fetchResponse(int statusCode, java.net.http.HttpHeaders headers, String body) {
    Map<String, String> headerMap = headers.map().entrySet().stream().collect(Collectors
        .toMap(Map.Entry::getKey, entry -> entry.getValue().stream().findFirst().orElse("")));
    return new Response(statusCode, headerMap, body);
  }

  /**
   * Fetch response
   *
   * @param httpUrlConnection the http url connection
   * @return the response
   * @throws IOException the io exception
   */
  protected Response fetchResponse(HttpResponse httpUrlConnection) throws IOException {
    return fetchResponse(httpUrlConnection.getStatusCode(), httpUrlConnection.getHeaders(),
        fromInputStream(httpUrlConnection
        .getContent()));
  }

  /**
   * Fetch response
   *
   * @param statusCode the status code of the response
   * @param headers    the HTTP headers
   * @param body       the response body
   * @return the response
   */
  protected Response fetchResponse(int statusCode, HttpHeaders headers, String body) {
    Map<String, String> headerMap = headers.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, headerValueMapper()));
    return new Response(statusCode, headerMap, body);
  }
  
  protected Function<Map.Entry<String, Object>, String> headerValueMapper() {
    return entry -> {
      if (!(entry.getValue() instanceof List)) {
        return entry.getValue().toString();
      }
      
      List<Object> value = (List<Object>) entry.getValue();
      if (value.isEmpty()) {
        return "";
      }
  
      if (value.size() == 1) {
        return value.get(0).toString();
      }
  
      return value.toString();
    };
  }

  /**
   * Builds and returns a string representation of the given {@code inputStream} .
   *
   * @param inputStream The stream from which a string representation is built.
   *
   * @return A string representation of the given {@code inputStream}.
   * @throws IOException If an error occurs while processing the {@code inputStream}.
   */
  public static String fromInputStream(InputStream inputStream) throws IOException {
    if (inputStream == null) {
      return null;
    }

    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING_CHARSET));
      StringBuilder response = new StringBuilder();

      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }

      return response.toString();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception t) {
          // Really nothing we can do but log the error
          LOGGER.warn("Unable to close stream, continuing on: ", t);
        }
      }
    }
  }
  
  private GenericUrl getGenericURL(String url, String parameters) {
    return new GenericUrl(url + (!StringUtils.isEmpty(parameters)
        ? "?" + parameters : ""));
  }
  
  /**
   * Convert the JSON into a map - annoyingly JsonHttpContent data object has to be a
   * key/value object i.e. map
   * @param jsonBody JSON body
   * @return JsonHttpContent
   */
  private JsonHttpContent getJsonHttpContent(String jsonBody) {
    JsonObject asObject = Json.parse(jsonBody).asObject();
    Map<String, Object> map = JsonUtils.toMap(asObject);
  
    return new JsonHttpContent(new GsonFactory(), map);
  }
  
  private void addHeadersToRequest(HttpRequest request, HttpHeaders httpHeaders,
      Map<String, String> headers) {
    if (headers != null) {
      // Add any additional headers
      headers.entrySet().stream()
          .filter(headerEntry -> {
            String lowerCaseHeaderName = headerEntry.getKey().toLowerCase();
            return !httpHeaders.containsKey(lowerCaseHeaderName);
          }).forEach(headerEntry -> httpHeaders.put(headerEntry.getKey(), headerEntry.getValue()));
    }
  
    request.setHeaders(httpHeaders);
  }

}
