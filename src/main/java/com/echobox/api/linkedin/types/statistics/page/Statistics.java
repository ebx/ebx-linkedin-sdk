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
import com.echobox.api.linkedin.types.TimeRange;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsByCountry;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsByFunction;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsByIndustry;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsByRegion;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsBySeniority;
import com.echobox.api.linkedin.types.statistics.page.PageStatisticsCategories.PageStatisticsByStaffCountRange;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * A abstract wrapper for statistics models
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/page-statistics">
 *  Page Statistics Categories
 * </a>
 * @author Alexandros
 */
public abstract class Statistics {
  
  @Setter
  @Getter
  @LinkedIn
  private TimeRange timeRange;
  
  @Getter
  @Setter 
  @LinkedIn
  private TotalPageStatistics totalPageStatistics;
  
  @Getter
  @Setter
  @LinkedIn
  private List<PageStatisticsBySeniority> pageStatisticsBySeniority;
  
  @Getter
  @Setter
  @LinkedIn
  private List<PageStatisticsByCountry> pageStatisticsByCountry;
  
  @Setter
  @Getter
  @LinkedIn
  private List<PageStatisticsByIndustry> pageStatisticsByIndustry;
  
  @Getter
  @Setter
  @LinkedIn
  private List<PageStatisticsByStaffCountRange> pageStatisticsByStaffCountRange;
  
  @Getter
  @Setter
  @LinkedIn
  private List<PageStatisticsByRegion> pageStatisticsByRegion;
  
  @Getter
  @Setter
  @LinkedIn
  private List<PageStatisticsByFunction> pageStatisticsByFunction;
  
  /**
   * Organization Statistics Model
   */
  public static class OrganizationStatistics extends Statistics {
    
    @Getter
    @Setter
    @LinkedIn
    private URN organization;
  }
  
  /**
   * Brand Statistics Model
   */
  public static class BrandStatistics extends Statistics {
    
    @Getter
    @Setter
    @LinkedIn
    private URN brand;
  }
}
