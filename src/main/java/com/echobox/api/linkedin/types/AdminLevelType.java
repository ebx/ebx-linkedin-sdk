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

/**
 * Admin level type enum
 * @author joanna
 *
 */
@Deprecated
public enum AdminLevelType {
  
  /**
   * A large region of the country that can span over more than one state or province
   */
  COUNTRY_AREA,
  
  /**
   * An aggregation of counties and/or cities. Usually a state or a province
   */
  STATE,
  
  /**
   * An aggregation of cities. Usually a county, jurisdiction, or department
   */
  COUNTY,
  
  /**
   * A group of postal codes. Usually a city or town
   */
  CITY,
  
  /**
   * None of the above
   */
  NULL;

}
