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
 * Abstract class to provide access to the JSON LinkedIn provides in case of an error
 *
 * @author Joanna
 */
public abstract class LinkedInErrorMessageException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  private JsonObject rawErrorJson;

  /**
   * Initialise the LinkedIn error message exception
   * @param message message
   */
  public LinkedInErrorMessageException(String message) {
    super(message);
  }

  /**
   * Initialise the LinkedIn error message exception
   * @param message The message
   * @param cause The cause
   */
  public LinkedInErrorMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Return the raw error as JSON, may be <code>null</code>
   * 
   * @return raw error
   */
  public JsonObject getRawErrorJson() {
    return rawErrorJson;
  }

  /**
   * Sets the raw error as JSON.
   *
   * @param rawError the raw error
   */
  protected void setRawErrorJson(JsonObject rawError) {
    rawErrorJson = rawError;
  }
}
