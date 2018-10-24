package com.echobox.api.linkedin.types;

import lombok.Getter;

/**
 * Linked in types that contain an id field
 * @author Joanna
 *
 */
public abstract class LinkedInIdType {
  
  /**
   * Unique internal numeric identifier
   */
  @Getter
  private long id;

}
