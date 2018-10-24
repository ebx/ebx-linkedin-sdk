package com.echobox.api.linkedin.types;

import lombok.Getter;

public enum CompanyType {
  
  COMPANY_TYPE("C"),
  EDUCATIONAL("C"),
  SELF_EMPLOYED("C"),
  COVERNMENT_AGENCY("C"),
  NON_PROFIT("C"),
  SELF_OWNED("C"),
  PRIVATELY_HELD("C"),
  PARTNERSHIP("C");
  
  @Getter
  private String id;
  
  CompanyType(String id) {
    this.id = id;
  }

}
