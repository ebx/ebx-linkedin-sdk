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
import com.echobox.api.linkedin.types.urn.URN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AdContext schema
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#adcontext">AdContext Schema</a>
 *
 * @author Sergio Abplanalp
 */
@NoArgsConstructor
@AllArgsConstructor
public class AdContext {
  
  /**
   * The Ad Account that created the Direct Sponsored Content (DSC).
   */
  @Getter
  @Setter
  @LinkedIn
  private URN dscAdAccount;
  
  /**
   * Among many types of content, only VIDEO is supported.
   */
  @Getter
  @Setter
  @LinkedIn
  private AdType dscAdType;
  
  /**
   * Plain text name of the DSC post. Only applicable for DSC.
   */
  @Getter
  @Setter
  @LinkedIn
  private String dscName;
  
  /**
   * The status of the advertising company content.
   * Representing whether the content is usable from the advertiser's perspective as a DSC.
   */
  @Getter
  @Setter
  @LinkedIn
  private Status dscStatus;
  
  /**
   * Whether this post is DSC. A posted DSC is created for the sole purpose of sponsorship.
   */
  @Getter
  @Setter
  @LinkedIn
  private boolean isDsc;
  
  /**
   * The post's ad content type.
   *
   * @author Sergio Abplanalp
   */
  public enum AdType {

    /**
     * Video content.
     */
    VIDEO,
  
    /**
     * Single image, text, or article.
     */
    STANDARD,
  
    /**
     * Carousel. Post contains a reference to a collection where the collection can only be images.
     */
    CAROUSEL,
  
    /**
     * Single content containing a LinkedIn job posting.
     */
    JOB_POSTING,
  
    /**
     * Native document uploaded to LinkedIn.
     * The following file types are supported: PPT, PPTX, DOC, DOCX, and PDF.
     */
    NATIVE_DOCUMENT,
  
    /**
     * Single content containing a LinkedIn event.
     */
    EVENT
  }
  
  public enum Status {
    
    /**
     * The content can be served.
     */
    ACTIVE,
    
    /**
     * The content is inactive and not intended for use, its creatives are cancelled.
     */
    ARCHIVED
  }
}
