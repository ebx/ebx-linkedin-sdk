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

package com.echobox.api.linkedin.types.urn.location;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.LocaleStringNameURN;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;

/**
 * Place URN POJO
 * @see <a href="https://developer.linkedin.com/docs/ref/v2/standardized-data/locations/places">
 * Place URN</a>
 * @author joanna
 *
 */
public class PlaceURN extends LocaleStringNameURN {
  
  /**
   * The administrative level of the place
   */
  @Getter
  @LinkedIn
  private String adminLevel;
  
  /**
   * The country URN that owns the place
   */
  @Getter
  @LinkedIn
  private URN country;
  
  /**
   * The parent place URN for this place. Can be null when this place is the top-level
   * administrative division.
   */
  @Getter
  @LinkedIn
  private URN parent;
  
  /**
   * The unique code within a country to identify the place
   */
  @Getter
  @LinkedIn
  private String placeCode;

}
