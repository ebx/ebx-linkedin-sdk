package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

public class Address {
  @Getter
  @Setter
  private String firstStreet;
  
  @Getter
  @Setter
  private String secondStreet;
  
  @Getter
  @Setter
  private String city;
  
  @Getter
  @Setter
  private String state;
  
  @Getter
  @Setter
  private String postalCode;
  
  @Getter
  @Setter
  private String countryCode;
  
  @Getter
  @Setter
  private String regionCode;
}
