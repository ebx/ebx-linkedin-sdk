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
import com.echobox.api.linkedin.types.function.FunctionURN;
import com.echobox.api.linkedin.types.locale.Locale;
import com.echobox.api.linkedin.types.location.CountryGroupURN;
import com.echobox.api.linkedin.types.location.CountryURN;
import com.echobox.api.linkedin.types.location.PlaceURN;
import com.echobox.api.linkedin.types.location.RegionURN;
import com.echobox.api.linkedin.types.location.StateURN;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Distribution target POJO
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/shares/share-api#distribution-
 * targets">Share Distribution Targets</a>
 * @author joanna
 *
 */
public class DistributionTarget {
  
  @Getter
  @LinkedIn
  private Boolean connectionsOnly;
  
  /**
   * TODO URN's are strings but need to be broken down into the URN object
   */
  @Getter
  @LinkedIn
  private List<String> industries;
  
  /**
   * TODO URN's are strings but need to be broken down into the URN object
   */
  @Getter
  private List<String> industriesURN;
  
  @Getter
  @LinkedIn
  private List<String> interfaceLocales;
  
  @Getter
  private List<Locale> interfaceLocalesURN;
  
  @Getter
  @LinkedIn
  private List<String> jobFunctions;
  
  @Getter
  private List<FunctionURN> jobFunctionsURN;
  
  /**
   * This value will contain the raw locations - the complete JSON mapper will try and map to the
   * correct location URN
   */
  @Getter
  @LinkedIn("locations")
  private List<String> locations;
  
  /**
   * TODO - map after completion
   */
  @Getter
  private List<CountryGroupURN> countryGroupURN;
  
  /**
   * TODO - map after completion
   */
  @Getter
  private List<CountryURN> countriesURN;
  
  /**
   * TODO - map after completion
   */
  @Getter
  private List<StateURN> statesURN;
  
  /**
   * TODO - map after completion
   */
  @Getter
  private List<RegionURN> regions;
  
  /**
   * TODO - map after completion
   */
  @Getter
  private List<PlaceURN> places;
  
  @Getter
  @LinkedIn
  private List<String> seniorities;
  
  @Getter
  private List<String> senioritiesURN;
  
  @Getter
  @LinkedIn
  private List<String> staffCountRanges;
  
  @Getter
  @LinkedIn
  private Boolean visibleToGuest;

}
