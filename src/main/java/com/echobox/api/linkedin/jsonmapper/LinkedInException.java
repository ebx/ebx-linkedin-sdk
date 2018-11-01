package com.echobox.api.linkedin.jsonmapper;

public class LinkedInException extends RuntimeException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  public LinkedInException(String message) {
    super(message);
  }
  
  public LinkedInException(String message, Throwable cause) {
    super(message, cause);
  }

}
