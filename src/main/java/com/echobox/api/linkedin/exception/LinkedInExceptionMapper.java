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

import org.json.JSONObject;

/**
 * Specifies a method for mapping LinkedIn API exceptions to corresponding instances of
 * {@code LinkedInException}.
 * @author Joanna
 *
 */
public interface LinkedInExceptionMapper {
  /**
   * Given a LinkedIn API exception type and message, generates an instance of the corresponding
   * {@code LinkedInGraphException} or one of its subclasses.
   * 
   * @param errorCode
   *          LinkedIn API exception error code field, e.g. 190.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param message
   *          LinkedIn API message field, e.g. "Invalid access token signature."
   * @param isTransient
   *          Whether the error is transient
   * @param rawError
   *          raw error message as JSON
   * @return An appropriate {@code LinkedInException} subclass.
   */
  LinkedInException exceptionForTypeAndMessage(Integer errorCode, Integer httpStatusCode,
      String message, Boolean isTransient, JSONObject rawError);
}
