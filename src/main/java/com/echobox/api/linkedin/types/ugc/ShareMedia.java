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

package com.echobox.api.linkedin.types.ugc;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Share media class
 * @see
 * <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#sharemedia">Share Media</a>
 * @author joanna
 */
public class ShareMedia {
  
  /**
   * The description of this media.
   */
  @Getter
  @Setter
  @LinkedIn
  private Commentary description;
  
  /**
   * Url that overrides the landing page.
   */
  @Getter
  @Setter
  @LinkedIn
  private LandingPage landingPage;
  
  /**
   * The URN of the media shared
   */
  @Getter
  @Setter
  @LinkedIn
  private URN media;
  
  /**
   * An overlay associated with a media
   */
  @Getter
  @Setter
  @LinkedIn
  private MediaOverlay mediaOverlay;
  
  /**
   * URL whose content is summarized; content may not have a corresponding url for some entities.
   */
  @Getter
  @Setter
  @LinkedIn
  private String originalUrl;
  
  /**
   * The status of the availability of this media
   */
  @Getter
  @Setter
  @LinkedIn
  private String status;
  
  /**
   * The thumbnails saved from the ingestion of this article.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<Thumbnail> thumbnails;
  
  /**
   * The title of this media.
   */
  @Getter
  @Setter
  @LinkedIn
  private Commentary title;
  
  /**
   * Url that overrides the landing page.
   */
  public static class LandingPage {
    /**
     * content entity will be rendered as a CTA with landingPageTitle as the CTA text and
     * landingPageUrl as the click through url.
     */
    @Getter
    @Setter
    @LinkedIn
    private String landingPageTitle;
  
    /**
     * The click through url
     */
    @Getter
    @Setter
    @LinkedIn
    private String landingPageUrl;
  }
  
  /**
   * An overlay associated with a media(video, image etc).
   * @author joanna
   */
  public static class MediaOverlay {
    
    /**
     * Union of possible MediaOverlay model
     */
    @Getter
    @Setter
    @LinkedIn
    private Object overlay;
  }
  
  /**
   * The status of the availability of this media.
   * @author joanna
   */
  public enum Status {
    /**
     * This ShareMedia is processing and not yet available.
     */
    PROCESSING,
  
    /**
     * This ShareMedia is immediately available.
     */
    READY,
  
    /**
     * This ShareMedia is not available and no further processing is being done.
     */
    FAILED,
  }
  
  /**
   * The thumbnail saved from the ingestion of this article.
   */
  public static class Thumbnail {
  
    /**
     * The alternate text of this thumbnail.
     */
    @Getter
    @Setter
    @LinkedIn
    private String altText;
  
    /**
     * Height of the media in pixels.
     */
    @Getter
    @Setter
    @LinkedIn
    private Integer height;
  
    /**
     * Size of the media in bytes.
     */
    @Getter
    @Setter
    @LinkedIn
    private Integer size;
  
    /**
     * The url of this media.
     */
    @Getter
    @Setter
    @LinkedIn
    private String url;
  
    /**
     * Width of the media in pixels.
     */
    @Getter
    @Setter
    @LinkedIn
    private Integer width;
  }
  
}
