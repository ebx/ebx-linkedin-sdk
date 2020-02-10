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

import com.echobox.api.linkedin.logging.LinkedInLogger;
import com.echobox.api.linkedin.util.JsonUtils;
import com.echobox.api.linkedin.util.URLUtils;
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
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of a service that sends HTTP requests to the LinkedIn API endpoint.
 * 
 * @author Joanna
 *
 */
public class DefaultWebRequestor implements WebRequestor {

  private static final Logger LOGGER = LinkedInLogger.getLoggerInstance();

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
  
  private static final String CONTENT_TYPE = "application/json";
  
  private static final String FORMAT_HEADER = "x-li-format";

  /**
   * By default, how long should we wait for a response (in ms)?
   */
  private static final int DEFAULT_READ_TIMEOUT_IN_MS = 180000;

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private Map<String, Object> currentHeaders;

  private DebugHeaderInfo debugHeaderInfo;

  private HttpRequestFactory requestFactory;

  /**
   * By default this is true, to prevent breaking existing usage
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
    return execute(url, HttpMethod.GET);
  }

  @Override
  public Response executePost(String url, String parameters, String jsonBody) throws IOException {
    return executePost(url, parameters, jsonBody, null, new BinaryAttachment[0]);
  }

  @Override
  public Response executePost(String url, String parameters, String jsonBody,
      Map<String, String> headers,
      BinaryAttachment... binaryAttachments)
      throws IOException {
    
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Executing a POST to " + url + " with parameters "
          + (binaryAttachments.length > 0 ? "" : "(sent in request body): ")
          + URLUtils.urlDecode(parameters)
          + (binaryAttachments.length > 0 ? " and " + binaryAttachments.length
              + " binary attachment[s]." : ""));
    }

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

          // Ensure the headers are set to JSON
          httpHeaders.setContentType(CONTENT_TYPE).set(FORMAT_HEADER, "json");

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
  
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Executing a PUT to " + url + " with parameters "
          + (binaryAttachment != null ? "" : "(sent in request body): ")
          + URLUtils.urlDecode(parameters)
          + (binaryAttachment != null ? " and " + binaryAttachment
          + " binary attachment." : ""));
    }
  
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
        
          // Ensure the headers are set to JSON
          httpHeaders.setContentType(CONTENT_TYPE).set(FORMAT_HEADER, "json");
        
          // Ensure the response headers are also set to JSON
          request.setResponseHeaders(new HttpHeaders().set(FORMAT_HEADER, "json"));
        } else {
          // Plain old POST request
          request = requestFactory.buildPutRequest(genericUrl, null);
        }
      }
    
      request.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);
    
      // Allow subclasses to customize the connection if they'd like to - set their own headers,
      // timeouts, etc.
      customizeConnection(request);
  
      addHeadersToRequest(request, httpHeaders, headers);
    
      return getResponse(request);
    } catch (HttpResponseException ex) {
      return handleException(ex);
    } finally {
      if (autocloseBinaryAttachmentStream && binaryAttachment != null) {
        closeQuietly(binaryAttachment.getDataInputStream());
      }
    }
  }
  
  private Response getResponse(HttpRequest request) throws IOException {
    HttpResponse httpResponse = request.execute();

    fillHeaderAndDebugInfo(httpResponse.getHeaders());

    Response response = fetchResponse(httpResponse);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(format("LinkedIn responded with %s", response));
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

  @Override
  public Response executeDelete(String url) throws IOException {
    return execute(url, HttpMethod.DELETE);
  }

  @Override
  public DebugHeaderInfo getDebugHeaderInfo() {
    return debugHeaderInfo;
  }

  private Response execute(String url, HttpMethod httpMethod) throws IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(format("Making a %s request to %s", httpMethod.name(), url));
    }

    HttpResponse httpResponse = null;
    try {
      GenericUrl genericUrl = new GenericUrl(url);
      HttpRequest request = requestFactory.buildRequest(httpMethod.name(), genericUrl, null);
      request.setReadTimeout(DEFAULT_READ_TIMEOUT_IN_MS);

      // Allow subclasses to customize the connection if they'd like to - set their own headers,
      // timeouts, etc.
      customizeConnection(request);

      return getResponse(request);
    } catch (HttpResponseException ex) {
      return handleException(ex);
    } finally {
      closeQuietly(httpResponse);
    }
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
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString(),
            (key, value) -> value));
    return new Response(statusCode, headerMap, body);
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
  
    return new JsonHttpContent(new JacksonFactory(), map);
  }
  
  private void addHeadersToRequest(HttpRequest request, HttpHeaders httpHeaders,
      Map<String, String> headers) {
    if (headers != null) {
      // Add any additional headers
      for (String headerKey : headers.keySet()) {
        if (!headerKey.equalsIgnoreCase("content-type")) {
          httpHeaders.put(headerKey, headers.get(headerKey));
        }
      }
    }
  
    request.setHeaders(httpHeaders);
  }

}
