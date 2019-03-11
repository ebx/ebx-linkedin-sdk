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

import java.util.List;


/**
 * @author clementcaylux on 03/12/2018.
 */
public class OrganizationFollowerStatisticsElement {

  @Getter
  @Setter
  @LinkedIn
  private List<CountByStaffCountRange> followerCountsByStaffCountRange;

  @Getter
  @Setter
  @LinkedIn
  private List<CountByIndustry> followerCountsByIndustry;

  @Getter
  @Setter
  @LinkedIn
  private List<CountByFunction> followerCountsByFunction;

  @Getter
  @Setter
  @LinkedIn
  private List<CountByAssociationType> followerCountsByAssociationType;

  @Getter
  @Setter
  @LinkedIn
  private List<CountBySeniority> followerCountsBySeniority;
  
  @Getter
  @Setter
  @LinkedIn
  private List<CountByCountry> followerCountsByCountry;
  
  @Getter
  @Setter
  @LinkedIn
  private List<CountByRegion> followerCountsByRegion;

  @Getter
  @Setter
  @LinkedIn
  private String organizationalEntity;
}
