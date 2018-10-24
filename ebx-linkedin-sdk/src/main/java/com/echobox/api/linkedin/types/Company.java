package com.echobox.api.linkedin.types;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Company extends LinkedInType {
  
  @Getter
  @Setter
  private String name;
  
  @Getter
  @Setter
  private String universalName;
  
  @Getter
  @Setter
  private String emailDomains;
  
  @Getter
  @Setter
  private CompanyType companyType;
  
  @Getter
  @Setter
  private String ticker;
  
  @Getter
  @Setter
  private String websiteURL;
  
  @Getter
  @Setter
  private String status;
  
  @Getter
  @Setter
  private String logoURL;
  
  @Getter
  @Setter
  private String squareLogoURL;
  
  @Getter
  @Setter
  private String blogRSSURL;
  
  @Getter
  @Setter
  private long twitterId;
  
  @Getter
  @Setter
  private String employeeCountRange;
  
  @Getter
  @Setter
  private String specialities;
  
  @Getter
  @Setter
  private List<Location> locations;
  
  @Getter
  @Setter
  private String description;
  
  @Getter
  @Setter
  private String stockExchange;
  
  @Getter
  @Setter
  private String endYear;
  
  @Getter
  @Setter
  private int numFollowers;
}
