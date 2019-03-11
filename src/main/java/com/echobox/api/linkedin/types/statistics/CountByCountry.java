package com.echobox.api.linkedin.types.statistics;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import lombok.Getter;
import lombok.Setter;

public class CountByCountry {
  
  @Getter
  @Setter
  @LinkedIn
  private FollowerCount followerCounts;
  
  @Getter
  @Setter
  @LinkedIn
  private URN country;
  
}
