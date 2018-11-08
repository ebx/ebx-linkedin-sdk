package com.echobox.api.linkedin.exception;

import static java.lang.String.format;

/**
 * Indicates that a network error occurred while trying to connect to the Facebook API endpoint.
 * <p>
 * Examples: No network adapter available, API endpoint is down.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class LinkedInNetworkException extends LinkedInException {
  
  /**
   * The HTTP response status code.
   */
  private Integer httpStatusCode;

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Creates an exception with the given message and cause.
   * 
   * @param message
   *          A message describing this exception.
   * @param cause
   *          The exception that caused this exception to be thrown.
   */
  public LinkedInNetworkException(String message, Throwable cause) {
    this(message, cause, null);
  }

  /**
   * Creates an exception with the given message and HTTP status code.
   * 
   * @param message
   *          A message describing this exception.
   * @param httpStatusCode
   *          The HTTP response status code.
   */
  public LinkedInNetworkException(String message, Integer httpStatusCode) {
    this(message, null, httpStatusCode);
  }

  /**
   * Creates an exception with the given message, cause, and HTTP status code.
   * 
   * @param message
   *          A message describing this exception.
   * @param cause
   *          The exception that caused this exception to be thrown.
   * @param httpStatusCode
   *          The HTTP response status code.
   */
  public LinkedInNetworkException(String message, Throwable cause, Integer httpStatusCode) {
    super(format("A network error occurred while trying to communicate with Facebook: %s (HTTP status code %d)",
      message, httpStatusCode), cause);
    this.httpStatusCode = httpStatusCode;
  }

  /**
   * Gets the HTTP response status code.
   * 
   * @return The HTTP response status code.
   */
  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

}
