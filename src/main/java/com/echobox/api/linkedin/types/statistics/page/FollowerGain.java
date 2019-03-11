package com.echobox.api.linkedin.types.statistics.page;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

public class FollowerGain {
  
  @Getter
  @Setter
  @LinkedIn
  private Long organicFollowerGain;
  
  @Getter
  @Setter
  @LinkedIn
  private Long paidFollowerGain;
  
}
