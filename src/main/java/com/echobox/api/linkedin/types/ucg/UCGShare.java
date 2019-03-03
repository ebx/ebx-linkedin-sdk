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

package com.echobox.api.linkedin.types.ucg;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.LinkedInURNIdType;
import com.echobox.api.linkedin.types.objectype.AuditStamp;
import com.echobox.api.linkedin.types.urn.URN;

import lombok.Getter;
import lombok.Setter;

/**
 * UCG Share
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations
 * /community-management/shares/ugc-post-api#schema">UCG Share</a>
 * @author joanna
 */
public class UCGShare extends LinkedInURNIdType {
  
  /**
   * Urn of the author of this content.
   */
  @Getter
  @Setter
  @LinkedIn
  private URN author;
  
  /**
   * For posts from API creation only.
   */
  @Getter
  @Setter
  @LinkedIn
  private URN clientApplication;
  
  /**
   * Urn of container entity that contains the user generated content such as an Organization or
   * Group.
   */
  @Getter
  @Setter
  @LinkedIn
  private URN containerEntity;
  
  /**
   * The content certification record associated with this content. Used to maintain information
   * about the content's visibility and spam status.
   */
  @Getter
  @Setter
  @LinkedIn
  private String contentCertificationRecord;
  
  /**
   * An AuditStamp corresponding to the creation of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private AuditStamp created;
  
  /**
   * An AuditStamp corresponding to the deletion of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private AuditStamp deleted;
  
  /**
   * LinkedIn and external destinations where the UGC post will be distributed.
   */
  @Getter
  @Setter
  @LinkedIn
  private Distribution distribution;
  
  /**
   * The time at which this content was first published. Represented in epoch time.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long firstPublishedAt;
  
  /**
   * An AuditStamp corresponding to the last modification of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private AuditStamp lastModified;
  
  /**
   * The state of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private String lifecycleState;
  
  /**
   * The service provider that created this UGC.
   */
  @Getter
  @Setter
  @LinkedIn
  private String origin;
  
  /**
   * The context in which this content was created. Represents concepts such as reshare and reshare
   * attribution.
   */
  @Getter
  @Setter
  @LinkedIn
  private ResponseContext responseContext;
  
  /**
   * Represents type-specific content of this object.
   */
  private ShareContent specificContent;
  
  /**
   * Intended audience or best fit audiences for this content as decided by the owner.
   */
  private TargetAudience targetAudience;
  
  /**
   * The service provider that created this UGC
   */
  private String ugcOrigin;
  
  /**
   * Version tag of the entity.
   */
  private String versionTag;
  
  /**
   * Visibility restrictions on content.
   */
  private String visibility;

}
