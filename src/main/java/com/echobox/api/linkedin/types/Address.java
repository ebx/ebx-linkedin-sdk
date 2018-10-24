package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Address model
 * @author Joanna
 *
 */
public class Address {
  
  /**
   * First line of street address of location.
   */
  @Getter
  @Setter
  private String firstStreet;
  
  /**
   * Second line of street address of location.
   */
  @Getter
  @Setter
  private String secondStreet;
  
  /**
   * City for location.
   */
  @Getter
  @Setter
  private String city;
  
  /**
   * State for location.
   */
  @Getter
  @Setter
  private String state;
  
  /**
   * Postal code for location. Matches companies within a specific postal code.
   * Must be combined with the country-code parameter. Not supported for all countries.
   */
  @Getter
  @Setter
  private String postalCode;
  
  /**
   * Country code for location. Matches companies with a location in a specific country.
   */
  @Getter
  @Setter
  private int countryCode;
  
  /**
   * Region code for location.
   */
  @Getter
  @Setter
  private String regionCode;
}
