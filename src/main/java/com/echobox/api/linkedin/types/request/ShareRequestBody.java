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

package com.echobox.api.linkedin.types.request;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.ShareContent;
import com.echobox.api.linkedin.types.ShareDistribution;
import com.echobox.api.linkedin.types.ShareText;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Share request body class
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/share-api#post-shares">Post Shares</a>
 * @author joanna
 */
@RequiredArgsConstructor
public class ShareRequestBody {
  
  private ShareRequestBody() {}
  
  @Getter
  @Setter
  @LinkedIn
  @NonNull
  private URN owner;
  
  @Getter
  @Setter
  @LinkedIn
  private URN agent;
  
  @Getter
  @Setter
  @LinkedIn
  private String subject;
  
  @Getter
  @Setter
  @LinkedIn
  private ShareText text;
  
  @Getter
  @Setter
  @LinkedIn
  private ShareContent content;
  
  @Getter
  @Setter
  @LinkedIn
  private ShareDistribution distribution;
  
  @Getter
  @Setter
  @LinkedIn
  private URN resharedShare;
  
  @Getter
  @Setter
  @LinkedIn
  private URN originalShare;

}
