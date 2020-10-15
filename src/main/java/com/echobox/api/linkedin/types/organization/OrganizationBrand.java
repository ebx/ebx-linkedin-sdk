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
 * The type Organization Brand
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#organizationbrand"> Organization Brand</a>
 * @author alexandros 
 */
public class OrganizationBrand extends OrganizationBase {
  
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

}
