package com.echobox.api.linkedin.types.organization;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

public class NetworkSize {
  
  @Getter
  @LinkedIn
  private Long firstDegreeSize;
  
}
