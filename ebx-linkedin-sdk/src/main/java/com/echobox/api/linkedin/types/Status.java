package com.echobox.api.linkedin.types;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public enum Status {
  
  OPERATING("OPR"),
  OPERATING_SUBSIDIARY("OPS"),
  REORGANIZING("RRG"),
  OUT_OF_BUSINESS("OOB"),
  ACQUIRED("ACQ");
  
  @Getter
  private String code;
  
  Status(String code) {
    this.code = code;
  }
  
  public static Status fromCode(String code) {
    if (!StringUtils.isBlank(code)) {
      for (Status status : Status.values()) {
        if (status.code.equals(code)) {
          return status;
        }
      }
    }
    return null;
  }

}
