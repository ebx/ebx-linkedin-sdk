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
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;

/**
 * Default Implementation of {@code LinkedInExceptionMapper} that maps LinkedIn API exceptions.
 * @author Kenneth Wong
 */
@NoArgsConstructor
public class DefaultLinkedInExceptionMapper implements LinkedInExceptionMapper {
  
  @Override
  public LinkedInException exceptionForTypeAndMessage(Integer errorCode, Integer httpStatusCode,
      String message, Boolean isTransient, JsonObject rawError) {
    // Bad Request - client mistakes
    if (Integer.valueOf(HttpStatus.SC_BAD_REQUEST).equals(httpStatusCode)) {
      return new LinkedInQueryParseException(message, errorCode, httpStatusCode, rawError);
    }
    
    // Unauthorised
    if (Integer.valueOf(HttpStatus.SC_UNAUTHORIZED).equals(httpStatusCode)) {
      return new LinkedInOAuthException(message, errorCode, httpStatusCode, rawError);
    }
    
    // Resource not found
    if (Integer.valueOf(HttpStatus.SC_NOT_FOUND).equals(httpStatusCode)) {
      return new LinkedInResourceNotFoundException(message, errorCode, httpStatusCode,
          rawError);
    }
    
    // 429 Rate limit
    if (Integer.valueOf(HttpStatus.SC_TOO_MANY_REQUESTS).equals(httpStatusCode)) {
      return new LinkedInRateLimitException(message, errorCode, httpStatusCode,
          rawError);
    }
    
    // Internal Server Error
    if (Integer.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR).equals(httpStatusCode)) {
      return new LinkedInInteralServerException(message, errorCode, httpStatusCode, rawError);
    }
    
    // Gateway timeout
    if (Integer.valueOf(HttpStatus.SC_GATEWAY_TIMEOUT).equals(httpStatusCode)) {
      return new LinkedInGatewayTimeoutException(message, errorCode, httpStatusCode, rawError);
    }
    
    // Don't recognize this exception type? Just go with the standard LinkedInAPIException.
    return new LinkedInAPIException(message, errorCode, httpStatusCode, rawError);
  }

}
