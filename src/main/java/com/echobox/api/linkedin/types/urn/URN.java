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

package com.echobox.api.linkedin.types.urn;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

/**
 * @author clementcaylux on 03/12/2018.
 */
public class URN {
  
  @Getter
  @Setter
  @LinkedIn
  private URNRessourceType entityType;
  
  @Getter
  @Setter
  @LinkedIn
  private String id;

  /**
   * Instantiates a new URN.
   *
   * @param entityType the entity type 
   * @param id the id
   */
  public URN(URNRessourceType entityType, String id) {
    this.entityType = entityType;
    this.id = id;
  }

  /**
   * Extract URN from string
   *
   * @param urnString the urn string 
   * @return the urn
   */
  public static URN extractFromString(String urnString) {
    String[] split = urnString.split(":");
    if (split.length != 4 || !"urn".equals(split[0]) || !"li".equals(split[1])) {
      throw new IllegalArgumentException("The String passed does not correspond to a valid urn");
    }
      
    return new URN(URNRessourceType.valueOf(split[2].toUpperCase()), split[3]);
  }
  
}
