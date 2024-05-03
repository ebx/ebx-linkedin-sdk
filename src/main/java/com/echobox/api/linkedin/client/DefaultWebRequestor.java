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

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
  
  private Map<String, Object> currentHeaders;
  
  private java.net.http.HttpHeaders currentHttpHeaders;
  
  private DebugHeaderInfo debugHeaderInfo;
  
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
    POST,
    /**
     * Put http method.
     */
    PUT
  }
  
  /**
   * Initialise the default web requestor with no authentication
   */
  public DefaultWebRequestor() {
    this(null);
  }
  
  /**
   * Initialise the default web requestor which uses OAuth2.
   *
   * @param accessToken the access token
   */
  public DefaultWebRequestor(String accessToken) {
    this(accessToken, DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_READ_TIMEOUT_MS);
  }
  
  /**
   * Initialise the default web requestor which uses OAuth2 and HTTP timeouts
   *
   * @param accessToken An OAuth access token.
   * @param connectTimeout the connect timeout
   * @param readTimeout the read timeout
   */
  public DefaultWebRequestor(String accessToken, int connectTimeout, int readTimeout) {
    this.headers =
        accessToken != null ? Arrays.asList("Access-Token", accessToken) : Collections.emptyList();
    this.httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofMillis(connectTimeout)).build();
    this.readTimeout = readTimeout;
  }
  
  @Override
  public Response executeGet(String url) throws IOException {
    return executeGet(url);
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
      Map<String, String> headers, BinaryAttachment... binaryAttachments) throws IOException {
    return execute(url, parameters, builder -> {
      String body = StringUtils.isEmpty(jsonBody) ? "no payload" : format("payload: %s", jsonBody);
      if (binaryAttachments != null && binaryAttachments.length > 0) {
        buildRequestBodyWithBinaryAttachments(url, builder, binaryAttachments);
      } else {
        buildJsonRequestBody(jsonBody, builder);
      }
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Executing a POST to {} with {} and headers: {}.", url, body,
            builder.headers());
      }
    }, headers);
  }
  
  private void buildJsonRequestBody(String jsonBody,
      java.net.http.HttpRequest.Builder builder) {
    if (jsonBody != null) {
      builder.POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonBody))
          .header("Content-Type", "application/json");
    }
    builder.POST(java.net.http.HttpRequest.BodyPublishers.noBody());
  }
  
  private void buildRequestBodyWithBinaryAttachments(String url,
      java.net.http.HttpRequest.Builder builder, BinaryAttachment... binaryAttachments) {
    HTTPRequestMultipartBody.Builder multipartBodyBuilder = new HTTPRequestMultipartBody.Builder();
    for (BinaryAttachment binaryAttachment : binaryAttachments) {
      multipartBodyBuilder.addPart(createFormFieldName(binaryAttachment),
          binaryAttachment.getData(), binaryAttachment.getContentType(),
          binaryAttachment.getFilename());
    }
    try {
      HTTPRequestMultipartBody multipartBody = multipartBodyBuilder.build();
      builder.POST(java.net.http.HttpRequest.BodyPublishers.ofByteArray(multipartBody.getBody()));
      builder.header("Connection", "Keep-Alive");
    } catch (IOException e) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Encountered error when executing POST to {} with binary attachments and "
            + "headers: {}.", url, builder.headers());
      }
    }
  }
  
  @Override
  public Response executePut(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment binaryAttachment)
      throws IOException {
    return execute(url, parameters, builder -> {
      String body = StringUtils.isEmpty(jsonBody) ? "no payload" : format("payload: %s", jsonBody);
      if (binaryAttachment != null) {
        buildRequestBodyWithBinaryAttachments(url, builder, binaryAttachment);
      } else {
        buildJsonRequestBody(jsonBody, builder);
      }
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace("Executing a POST to {} with {} and headers: {}.", url, body,
            builder.headers());
      }
    }, headers);
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
   * access to the current response headers
   * 
   * @return the current reponse header map
   */
  public Map<String, Object> getCurrentHeaders() {
    return currentHeaders;
  }
  
  /**
   * access to the current response headers
   *
   * @return the current reponse header map
   */
  public java.net.http.HttpHeaders getCurrentHttpHeaders() {
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
    currentHttpHeaders = httpHeaders;
  
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
}
