package com.echobox.api.linkedin.exception;

import org.json.JSONObject;

public class LinkedInOAuthException extends LinkedInAPIException {
  
  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates an exception with the given error type and message.
   * 
   * @param errorType
   *          Value of the Facebook response attribute {@code error.type}.
   * @param errorMessage
   *          Value of the Facebook response attribute {@code error.message}.
   * @param errorCode
   *          Value of the Facebook response attribute {@code error.code}.
   * @param errorSubcode
   *          Value of the Facebook response attribute {@code error.error_subcode}.
   * @param httpStatusCode
   *          The HTTP status code returned by the server, e.g. 500.
   * @param userTitle
   *          Value of the Facebook response attribute {@code error.error_user_title}.
   * @param userMessage
   *          Value of the Facebook response attribute {@code error.error_user_message}.
   */
  public LinkedInOAuthException(String errorMessage, Integer errorCode,
      Integer httpStatusCode, Boolean isTransient, JSONObject rawError) {
    super(errorMessage, errorCode, httpStatusCode, isTransient,
      rawError);
  }

}
