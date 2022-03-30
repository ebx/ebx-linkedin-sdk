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
import com.echobox.api.linkedin.types.LinkedInIdAndURNType;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.objectype.LocaleString;
import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Common fields for Organization and Organization Brand
 * 
 * @author alexandros
 */
public abstract class OrganizationBase extends LinkedInIdAndURNType {
  
  /**
   *  Alternative names of the entity. There can be multiple names per locale.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<LocaleString> alternativeNames;
  
  /**
   * The entity's background cover image.
   */
  @Getter
  @Setter
  @LinkedIn
  @Deprecated
  private CroppedImage coverPhoto;
  
  /**
   * The entity's background cover image. 
   * The sizes may vary, so clients should handle the given height and width accordingly.
   */
  @Getter
  @Setter
  @LinkedIn
  private CroppedImage coverPhotoV2;
  
  /**
   * Default locale of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private Locale defaultLocale;
  
  /**
   * Description for the entity
   */
  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString description;
  
  
  /**
   * Date when the entity was founded.
   */
  @Getter
  @Setter
  @LinkedIn
  private Date foundedOn;
  
  /**
   * The locale-specific description of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private String localizedDescription;
  
  /**
   * The industries associated with the entity.
   */
  @Getter
  @Setter
  @LinkedIn("industries")
  private List<URN> industries;
  
  /**
   * The locale-specific name of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private String localizedName;
  
  /**
   * The locale-specific, admin-defined specialty tags of the entity.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<String> localizedSpecialties;
  
  
  /**
   * Entity's name.
   */
  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString name;
  
  
  /**
   * The parent organization.
   */
  @Getter
  @Setter
  @LinkedIn
  private ParentRelationship parentRelationship;
  
  /**
   * Entity's unique name used in URLs.
   */
  @Getter
  @Setter
  @LinkedIn
  private String vanityName;
  
  
  /**
   * Tag indicating version.
   */
  @Getter
  @Setter
  @LinkedIn
  private String versionTag;
  
  /**
   * Entity's website.
   */
  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString website;
  
  /**
   * The entity’s logo.
   */
  @Getter
  @Setter
  @LinkedIn
  @Deprecated
  private CroppedImage logo;
  
  
  /**
   * The entity’s logo. The sizes may vary greatly, 
   * i.e., 50x50, 100x60, 400x400, so clients should handle the given height and width accordingly.
   */
  @Getter
  @Setter
  @LinkedIn
  private CroppedImage logoV2;
  
  /**
   * Primary Organization type
   */
  @Getter
  @Setter
  @LinkedIn
  private PrimaryOrganizationType primaryOrganizationType;
  
}
