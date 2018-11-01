package com.echobox.api.linkedin.jsonmapper;

public class LinkedInJsonMappingException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  public LinkedInJsonMappingException(String message) {
    super(message);
  }
  
  public LinkedInJsonMappingException(String message, Throwable cause) {
    super(message, cause);
  }

}
