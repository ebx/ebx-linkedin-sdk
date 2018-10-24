package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

public class Location {
  @Getter
  @Setter
  private String description;
  
  @Getter
  @Setter
  private boolean isHeadQuarters;
  
  @Getter
  @Setter
  private String isActive;
  
  @Getter
  @Setter
  private Address address;
  
  @Getter
  @Setter
  private ContactInfo contactInfo;
}
