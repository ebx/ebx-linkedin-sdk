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

import lombok.Getter;
import lombok.Setter;

/**
 * A Wrapper Class for all different Page Statistics Categories
 * @author Alexandros
 */
public abstract class PageStatisticsCategories {
  
  @Getter
  @Setter
  @LinkedIn
  private PageStatistics pageStatistics;
  
  /**
   * A page statics by seniority model
   */
  public static class PageStatisticsBySeniority extends PageStatisticsCategories {
    
    @Getter
    @Setter
    @LinkedIn
    private String seniority;
  }
  
  /**
   * A page statics by county model
   */
  public static class PageStatisticsByCountry extends PageStatisticsCategories {
    
    @Getter
    @Setter
    @LinkedIn
    private String country;
  }
  
  /**
   * A page statics by industry model
   */
  public static class PageStatisticsByIndustry extends PageStatisticsCategories {
    
    @Getter
    @Setter
    @LinkedIn
    private String industry;
  }
  
  /**
   * A page statics by staff count model
   */
  public static class PageStatisticsByStaffCountRange extends PageStatisticsCategories {
    
    @Getter
    @Setter
    @LinkedIn
    private String staffCountRange;
  }
  
  /**
   * A page statics by region model
   */
  public static class PageStatisticsByRegion extends PageStatisticsCategories {
    
    @Getter
    @Setter
    @LinkedIn
    private String region;
  }
  
  /**
   * A page statics by function model
   */
  public static class PageStatisticsByFunction extends PageStatisticsCategories {
  
    @Getter
    @Setter
    @LinkedIn
    private String function;
  }
}
