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

package com.echobox.api.linkedin.exception;

import com.eclipsesource.json.JsonObject;

/**
 * Indicates that the LinkedIn API endpoint returned JSON which indicates an error condition.
 * 
 * @author Joanna
 */
public class LinkedInAPIException extends LinkedInErrorMessageException {
  
  private static final long serialVersionUID = 1L;

  /**
   * The LinkedIn API error message.
   */
  private final String errorMessage;

  /**
   * The LinkedIn API error code.
   */
  private final Integer errorCode;

  /**
   * The HTTP status code returned by the server.
   */
  private final Integer httpStatusCode;

  /**
   * Creates an exception with the given error type and message.
   * 
   * @param errorMessage
   *          Value of the LinkedIn response attribute {@code error.message}.
   * @param errorCode
   *          Value of the LinkedIn response attribute {@code error.code}.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param rawError
   *          The raw error JSON object
   */
  public LinkedInAPIException(String errorMessage, Integer errorCode, Integer httpStatusCode,
      JsonObject rawError) {
    super(String.format("Received LinkedIn error response: %s (code %s)", errorMessage,
        errorCode));
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
    this.httpStatusCode = httpStatusCode;
    setRawErrorJson(rawError);
  }

  /**
   * Gets the LinkedIn API error message.
   * 
   * @return The LinkedIn API error message.
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Gets the LinkedIn API error code.
   * 
   * @return The LinkedIn API error code.
   */
  public Integer getErrorCode() {
    return errorCode;
  }

  /**
   * Gets the HTTP status code returned by the server.
   * 
   * @return The HTTP status code returned by the server.
   */
  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  /**
   * Gets the LinkedIn API error {@code requestId}.
   *
   * Internal support identifier. When reporting a bug related to a Graph API call, include the
   * requestId to help us find log data for debugging.
   *
   * @return the LinkedIn API error {@code requestId}
   */
  public String getLinkedInRequestId() {
    return getRawErrorJson() == null ? null : getRawErrorJson().getString("requestId", "");
  }

}
