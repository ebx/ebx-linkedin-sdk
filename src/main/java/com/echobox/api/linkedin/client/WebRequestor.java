/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
//Source - https://restfb.com/

package com.echobox.api.linkedin.client;

import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Specifies how a class that sends {@code HTTP} requests to the LinkedIn API endpoint must operate.
 * 
 * @author Joanna
 */
public interface WebRequestor {
  /**
   * Encapsulates an HTTP response body and status code.
   * 
   * @author Joanna
   */
  class Response {
    /**
     * HTTP response status code (e.g. 200).
     */
    private Integer statusCode;

    /**
     * HTTP response body as text.
     */
    private String body;
    
    private Map<String, String> headers;

    /**
     * Creates a response with the given HTTP status code and response body as text.
     * 
     * @param statusCode
     *          The HTTP status code of the response.
     * @param headers
     *          The headers from the response
     * @param body
     *          The response body as text.
     */
    public Response(Integer statusCode, Map<String, String> headers, String body) {
      this.statusCode = statusCode;
      this.headers = headers;
      this.body = StringUtils.trimToEmpty(body);
    }

    /**
     * Gets the HTTP status code.
     * 
     * @return The HTTP status code.
     */
    public Integer getStatusCode() {
      return statusCode;
    }

    /**
     * Gets the HTTP response body as text.
     * 
     * @return The HTTP response body as text.
     */
    public String getBody() {
      return body;
    }
    
    public Map<String, String> getHeaders() {
      return headers;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      String responseBody = StringUtils.isBlank(getBody()) ? "an empty response body" :
          format("response body: %s", getBody());
      return format("HTTP status code %d and %s and headers %s.", getStatusCode(), responseBody,
          getHeaders());
    }
  }

  /**
   * Given a LinkedIn API endpoint URL, execute a {@code GET} against it.
   * 
   * @param url
   *          The URL to make a {@code GET} request for, including URL parameters.
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code GET} operation.
   */
  Response executeGet(String url) throws IOException;
  
  /**
   * Given a LinkedIn API endpoint URL, execute a {@code GET} against it.
   *
   * @param url
   *          The URL to make a {@code GET} request for, including URL parameters.
   * @param headers
   *          The headers for the GET request
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code GET} operation.
   */
  Response executeGet(String url, Map<String, String> headers) throws IOException;

  /**
   * Given a LinkedIn API endpoint URL and parameter string, execute a {@code POST} to the endpoint
   * URL.
   * 
   * @param url
   *          The URL to {@code POST} to.
   * @param parameters
   *          The parameters to be {@code POST}ed.
   * @param jsonBody
   *          The POST JSON body
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code POST}.
   */
  Response executePost(String url, String parameters, String jsonBody) throws IOException;

  /**
   * Given a LinkedIn API endpoint URL and parameter string, execute a {@code POST} to the endpoint
   * URL.
   * 
   * @param url
   *          The URL to {@code POST} to.
   * @param parameters
   *          The parameters to be {@code POST}ed.
   * @param jsonBody
   *          The POST JSON body
   * @param headers
   *          The headers for the POST request
   * @param binaryAttachments
   *          Optional binary attachments to be included in the {@code POST} body (e.g. photos and
   *          videos).
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code POST}.
   */
  Response executePost(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment... binaryAttachments)
      throws IOException;
  
  Response executePut(String url, String parameters, String jsonBody,
      Map<String, String> headers, BinaryAttachment binaryAttachments)
      throws IOException;

  /**
   * Given a LinkedIn API endpoint URL and parameter string, execute a {@code DELETE} to the
   * endpoint URL.
   * 
   * @param url
   *          The URL to submit the {@code DELETE} to.
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code DELETE}.
   */
  Response executeDelete(String url) throws IOException;

  /**
   * Given a LinkedIn API endpoint URL and parameter string, execute a {@code DELETE} to the
   * endpoint URL.
   *
   * @param url
   *          The URL to submit the {@code DELETE} to.
   * @param headers
   *          The headers for the DELETE request
   * @return HTTP response data.
   * @throws IOException
   *           If an error occurs while performing the {@code DELETE}.
   */
  Response executeDelete(String url, Map<String, String> headers) throws IOException;
  
  /**
   * Provides access to the LinkedIn header information.
   * 
   * @return container with the explained LinkedIn debug header information
   */
  DebugHeaderInfo getDebugHeaderInfo();
}
