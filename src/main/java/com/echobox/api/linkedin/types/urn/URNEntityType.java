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

/**
 * The enum Urn entity type.
 * @author clementcaylux 
 */
public enum URNEntityType {

  /**
   * Place URN entity type.
   */
  PLACE("place"),
  /**
   * Country URN entity type.
   */
  COUNTRY("country"),
  /**
   * Organization URN entity type.
   */
  ORGANIZATION("organization"),
  /**
   * Seniority URN entity type.
   */
  SENIORITY("seniority"),
  /**
   * Function URN entity type.
   */
  FUNCTION("function"),
  /**
   * Share URN entity type.
   */
  SHARE("share"),
  /**
   * UGC post URN entity type.
   */
  UGCPOST("ugcPost");

  /**
   * The string representation of the type
   */
  private final String entityValue;

  /**
   * Instantiates a new URN entity type
   * @param name the URN entity type name
   */
  URNEntityType(String name) {
    this.entityValue = name;
  }

  /**
   * Getter the value of the entity
   * @return the value of the entity
   */
  public String getEntityValue() {
    return entityValue;
  }
}
