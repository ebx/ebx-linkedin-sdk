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

package com.echobox.api.linkedin.types.posts;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.StaffCountRanges;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Target Entity
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#targetentities">Target Entities</a>
 *
 * @author Sergio Abplanalp
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetEntity {
  /**
   * Standardized degrees to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> degrees;

  /**
   * Standardized fields of study to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> fieldsOfStudy;

  /**
   * Industries to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> industries;
  
  /**
   * Interface locales to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<Locale> interfaceLocales;

  /**
   * Top level groupings of supertitles to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> jobFunctions;

  /**
   * GeoLocations for targeting.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> geoLocations;

  /**
   * Standardized schools to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> schools;

  /**
   * School organizations for targeting.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> organizations;
  
  /**
   * Seniorities to be targeted
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> seniorities;

  /**
   * Organization sizes to be targeted.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<StaffCountRanges> staffCountRanges;
}
