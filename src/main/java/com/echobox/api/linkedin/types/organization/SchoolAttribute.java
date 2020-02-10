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

package com.echobox.api.linkedin.types.organization;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.Getter;
import lombok.Setter;

/**
 * The type School attributes.
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/organization-lookup-api#schoolattr">School Attribute Schema</a>
 * @author clementcaylux 
 */
public class SchoolAttribute {

  /**
   * The classification for school hierarchy
   */
  @Getter
  @Setter
  @LinkedIn
  private String hierarchyClassification;

  /**
   * Legacy school urn for the organization.
   */
  @Getter
  @Setter
  @LinkedIn
  private String legacySchool;

  /**
   * The type of institution.
   */
  @Getter
  @Setter
  @LinkedIn
  private String type;

  /**
   * Classification of the length of the institution's educational programs. 
   * These levels are defined by the National Center for Education Statistics 
   * (http://nces.ed.gov/pubs2015/2015097rev.pdf).
   */
  @Getter
  @Setter
  @LinkedIn
  private String yearLevel;
  
}
