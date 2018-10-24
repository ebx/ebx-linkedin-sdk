package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Location model
 * @author Joanna
 *
 */
public class Location {
  
  /**
   * Description of company location.
   */
  @Getter
  @Setter
  private String description;
  
  /**
   * Valid values are true or false. A value of true matches the Company headquarters location.
   */
  @Getter
  @Setter
  private boolean isHeadQuarters;
  
  /**
   * Valid values are true or false. A value of true matches the active location.
   */
  @Getter
  @Setter
  private String isActive;
  
  /**
   * Address of location.
   */
  @Getter
  @Setter
  private Address address;
  
  /**
   * Company contact information for the location.
   */
  @Getter
  @Setter
  private ContactInfo contactInfo;
}
