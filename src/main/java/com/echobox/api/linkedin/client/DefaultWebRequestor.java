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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of a service that sends HTTP requests to an API endpoint.
 * @author paulp
 *
 */
public class DefaultWebRequestor implements WebRequestor {
  
  private static final Logger logger = LoggerFactory.getLogger(DefaultWebRequestor.class);
  private static final int DEFAULT_CONNECT_TIMEOUT_MS = 10000;
  private static final int DEFAULT_READ_TIMEOUT_MS = 30000;
  
  private final List<String> headers;
  private final HttpClient httpClient;
  private final int readTimeout;
  
  /**
   * HTTP methods available
   * @author paulp
   *
   */
  public enum HttpMethod {
    /**
     * Get http method.
     */
    GET,
    /**
     * Post http method.
     */
    POST
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
    return execute(url, null, HttpRequest.Builder::GET, null);
  }
  
  @Override
  public Response executeGet(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, HttpRequest.Builder::GET, headers);
  }
  
  @Override
  public Response executePost(String url, String jsonBody, String parameters) throws IOException {
    return execute(url, parameters, builder -> {
      if (jsonBody != null) {
        builder.POST(BodyPublishers.ofString(jsonBody)).header("Content-Type", "application/json");
      } else {
        builder.POST(BodyPublishers.noBody());
      }
    }, null);
  }
  
  @Override
  public Response executePost(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment... binaryAttachments) throws IOException {
    return execute(url, parameters, builder -> {
      if (jsonBody != null) {
        builder.POST(BodyPublishers.ofString(jsonBody)).header("Content-Type", "application/json");
      } else {
        builder.POST(BodyPublishers.noBody());
      }
      // Add binary attachments
    }, headers);
  }
  
  @Override
  public Response executePut(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment binaryAttachments) throws IOException {
    return execute(url, parameters, builder -> {
      if (jsonBody != null) {
        builder.PUT(BodyPublishers.ofString(jsonBody)).header("Content-Type", "application/json");
      } else {
        builder.PUT(BodyPublishers.noBody());
      }
      // Add binary attachments
    }, headers);
  }
  
  @Override
  public Response executeDelete(String url) throws IOException {
    return execute(url, null, HttpRequest.Builder::DELETE, null);
  }
  
  @Override
  public Response executeDelete(String url, Map<String, String> headers) throws IOException {
    return execute(url, null, HttpRequest.Builder::DELETE, headers);
  }
  
  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return null;
  }
  
  private Response execute(String url, String parameters,
      Consumer<HttpRequest.Builder> requestBuilder, Map<String, String> customHeaders)
      throws IOException {
    logger.debug("Executing request {} with parameters: {}", url, parameters);
    
    try {
      URI uri = getURI(url, parameters);
      
      HttpRequest.Builder builder =
          HttpRequest.newBuilder(uri).timeout(Duration.ofMillis(readTimeout));
  
      List<String> allHeaders = new ArrayList<>();
      if (customHeaders != null && !customHeaders.isEmpty()) {
        allHeaders = customHeaders.entrySet().stream()
            .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
      }
      if (!headers.isEmpty()) {
        allHeaders.addAll(headers);
      }
      if (!allHeaders.isEmpty()) {
        builder.headers(allHeaders.toArray(new String[0]));
      }
      
      requestBuilder.accept(builder);
      
      return getResponse(builder.build());
    } catch (URISyntaxException | InterruptedException ex) {
      throw new IOException(ex);
    }
  }
  
  private Response getResponse(HttpRequest request) throws IOException, InterruptedException {
    HttpResponse<String> httpResponse =
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
    Response response =
        fetchResponse(httpResponse.statusCode(), httpResponse.headers(), httpResponse.body());
    
    logger.debug("API {} responded with {}", request.uri(), response);
    
    return response;
  }
  
  private Response fetchResponse(int statusCode, HttpHeaders headers, String body) {
    Map<String, String> headerMap = headers.map().entrySet().stream().collect(Collectors
        .toMap(Map.Entry::getKey, entry -> entry.getValue().stream().findFirst().orElse("")));
    return new Response(statusCode, headerMap, body);
  }
  
  private URI getURI(String url, String parameters) throws URISyntaxException {
    String parametersToAppend = "";
    if (StringUtils.isNotEmpty(parameters)) {
      parametersToAppend = parameters.startsWith("?") ? parameters : "?" + parameters;
    }
    return new URI(url + parametersToAppend);
  }
  
}
