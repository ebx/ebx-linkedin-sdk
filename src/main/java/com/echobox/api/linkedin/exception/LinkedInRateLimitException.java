package com.echobox.api.linkedin.exception;

import org.json.JSONObject;

public class LinkedInRateLimitException extends LinkedInAPIException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  public LinkedInRateLimitException(String errorMessage, Integer errorCode, Integer httpStatusCode,
      Boolean isTransient, JSONObject rawError) {
    super(errorMessage, errorCode, httpStatusCode, isTransient, rawError);
  }

}
