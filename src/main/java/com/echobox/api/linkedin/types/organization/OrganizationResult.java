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

import java.util.List;

/**
 * Class for deserialising from Organization endpoint, which may return Organization or
 * OrganizationBrand
 * This class should contain all fields from Organization and OrganizationBrand
 * @author Kenneth Wong
 */
public class OrganizationResult extends Organization {
  
  /**
   * Admin-defined specialty tags of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<Specialty> tags;
  
  /**
   * The URN of the entity's pinned post.
   */
  @Getter
  @Setter
  @LinkedIn
  private String pinnedPost;
  
  
  /**
   * Whether the entity was auto-created.
   */
  @Getter
  @Setter
  @LinkedIn
  private Boolean autoCreated;
  
  public OrganizationBase getOrganization() {
    OrganizationBase result;
    if (PrimaryOrganizationType.BRAND.equals(this.getPrimaryOrganizationType())) {
      // OrganizationBrand
      OrganizationBrand brand = new OrganizationBrand();
      brand.setTags(this.getTags());
      brand.setPinnedPost(this.getPinnedPost());
      brand.setAutoCreated(this.getAutoCreated());
      
      result = brand;
    } else {
      // Organization
      Organization organization = new Organization();
      organization.setDeleted(this.getDeleted());
      organization.setEntityStatus(this.getEntityStatus());
      organization.setGroups(this.getGroups());
      organization.setLocalizedWebsite(this.getLocalizedWebsite());
      organization.setLocations(this.getLocations());
      organization.setSpecialties(this.getSpecialties());
      organization.setStaffCountRange(this.getStaffCountRange());
      organization.setSchoolAttributes(this.getSchoolAttributes());
      organization.setOverviewPhotoV2(this.getOverviewPhotoV2());
      organization.setOrganizationType(this.getOrganizationType());

      result = organization;
    }
    
    // Common fields in OrganizationBase
    result.setAlternativeNames(this.getAlternativeNames());
    result.setCoverPhotoV2(this.getCoverPhotoV2());
    result.setDefaultLocale(this.getDefaultLocale());
    result.setDescription(this.getDescription());
    result.setFoundedOn(this.getFoundedOn());
    result.setLocalizedDescription(this.getLocalizedDescription());
    result.setIndustries(this.getIndustries());
    result.setLocalizedName(this.getLocalizedName());
    result.setLocalizedSpecialties(this.getLocalizedSpecialties());
    result.setName(this.getName());
    result.setParentRelationship(this.getParentRelationship());
    result.setVanityName(this.getVanityName());
    result.setVersionTag(this.getVersionTag());
    result.setWebsite(this.getWebsite());
    result.setLogoV2(this.getLogoV2());
    result.setPrimaryOrganizationType(this.getPrimaryOrganizationType());
    
    return result;
  }
}
