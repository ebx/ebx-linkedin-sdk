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

package com.echobox.api.linkedin.types.statistics.page;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.statistics.page.PageStatistic.Views;
import lombok.Getter;
import lombok.Setter;

/**
 * Total Page Statistics Model
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/page-statistics">
 *   Total Statistics
 * </a>
 * @author Alexandros
 */
public class TotalPageStatistics {
  
  @Getter
  @Setter
  @LinkedIn
  private Clicks clicks;
  
  @Getter
  @Setter
  @LinkedIn
  private Views views;
  
  /**
   * Clicks Model 
   */
  public static class Clicks {
    
    @Getter
    @Setter
    @LinkedIn
    private PageClicks careersPageClicks;
  
    @Getter
    @Setter
    @LinkedIn
    private PageClicks mobileCareersPageClicks;
  }
  
  
  /**
   * Page Clicks Model
   */
  public static class PageClicks {
    
    @Getter
    @Setter
    @LinkedIn
    private Integer careersPageBannerPromoClicks;
  
    @Getter
    @Setter
    @LinkedIn
    private Integer careersPageEmployeesClicks;
  
    @Getter
    @Setter
    @LinkedIn
    private Integer careersPagePromoLinksClicks;
  
    @Getter
    @Setter
    @LinkedIn
    private Integer careersPageJobsClicks;
    
  }
}
