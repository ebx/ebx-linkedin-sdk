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

package com.echobox.api.linkedin.types.posts;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.LinkedInURNIdType;
import com.echobox.api.linkedin.types.ugc.Distribution;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Post
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#post-schema">Post Schema</a>
 *
 * @author Sergio Abplanalp
 */
@RequiredArgsConstructor
@NoArgsConstructor
public class Post extends LinkedInURNIdType {
  
  /**
   * Urn of the author of this content.
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private URN author;
  
  /**
   * A timestamp corresponding to the creation of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long createdAt;
  
  /**
   * A timestamp corresponding to the publication of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long publishedAt;
  
  /**
   * A timestamp corresponding to the deletion of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long deletedAt;

  /**
   * A timestamp corresponding to the last modification of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long lastModifiedAt;
  
  /**
   * LinkedIn and external destinations where the post will be distributed.
   */
  @Getter
  @Setter
  @LinkedIn
  private Distribution distribution;
  
  /**
   * The state info of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private LifecycleStateInfo lifecycleStateInfo;
  
  /**
   * The state of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private LifecycleState lifecycleState;
  
  /**
   * The reshare state of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private boolean isReshareDisabledByAuthor;
  
  /**
   * The comment of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private String commentary;
  
  /**
   * Represents the content of this object.
   */
  @Getter
  @Setter
  @LinkedIn
  private Content content;
  
  /**
   * The ads specific metadata associated with the post.
   */
  @Getter
  @Setter
  @LinkedIn
  private AdContext adContext;
  
  /**
   * Visibility restrictions on content.
   */
  @Getter
  @Setter
  @LinkedIn
  private Visibility visibility;
  
  /**
   * Visibility enum
   * @author Sergio Abplanalp
   */
  public enum Visibility {
    /**
     * Represents 1st degree network of owner.
     */
    CONNECTIONS,
    
    /**
     * Anyone can view this.
     */
    PUBLIC,
    
    /**
     * Viewable by logged in members only.
     */
    LOGGED_IN,
    
    /**
     * Visibility is delegated to the owner of the container entity.
     */
    CONTAINER
  }
  
  /**
   * Lifecycle state enum
   * @author Sergio Abplanalp
   */
  public enum LifecycleState {
    /**
     * Represents content that is accessible only to the author and is not yet published.
     */
    DRAFT,
    
    /**
     * Represents content that is accessible to all entities.
     * This is the only accepted field during creation.
     */
    PUBLISHED,
    
    /**
     * Represents content that has been submitted for publishing but is not yet ready for rendering.
     * The content will be published asynchronously once the processing has successfully completed.
     */
    PUBLISH_REQUESTED,
    
    /**
     * Represents content that has been submitted for publishing but was not processed.
     * An edit is required in order to re-attempt publishing.
     */
    PUBLISH_FAILED
  }
}
