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

/**
 * Response context
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#responsecontext">Response Context</a>
 * @author joanna
 */
public class ResponseContext {
  
  /**
   * The content that a piece of content is a response to. Currently, the only supported Urn is
   * ugcPost Urn.
   */
  @LinkedIn
  @Getter
  private URN parent;
  
  /**
   * The greatest ancestor content that a piece of content is a response to. Currently, the only
   * supported Urn is ugcPost Urn.
   */
  @LinkedIn
  @Getter
  private URN root;

}
