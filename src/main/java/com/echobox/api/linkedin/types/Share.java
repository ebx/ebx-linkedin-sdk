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

package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Share POJO
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/shares/share-api#share-schema">
 * Share Schema</a>
 * @author joanna
 *
 */
public class Share extends LinkedInIdType {
  
  /**
   * Urn of the activity associated with this share.
   */
  @Getter
  private String activity;
  
  /**
   * Agent which acted on behalf of the owner. This permission has to be delegated. 
   * Use this field to create Direct Sponsored Content Share by specifying a Sponsored Account
   * URN when creating the share.
   */
  @Getter
  @Setter
  private String agent;
  
  /**
   * Referenced content such as articles and images
   */
  @Getter
  @Setter
  private ShareContent content;
  
  /**
   * Time of creation
   */
  @Getter
  private AuditStamp created;
  
  /**
   * Distribution target for the share
   */
  @Getter
  @Setter
  private ShareDistributionTarget distribution;
  
  /**
   * A flag that indicates if this share was edited by a member.
   */
  @Getter
  private Boolean edited;
  
  /**
   * Time of last modification
   */
  @Getter
  @Setter
  private AuditStamp lastModified;
  
  /**
   * If this share is a reshare, then this is the urn of the original/root share that was reshared.
   */
  @Getter
  private String originalShare;
  
  /**
   * Owner of the share
   */
  @Getter
  @Setter
  private String owner;
  
  /**
   * Share being reshared
   */
  @Getter
  @Setter
  private String resharedShare;
  
  /**
   * Share subject
   */
  @Getter
  @Setter
  private String subject;
  
  /**
   * Name of service provider from which the share creation was requested.
   */
  @Getter
  @Setter
  private String serviceProvider;
  
  /**
   * Text entered by the user for this share, which may contain annotations.
   */
  @Getter
  @Setter
  private ShareText text;

}
