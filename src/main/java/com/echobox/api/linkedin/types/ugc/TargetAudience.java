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

package com.echobox.api.linkedin.types.ugc;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Target Audience
 * @see
 * <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#targetaudience">Target Audience</a>
 * @author joanna
 */
@EqualsAndHashCode
public class TargetAudience implements Serializable {
  
  private static final long serialVersionUID = -1L;
  
  /**
   * The entities targeted for distribution.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<TargetAudienceEntity> targetedEntities;
  
  /**
   * TargetAudienceEntity
   * @author joanna
   */
  @EqualsAndHashCode
  public static class TargetAudienceEntity implements Serializable {
  
    private static final long serialVersionUID = -1L;
    
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
     * Location to be targeted.
     */
    @Getter
    @Setter
    @LinkedIn("locations")
    private List<URN> locations;
  
    /**
     * Standardized schools to be targeted.
     */
    @Getter
    @Setter
    @LinkedIn
    private List<URN> schools;
    
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
    private String staffCountRanges;
  }

}
