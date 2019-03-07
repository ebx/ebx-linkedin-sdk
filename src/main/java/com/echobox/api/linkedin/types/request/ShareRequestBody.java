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

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Share request body class
 * @author joanna
 */
@Data
@RequiredArgsConstructor
public class ShareRequestBody {
  
  private ShareRequestBody() {}
  
  @LinkedIn
  @NonNull
  private URN owner;
  
  @LinkedIn
  private String subject;
  
  @LinkedIn
  private ShareText text;
  
  @LinkedIn
  private ShareContent content;
  
  @LinkedIn
  private ShareDistribution distribution;
  
  @LinkedIn
  private URN resharedShare;
  
  @LinkedIn
  private URN originalShare;

}
