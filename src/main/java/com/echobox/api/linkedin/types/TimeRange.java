/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echobox.api.linkedin.types;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.Getter;

/**
 * Time range model
 * @author Alexandros 
 */
public class TimeRange {
  
  @Getter
  @LinkedIn
  private Long start;
  
  @Getter
  @LinkedIn
  private Long end;
  
  private TimeRange() {}
  
  /**
   * Initialise time range
   * @param start start of the time range
   * @param end end of the time range
   */
  public TimeRange(Long start, Long end) {
    if (start == null && end == null) {
      throw new IllegalArgumentException("Start and end cannot both be null");
    }
    if (start > end) {
      throw new IllegalStateException("Start should be before the end");
    }
    this.start = start;
    this.end = end;
  }
  
}
