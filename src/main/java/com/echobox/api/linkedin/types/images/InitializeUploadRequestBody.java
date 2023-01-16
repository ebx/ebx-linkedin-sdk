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

package com.echobox.api.linkedin.types.images;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Register Upload Request body
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#initialize-image-upload">Initialize an Image Upload</a>
 * @author Sergio Abplanalp
 */
@RequiredArgsConstructor
@NoArgsConstructor
public class InitializeUploadRequestBody {
  
  @NonNull
  @Setter
  @Getter
  @LinkedIn
  private InitializeUploadRequest initializeUploadRequest;
  
  /**
   * InitializeUploadRequest object
   * @author Sergio Abplanalp
   */
  @RequiredArgsConstructor
  @NoArgsConstructor
  public static class InitializeUploadRequest {
  
    /**
     * The URN of the entity that owns this asset
     */
    @NonNull
    @Setter
    @Getter
    @LinkedIn
    private URN owner;
  }
}
