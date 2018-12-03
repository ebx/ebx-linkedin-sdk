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

package com.echobox.api.linkedin.types.statistics;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

/**
 * Page Statistics Model 
 * @author Alexandros
 */
public class PageStatistics {
  
  @Getter
  @Setter
  @LinkedIn
  private Views views;
  
  /**
   * Views Model
   */
  public static class Views {
    
    @Getter
    @Setter
    @LinkedIn
    private PageViews mobileJobsPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews careersPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews mobileLifeAtPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews allDesktopPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews allMobilePageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews desktopJobsPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews jobsPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews overviewPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews desktopCareersPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews mobileOverviewPageViews;
    
    @Getter
    @Setter
    @LinkedIn
    private PageViews lifeAtPageViews;
    
    @Getter
    @Setter
    @LinkedIn
    private PageViews desktopOverviewPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews desktopLifeAtPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews mobileCareersPageViews;
  
    @Getter
    @Setter
    @LinkedIn
    private PageViews allPageViews;
  }
  
  /**
   * Page Views Model
   */
  public static class PageViews {
    
    @Getter
    @Setter
    @LinkedIn
    private Integer pageViews;
    
    @Getter
    @Setter
    @LinkedIn
    private Integer uniquePageViews;
  }
}
