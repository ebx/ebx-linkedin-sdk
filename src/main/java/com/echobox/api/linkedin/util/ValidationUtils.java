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

package com.echobox.api.linkedin.util;

import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.exception.LinkedInOAuthException;
import com.echobox.api.linkedin.exception.LinkedInResponseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.util.Map;

/**
 * Valiation utility class
 * @author Joanna
 *
 */
public class ValidationUtils {
  
  /**
   * Video file byte arrays are chunked by specifying the start and end bytes of type {@code int}.
   * To prevent an ArithmeticException, file sizes are limited to the integer's max value, which
   * allows for file sizes of ~2GB.
   */
  private static final int MAX_VIDEO_FILE_SIZE_LIMIT = Integer.MAX_VALUE;

  /**
   * Ensures that {@code parameter} isn't {@code null} or an empty string.
   * 
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws IllegalArgumentException
   *           If {@code parameter} is {@code null} or an empty string.
   */
  public static void verifyParameterPresence(String parameterName, String parameter) {
    verifyParameterPresence(parameterName, (Object) parameter);
    if (StringUtils.isBlank(parameter)) {
      throw new IllegalArgumentException("The '" + parameterName
          + "' parameter cannot be an empty string.");
    }
  }

  /**
   * Ensures that {@code parameter} isn't {@code null}.
   * 
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws NullPointerException
   *           If {@code parameter} is {@code null}.
   */
  public static void verifyParameterPresence(String parameterName, Object parameter) {
    if (parameter == null) {
      throw new NullPointerException("The '" + parameterName + "' parameter cannot be null.");
    }
  }
  
  /**
   * Validate response.
   *
   * @param response the response from the API call
   */
  public static void validateResponse(WebRequestor.Response response) {
    // If there was no response error information and this was a 401
    // error, something weird happened on LinkedIn's end. Assume it is a Oauth error.
    if (HttpStatus.SC_UNAUTHORIZED == response.getStatusCode()) {
      throw new LinkedInOAuthException("LinkedIn request failed", response.getStatusCode());
    }
  
    // If there was no response error information and this was a 500
    // error, something weird happened on LinkedIn's end. Bail.
    if (HttpStatus.SC_INTERNAL_SERVER_ERROR == response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
  
    if (HttpStatus.SC_OK != response.getStatusCode()
        && HttpStatus.SC_CREATED != response.getStatusCode()) {
      throw new LinkedInNetworkException("LinkedIn request failed", response.getStatusCode());
    }
  }
  
  /**
   * Validate that the response contains the required header.
   *
   * @param headers map of headers from the response
   * @param header the header to check exists
   */
  public static void validateRequiredResponseHeader(Map<String, String> headers, String header) {
    if (headers == null || headers.isEmpty()) {
      throw new LinkedInResponseException("No headers were found on the response.");
    }
  
    String headerResponse = headers.get(header);
    if (headerResponse == null) {
      throw new LinkedInResponseException(String.format("The header [%s] is missing from the "
          + "response.", header));
    }
  }
  
  /**
   * Validate video files
   *
   * @param fileSizeBytes size of the video file in bytes
   */
  public static void validateVideoFileSize(Long fileSizeBytes) {
    if (fileSizeBytes == null) {
      throw new IllegalArgumentException("fileSizeBytes is a required field.");
    }

    if (fileSizeBytes > MAX_VIDEO_FILE_SIZE_LIMIT) {
      throw new IllegalArgumentException(String.format("The maximum video file size is %s bytes.",
          MAX_VIDEO_FILE_SIZE_LIMIT));
    }
  }
}
