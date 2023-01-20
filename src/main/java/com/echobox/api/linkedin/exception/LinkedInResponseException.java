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

/**
 * LinkedIn response exception
 * @author Joanna
 *
 */
public class LinkedInResponseException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialise a LinkedInResponseException with a message
   * @param message message
   */
  public LinkedInResponseException(String message) {
    super(message);
  }

  /**
   * Initialise a LinkedInResponseException with a message and a cause
   * @param message message
   * @param cause cause
   */
  public LinkedInResponseException(String message, Throwable cause) {
    super(message, cause);
  }
}
