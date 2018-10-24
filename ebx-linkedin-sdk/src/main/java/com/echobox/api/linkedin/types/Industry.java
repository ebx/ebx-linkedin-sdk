package com.echobox.api.linkedin.types;

import lombok.Getter;

public class Industry {
  
  @Getter
  private int code;
  
  @Getter
  private String name;
  
  public Industry(int code, String name) {
    this.code = code;
    this.name = name;
  }
  
}
