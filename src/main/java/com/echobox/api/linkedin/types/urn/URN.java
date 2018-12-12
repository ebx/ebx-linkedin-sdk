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

import org.apache.commons.lang3.StringUtils;

/**
 * The type Urn.
 * @author clementcaylux 
 */
public class URN {

  @Getter
  @Setter
  @LinkedIn
  private String entityType;

  @Getter
  @Setter
  @LinkedIn
  private String id;

  /**
   * Instantiates a new Urn.
   *
   * @param entityType the entity type 
   * @param id the id
   */
  public URN(String entityType, String id) {
    if (entityType == null || id == null) {
      throw new IllegalArgumentException("No argument provided can be null"); 
    }
    
    this.entityType = entityType.toUpperCase();
    this.id = id;
  }

  /**
   * Instantiates a new Urn.
   *
   * @param urnString the urn string
   */
  public URN(String urnString) {
    if (StringUtils.isBlank(urnString)) {
      throw new IllegalArgumentException("A linkedin urn cannot be empty");
    }
    
    if (!urnString.startsWith("urn:li:")) {
      throw new IllegalArgumentException("A linkedin urn should start with urn:li:");
    }

    String[] split = urnString.split(":", 4);
    if (split.length != 4) {
      throw new IllegalArgumentException("the urn " + urnString + " is malformed");
    }
    
    this.entityType = split[2].toUpperCase();
    this.id = split[3];
  }

  /**
   * Gets urn entity type.
   *
   * @return the urn entity type
   */
  public URNEntityType getURNEntityType() {
    return URNEntityType.valueOf(entityType);
  }

  @Override
  public String toString() {
    return "urn:li:" + entityType.toLowerCase() + ":" + id;
  }
  
  @Override
  public boolean equals(Object other) {
    
    if (!(other instanceof URN)) {
      return false;
    }
    
    URN otherURN = (URN) other;
    if (!otherURN.getEntityType().equals(this.entityType)) {
      return false;
    }

    return otherURN.getId().equals(this.id);
  }
  
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + entityType.hashCode();
    return 31 * result + id.hashCode();
  }
  
}
