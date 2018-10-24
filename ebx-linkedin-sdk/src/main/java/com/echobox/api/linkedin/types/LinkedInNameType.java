package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Linked in types that contain a name field
 * @author Joanna
 *
 */
public abstract class LinkedInNameType extends LinkedInIdType {
  
  /**
   * The name of the type
   */
  @Getter
  @Setter
  private String name;

}
