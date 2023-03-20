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
import com.echobox.api.linkedin.types.images.ImageDetails;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.videos.VideoDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Post
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#post-schema">Post Schema</a>
 *
 * @author Sergio Abplanalp
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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
   * The ads specific metadata associated with the post.
   */
  @Getter
  @Setter
  @LinkedIn
  private AdContext adContext;
  
  /**
   * The comment of this content.
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private String commentary;
  
  /**
   * Container Entity URN that contains user generated content.
   */
  @Getter
  @Setter
  @LinkedIn
  private URN container;
  
  /**
   * Represents the content of this object.
   */
  @Getter
  @Setter
  @LinkedIn
  private Content content;
  
  /**
   * Web page that is opened when the member clicks on the associated content.
   */
  @Getter
  @Setter
  @LinkedIn
  private String contentLandingPage;
  
  /**
   * The call to action label which a member can act upon that is associated with the content.
   */
  @Getter
  @Setter
  @LinkedIn
  private ContentCallToActionLabel contentCallToActionLabel;
  
  /**
   * A timestamp corresponding to the creation of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long createdAt;
  
  /**
   * LinkedIn and external destinations where the post will be distributed.
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private Distribution distribution;
  
  /**
   * ugcPostUrn or shareUrn
   */
  @Getter
  @Setter
  @LinkedIn
  private URN id;
  
  /**
   * The reshare state of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private Boolean isReshareDisabledByAuthor;
  
  /**
   * A timestamp corresponding to the last modification of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long lastModifiedAt;
  
  /**
   * The state of this content
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private LifecycleState lifecycleState;
  
  /**
   * The state info of this content
   */
  @Getter
  @Setter
  @LinkedIn
  private LifecycleStateInfo lifecycleStateInfo;
  
  /**
   * The service provider that created this UGC.
   */
  @Getter
  @Setter
  @LinkedIn
  private String origin;

  /**
   * A timestamp corresponding to the publication of this resource.
   */
  @Getter
  @Setter
  @LinkedIn
  private Long publishedAt;
  
  /**
   * The context in which the post was re-shared.
   */
  @Getter
  @Setter
  @LinkedIn
  private ReshareContext reshareContext;
  
  /**
   * Visibility restrictions on content.
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private Visibility visibility;
  
  /**
   * Mapping of image URN to image details.
   * The image details will need to be retrieved via a separate API call and populated manually.
   */
  @Getter
  @Setter
  private Map<URN, ImageDetails> images;
  
  /**
   * Mapping of video URN to video details.
   * The video details will need to be retrieved via a separate API call and populated manually.
   */
  @Getter
  @Setter
  private Map<URN, VideoDetails> videos;

  public String getTitle() {
    Content content = this.getContent();
    if (content == null) {
      return null;
    }
    
    if (content.getMedia() != null) {
      return content.getMedia().getTitle();
    }
    
    if (content.getArticle() != null) {
      return content.getArticle().getTitle();
    }
    
    return null;
  }
  
  public String getDescription() {
    return Optional.ofNullable(this.getContent())
        .map(Content::getArticle)
        .map(ArticleContent::getDescription)
        .orElse(null);
  }
  
  public List<String> getImageURLs() {
    return images.values().stream()
        .map(ImageDetails::getDownloadUrl)
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
  }
  
  public List<String> getVideoURLs() {
    return videos.values().stream()
        .map(VideoDetails::getDownloadUrl)
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
  }
  
  /**
   * ContentCallToActionLabel enum
   * @author Sergio Abplanalp
   */
  public enum ContentCallToActionLabel {
    /**
     *  Call To Action button on the creative shows 'Apply'.
     */
    APPLY,
    
    /**
     *  Call To Action button on the creative shows 'Download'.
     */
    DOWNLOAD,
    
    /**
     *  Call To Action button on the creative shows 'View Quote'.
     */
    VIEW_QUOTE,
    
    /**
     *  Call To Action button on the creative shows 'Learn More'.
     */
    LEARN_MORE,
    
    /**
     *  Call To Action button on the creative shows 'Sign Up'.
     */
    SIGN_UP,
    
    /**
     *  Call To Action button on the creative shows 'Subscribe'.
     */
    SUBSCRIBE,
    
    /**
     *  Call To Action button on the creative shows 'Register'.
     */
    REGISTER,
    
    /**
     *  Call To Action button on the creative shows 'Join'.
     */
    JOIN,
    
    /**
     *  Call To Action button on the creative shows 'Attend'.
     */
    ATTEND,
    
    /**
     *  Call To Action button on the creative shows 'Register Demo'.
     */
    REQUEST_DEMO,
    
    /**
     *  Call To Action button on the creative shows 'See More'.
     */
    SEE_MORE
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
}
