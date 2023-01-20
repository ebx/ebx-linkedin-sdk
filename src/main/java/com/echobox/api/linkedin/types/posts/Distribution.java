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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Distribution schema
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#distribution">Distribution Schema</a>
 *
 * @author Sergio Abplanalp
 */
@RequiredArgsConstructor
@NoArgsConstructor
public class Distribution {
  
  /**
   * External distribution channels that this content is distributed to.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<String> thirdPartyDistributionChannels;

  /**
   * Specifies the feeds distributed to within LinkedIn.
   */
  @Getter
  @Setter
  @NonNull
  @LinkedIn
  private FeedDistribution feedDistribution;

  /**
   * Intended audience for this post. The target entities targeted for distribution.
   */
  @Getter
  @Setter
  @LinkedIn
  private List<TargetEntity> targetEntities;
  
  /**
   * Specifies the feeds distributed to within LinkedIn.
   */
  public enum FeedDistribution {

    /**
     * Do not distribute within LinkedIn via feed.
     */
    NONE,

    /**
     * Distribute to the flagship feed, and container entity feed if applicable.
     */
    MAIN_FEED
  }
}
