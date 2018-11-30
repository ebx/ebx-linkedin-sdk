package com.echobox.api.linkedin.types;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

/**
 * Error model
 * @author Alexandros
 */
public class Error {
  
  @Getter
  @Setter
  @LinkedIn
  private int status;
  
  @Getter
  @Setter
  @LinkedIn
  private int serviceErrorCode;
  
  @Getter
  @Setter
  @LinkedIn
  private String message;
}
