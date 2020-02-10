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

package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;

import java.util.Map;

/**
 * Register upload
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/vector-asset-api#register-an-upload">Register an Upload</a>
 * @author Joanna
 */
public class RegisterUpload {
  
  @Getter
  @LinkedIn
  private RegisterUploadValue value;
  
  /**
   * RegisterUploadValue object
   * @author Joanna
   */
  public static class RegisterUploadValue {
    @Getter
    @LinkedIn
    private URN asset;
    
    @Getter
    @LinkedIn
    private URN mediaArtifact;
  
    @Getter
    @LinkedIn
    private UploadMechanism uploadMechanism;
  }
  
  /**
   * UploadMechanism Object
   * @author Joanna
   */
  public static class UploadMechanism {
    @Getter
    @LinkedIn("com.linkedin.digitalmedia.uploading.MediaUploadHttpRequest")
    private MediaUploadHttpRequest mediaUploadHttpRequest;
  }
  
  /**
   * MediaUploadHttpRequest object
   * @author Joanna
   */
  public static class MediaUploadHttpRequest {
    @Getter
    @LinkedIn
    private Map<String, String> headers;
  
    @Getter
    @LinkedIn
    private String uploadUrl;
  }

}
