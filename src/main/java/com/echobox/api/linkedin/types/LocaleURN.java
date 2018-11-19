package com.echobox.api.linkedin.types;

import lombok.Getter;

public class LocaleURN extends URN {

  @Getter
  private Locale local;
  
  @Getter
  private String name;
  
}
