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
  
  private String rawCompanyType;
  
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
  private List<Industry> industries;
  
  private String rawStatus;
  
  @Getter
  @Setter
  private Status status;
  
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
  
  private String rawStockExchange;
  
  @Getter
  @Setter
  private StockExchange stockExchange;
  
  @Getter
  @Setter
  private String endYear;
  
  @Getter
  @Setter
  private int numFollowers;
  
  /**
   * Creates the stock CompanyType enum from the raw string and populates the field.
   * Should be called when the JSON is mapped to the object
   */
  private void createCompanyType() {
    companyType = CompanyType.fromCode(rawCompanyType);
  }
  
  /**
   * Creates the Status enum from the raw string and populates the field.
   * Should be called when the JSON is mapped to the object
   */
  private void createStatus() {
    status = Status.fromCode(rawStatus);
  }

  /**
   * Creates the StockExchange enum from the raw string and populates the field.
   * Should be called when the JSON is mapped to the object
   */
  private void createStockExchange() {
    stockExchange = StockExchange.fromCode(rawStockExchange);
  }

}
