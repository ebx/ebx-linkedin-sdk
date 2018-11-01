package com.echobox.api.linkedin.exception;

import com.echobox.api.linkedin.jsonmapper.LinkedInException;

/**
 * LinkedIn logger exception
 * @author Joanna
 *
 */
public class LinkedInLoggerException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialise a LinkedInLoggerException with a message
   * @param message message
   */
  public LinkedInLoggerException(String message) {
    super(message);
  }

  /**
   * Initialise a LinkedInLoggerException with a message and a cause
   * @param message message
   * @param cause cause
   */
  public LinkedInLoggerException(String message, Throwable cause) {
    super(message, cause);
  }
}
