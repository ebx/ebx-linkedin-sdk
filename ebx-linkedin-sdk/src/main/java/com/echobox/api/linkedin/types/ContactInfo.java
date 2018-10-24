package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

public class ContactInfo {
  @Getter
  @Setter
  private String phoneOne;

  @Getter
  @Setter
  private String phoneTwo;

  @Getter
  @Setter
  private String fax;
}
