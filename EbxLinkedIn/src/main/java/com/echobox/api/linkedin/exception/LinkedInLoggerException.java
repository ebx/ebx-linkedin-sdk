package com.echobox.api.linkedin.exception;

import com.echobox.api.linkedin.jsonmapper.LinkedInException;

public class LinkedInLoggerException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  public LinkedInLoggerException(String message) {
    super(message);
  }

  public LinkedInLoggerException(String message, Throwable cause) {
    super(message, cause);
  }
}