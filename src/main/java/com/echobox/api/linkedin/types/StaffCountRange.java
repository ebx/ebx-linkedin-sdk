package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StaffCountRange {
  
  SIZE_1(null, 1),
  
  SIZE_2_TO_10(2, 10),
  
  SIZE_11_TO_50(11, 50),
  
  SIZE_51_TO_200(51, 200),
  
  SIZE_201_TO_500(201, 500),
  
  SIZE_501_TO_1000(501, 1000),
  
  SIZE_1001_TO_5000(1001, 5000),
  
  SIZE_5001_TO_10000(5001, 10000),
  
  SIZE_10001_OR_MORE(10001, null);
  
  @Getter
  private final Integer min;
  
  @Getter
  private final Integer max;

}
