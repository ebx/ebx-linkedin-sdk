package com.echobox.api.linkedin.types;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public enum CompanyType {
  
  COMPANY_TYPE("C"),
  EDUCATIONAL("D"),
  SELF_EMPLOYED("E"),
  COVERNMENT_AGENCY("G"),
  NON_PROFIT("N"),
  SELF_OWNED("O"),
  PRIVATELY_HELD("P"),
  PARTNERSHIP("S");
  
  @Getter
  private String code;
  
  CompanyType(String code) {
    this.code = code;
  }
  
  public static CompanyType fromCode(String code) {
    if (!StringUtils.isBlank(code)) {
      for (CompanyType companyType : CompanyType.values()) {
        if (companyType.code.equals(code)) {
          return companyType;
        }
      }
    }
    return null;
  }

}
