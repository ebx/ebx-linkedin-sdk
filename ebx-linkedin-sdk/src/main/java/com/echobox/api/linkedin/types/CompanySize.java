package com.echobox.api.linkedin.types;

import lombok.Getter;

public enum CompanySize {
  
  A("A", 0, 1),
  B("B", 2, 10),
  C("C", 11, 50),
  D("D", 51, 200),
  E("E", 201, 500),
  F("F", 501, 1000),
  G("G", 1001, 5000),
  H("H", 5001, 10000),
  I("I", 10000, null);
  
  @Getter
  private final String code;
  
  @Getter
  private final Integer startRange;
  
  @Getter
  private final Integer endRange;
  
  CompanySize(String code, int startRange, Integer endRange) {
    this.code = code;
    this.startRange = startRange;
    this.endRange = endRange;
  }

}
