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
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.objectype.LocaleString;
import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
import com.echobox.api.linkedin.types.urn.URN;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * The type Organization.
 * @author clementcaylux 
 */
public class Organization {
  
  @Getter
  @Setter
  @LinkedIn
  private List<LocaleString> alternativeNames;

  @Getter
  @Setter
  @LinkedIn
  private CroppedImage coverPhoto;
  
  @Getter
  @Setter
  @LinkedIn
  private CroppedImage coverPhotoV2;
  
  @Getter
  @Setter
  @LinkedIn
  private Locale defaultLocale;

  @Getter
  @Setter
  @LinkedIn
  private  Long deletedTime;

  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString description;

  @Getter
  @Setter
  @LinkedIn
  private Date foundedOn;
  
  //TODO: check whether URN is more adapted : documentation says field should be of type GroupUrn[]
  @Getter
  @Setter
  @LinkedIn
  private List<URN> groups;
  
  @Getter
  @Setter
  @LinkedIn
  private long id;

  //TODO: check whether URN is more adapted : documentation says field should be of type 
  // IndustryUrn[]
  @Getter
  @Setter
  @LinkedIn
  private List<URN> industries;
  
  @Getter
  @Setter
  @LinkedIn
  private String localizedDescription;
  
  @Getter
  @Setter
  @LinkedIn
  private String localizedName;
  
  @Getter
  @Setter
  @LinkedIn
  private List<String> localizedSpecialties;

  @Getter
  @Setter
  @LinkedIn
  private String localizedWebsite;
  
  @Getter
  @Setter
  @LinkedIn
  private List<LocationInfo> locations;
  
  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString name;

  //TODO The documentation here specifies that this is the urn of the parent organization
  @Getter
  @Setter
  @LinkedIn
  private ParentRelationship parentRelationship;

  @Getter
  @Setter
  @LinkedIn
  private String[] specialitiesTags;

  @Getter
  @Setter
  @LinkedIn
  private String vanityName;

  @Getter
  @Setter
  @LinkedIn
  private String versionTag;

  @Getter
  @Setter
  @LinkedIn
  private MultiLocaleString website;

  @Getter
  @Setter
  @LinkedIn
  private String staffCountRange;

  @Getter
  @Setter
  @LinkedIn
  private SchoolAttributes schoolAttributes;

  @Getter
  @Setter
  @LinkedIn
  private CroppedImage overviewPhoto;

  @Getter
  @Setter
  @LinkedIn
  private CroppedImage overviewPhotov2;
  
  @Getter
  @Setter
  @LinkedIn
  private CroppedImage logo;

  @Getter
  @Setter
  @LinkedIn
  private CroppedImage logoV2;

  @Getter
  @Setter
  @LinkedIn
  private String organizationStatus;

  @Getter
  @Setter
  @LinkedIn
  private String organizationType;
}

