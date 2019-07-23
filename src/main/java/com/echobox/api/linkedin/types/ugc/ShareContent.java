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
 * Share content
 * @see
 * <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#sharecontent">Share Content</a>
 * @author joanna
 */
public class ShareContent {
  
  @Getter
  @Setter
  @LinkedIn("com.linkedin.ugc.ShareContent")
  private ShareContentBody shareContent;
  
  /**
   * ShareContentBody object
   * @author Joanna
   */
  public static class ShareContentBody {
    /**
     * The media shared in this share. Can be videos, images, or articles.
     */
    @Getter
    @Setter
    @LinkedIn
    private List<ShareMedia> media;
  
    /**
     * The main landing page URL of the share.
     */
    @Getter
    @Setter
    @LinkedIn
    private String primaryLandingPageUrl;
  
    /**
     * Categorization info associated with the share.
     */
    @Getter
    @Setter
    @LinkedIn
    private ShareCategorization shareCategorization;
  
    /**
     * The message content of this share.
     */
    @Getter
    @Setter
    @LinkedIn
    private Commentary shareCommentary;
  
    /**
     * The type of media contained within the media field of this object.
     */
    @Getter
    @Setter
    @LinkedIn
    private String shareMediaCategory;
  
    /**
     * Categorization info associated with the share.
     * @author joanna
     */
    public static class ShareCategorization {
    
      /**
       * Skill categorizations for a share
       */
      @Getter
      @Setter
      @LinkedIn
      private List<URN> skills;
    }
  }
  
}
