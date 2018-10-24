package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Contact info model
 * @author Joanna
 *
 */
public class ContactInfo {
  
  /**
   * Company phone number for the location.
   */
  @Getter
  @Setter
  private String phoneOne;

  /**
   * Second company phone number for the location.
   */
  @Getter
  @Setter
  private String phoneTwo;

  /**
   * Company fax number for the location.
   */
  @Getter
  @Setter
  private String fax;
}
