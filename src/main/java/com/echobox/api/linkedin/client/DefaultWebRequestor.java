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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
  
  private Map<String, List<String>> currentHeaders;
  private DebugHeaderInfo debugHeaderInfo;
  
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
    this.headers = accessToken != null ? Arrays.asList("Authorization",
        String.format("Bearer %s", accessToken)) : Collections.emptyList();
    this.httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofMillis(connectTimeout)).build();
    this.readTimeout = readTimeout;
  }
  
  @Override
  public Response executeGet(String url) throws IOException {
    return executeGet(url, null);
  }
  
  @Override
  public Response executeGet(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, HttpRequest.Builder::GET, headers);
  }
  
  @Override
  public Response executePost(String url, String parameters, String jsonBody) throws IOException {
    return executePost(url, parameters, jsonBody, null);
  }
  
  @Override
  public Response executePost(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment... binaryAttachments) throws IOException {
    return execute(url, parameters,
        builder -> buildRequestWithBody(builder, builder::POST, url, jsonBody, headers,
            binaryAttachments), headers);
  }
  
  private void buildRequestWithBody(HttpRequest.Builder builder,
      Consumer<HttpRequest.BodyPublisher> bodyConsumer, String url, String jsonBody,
      Map<String, String> headers, BinaryAttachment... binaryAttachments) {
    if (binaryAttachments != null && binaryAttachments.length > 0) {
      bodyConsumer.accept(
          buildRequestBodyWithBinaryAttachments(url, builder, headers, binaryAttachments));
    } else {
      bodyConsumer.accept(buildJsonRequestBody(jsonBody, builder));
    }
  }
  
  private HttpRequest.BodyPublisher buildJsonRequestBody(String jsonBody,
      HttpRequest.Builder builder) {
    if (jsonBody != null) {
      builder.header("Content-Type", "application/json");
      return HttpRequest.BodyPublishers.ofString(jsonBody);
    }
    return HttpRequest.BodyPublishers.noBody();
  }
  
  private HttpRequest.BodyPublisher buildRequestBodyWithBinaryAttachments(String url,
      HttpRequest.Builder builder,
      Map<String, String> headers, BinaryAttachment... binaryAttachments) {
    HTTPRequestMultipartBody.Builder multipartBodyBuilder = new HTTPRequestMultipartBody.Builder();
    for (BinaryAttachment binaryAttachment : binaryAttachments) {
      String contentType =
          binaryAttachment.getContentType() != null ? binaryAttachment.getContentType()
              : headers.entrySet().stream()
                  .filter(entry -> entry.getKey().equalsIgnoreCase("content-type"))
                  .map(Map.Entry::getValue).findFirst().orElse("application/octet-stream");
      multipartBodyBuilder.addPart(createFormFieldName(binaryAttachment),
          binaryAttachment.getData(), contentType, binaryAttachment.getFilename());
    }
    try {
      HTTPRequestMultipartBody multipartBody = multipartBodyBuilder.build();
      return HttpRequest.BodyPublishers.ofByteArray(multipartBody.getBody());
    } catch (IOException e) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Encountered error when executing POST/PUT to {} with binary attachments and "
            + "headers: {}.", url, builder.headers());
      }
    } finally {
      for (BinaryAttachment binaryAttachment : binaryAttachments) {
        closeQuietly(binaryAttachment.getDataInputStream());
      }
    }
    return HttpRequest.BodyPublishers.noBody();
  }
  
  @Override
  public Response executePut(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment binaryAttachment) throws IOException {
    return execute(url, parameters,
        builder -> buildRequestWithBody(builder, builder::PUT, url, jsonBody, headers,
            binaryAttachment), headers);
  }
  
  private Response getResponse(HttpRequest request) throws IOException, InterruptedException {
    HttpResponse<String> httpResponse =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
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
   * Access to the current response headers
   *
   * @return the current response headers
   */
  public Map<String, List<String>> getCurrentHeaders() {
    return currentHeaders;
  }
  
  @Override
  public Response executeDelete(String url) throws IOException {
    return executeDelete(url, null);
  }
  
  @Override
  public Response executeDelete(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, HttpRequest.Builder::DELETE, headers);
  }
  
  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return debugHeaderInfo;
  }
  
  private Response execute(String url, String parameters,
      Consumer<HttpRequest.Builder> requestBuilder, Map<String, String> customHeaders)
      throws IOException {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Executing request {} with parameters: {} and headers: {} {}", url, parameters,
          headers, customHeaders);
    }
    
    try {
      URI uri = getURI(url, parameters);
      
      HttpRequest.Builder builder =
          HttpRequest.newBuilder(uri).timeout(Duration.ofMillis(readTimeout));
      
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
  private void fillHeaderAndDebugInfo(java.net.http.HttpHeaders httpHeaders) {
    currentHeaders = httpHeaders.map();
    
    String liFabric = httpHeaders.firstValue("x-li-fabric").orElse("");
    String liFormat = httpHeaders.firstValue("x-li-format").orElse("");
    String liRequestId = httpHeaders.firstValue("x-li-request-id").orElse("");
    String liUUID = httpHeaders.firstValue("x-li-uuid").orElse("");
    debugHeaderInfo = new DebugHeaderInfo(liFabric, liFormat, liRequestId, liUUID);
  }
  
  private Response fetchResponse(int statusCode, HttpHeaders headers, String body) {
    Map<String, String> headerMap = headers.map().entrySet().stream().collect(
        Collectors.toMap(Map.Entry::getKey,
            entry -> entry.getValue().stream().findFirst().orElse("")));
    return new Response(statusCode, headerMap, body);
  }
}
