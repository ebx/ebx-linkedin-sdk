package com.echobox.api.linkedin.types.statistics.page;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.TimeRange;
import com.echobox.api.linkedin.types.urn.URN;

import lombok.Getter;

public class FollowerStatistic {
  
  @Getter
  @LinkedIn
  private TimeRange timeRange;
  
  @Getter
  @LinkedIn
  private FollowerGain followerGains;
  
  @Getter
  @LinkedIn
  private URN organizationalEntity;
  
}
