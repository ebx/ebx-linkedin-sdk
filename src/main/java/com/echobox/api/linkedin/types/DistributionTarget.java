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
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Distribution target POJO
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/shares/share-api#distribution-targets">Share Distribution Targets</a>
 * @author joanna
 *
 */
public class DistributionTarget {
  
  /**
   * Restrict share to member's connections only.
   * Not applicable to organization shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private Boolean connectionsOnly;
  
  /**
   * Restrict share to specific industries.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> industries;
  
  /**
   * Restrict share to specific industries.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<Locale> interfaceLocales;
  
  /**
   * Restrict share to specific functions.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> jobFunctions;
  
  /**
   * Restrict share to specific locations.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> locations;
  
  /**
   * Restrict share to specific locations.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> seniorities;
  
  /**
   * Restrict share to members working at organizations of specific sizes.
   * Not applicable to member shares.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<String> staffCountRanges;
  
  /**
   * Make share visible to everyone, even guests on LinkedIn 
   */
  @Getter
  @Setter
  @LinkedIn
  private Boolean visibleToGuest;

}
