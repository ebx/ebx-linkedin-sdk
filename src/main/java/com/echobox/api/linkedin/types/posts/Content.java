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
import lombok.Getter;
import lombok.Setter;

/**
 * Content schema
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#content">Content Schema</a>
 *
 * @author Sergio Abplanalp
 */
public class Content {
  
  /**
   * Details of the Media content such as Image, Video
   */
  @Getter
  @Setter
  @LinkedIn
  private MediaContent media;
  
  /**
   * MediaContent object
   * @author Sergio Abplanalp
   */
  public static class MediaContent {
    /**
     * The URN of the media such as image or video.
     */
    @Getter
    @Setter
    @LinkedIn
    private URN id;
  
    /**
     * The media title. No title if empty.
     */
    @Getter
    @Setter
    @LinkedIn
    private String title;
  
    /**
     * The alternate text for the media. None if empty.
     */
    @Getter
    @Setter
    @LinkedIn
    private String altText;
  }
}
