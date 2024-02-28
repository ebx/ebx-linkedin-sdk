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
import com.echobox.api.linkedin.types.Deleted;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The type Organization.
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/organization-lookup-api#schema">Organization Schema</a>
 * @author clementcaylux 
 */
public class Organization extends OrganizationBase {

  /**
   * Deleted timestamp
   */
  @Getter
  @Setter
  @LinkedIn
  private Deleted deleted;
  
  /**
   * Entity status
   */
  @Getter
  @Setter
  @LinkedIn
  private String entityStatus;
  
  /**
   * Groups featured by the organizational entity. Default to empty array.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<URN> groups;

  /**
   * The locale-specific website of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private String localizedWebsite;
  
  /**
   * List of locations for the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<LocationInfo> locations;

  /**
   * Admin-defined specialty
   */
  @Getter
  @Setter
  @LinkedIn
  private List<Specialty> specialties;

  /**
   * Range of the number of staff associated with this entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private String staffCountRange;

  /**
   * School-specific attributes of the organization. 
   * If this field exists, then this entity is a school.
   */
  @Getter
  @Setter
  @LinkedIn
  private SchoolAttribute schoolAttributes;
  
  /**
   * The image used in the Overview tab on the organization's page.
   */
  @Getter
  @Setter
  @LinkedIn
  private CroppedImage overviewPhotoV2;
  
  /**
   * Status of the organization, such as operating or out of business.
   */
  @Getter
  @Setter
  @LinkedIn
  private String organizationStatus;

  /**
   * Type of organization.
   */
  @Getter
  @Setter
  @LinkedIn
  private String organizationType;
  
}
