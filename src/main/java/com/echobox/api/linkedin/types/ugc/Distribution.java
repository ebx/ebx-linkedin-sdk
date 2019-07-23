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

import lombok.Getter;

import java.util.List;

/**
 * Distribution
 * @see
 * <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#distribution">Distribution</a>
 * @author joanna
 */
public class Distribution {
  
  /**
   * Whether UGC post is distributed to follow-feed or not.
   */
  @Getter
  @LinkedIn
  private boolean distributedViaFollowFeed;
  
  /**
   * External distribution channels that this content is distributed to.
   */
  @Getter
  @LinkedIn
  private List<String> externalDistributionChannels;

}
