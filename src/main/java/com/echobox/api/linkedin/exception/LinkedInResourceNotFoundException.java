package com.echobox.api.linkedin.exception;

import org.json.JSONObject;

public class LinkedInResourceNotFoundException extends LinkedInAPIException {

  /**
   * Default Serial version UID
   */
  private static final long serialVersionUID = 1L;

  public LinkedInResourceNotFoundException(String errorMessage, Integer errorCode,
      Integer httpStatusCode, Boolean isTransient, JSONObject rawError) {
    super(errorMessage, errorCode, httpStatusCode, isTransient, rawError);
  }

}
