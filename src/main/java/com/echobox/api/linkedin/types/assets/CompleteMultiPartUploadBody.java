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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Complete multi-part upload body
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/vector-asset-api#complete-multi-part-upload">Complete Multi-Part
 * Upload</a>
 * @author Joanna
 */
@RequiredArgsConstructor
public class CompleteMultiPartUploadBody {
  
  @NonNull
  @Getter
  @Setter
  @LinkedIn
  private CompleteMultiPartUpload completeMultipartUploadRequest;
  
  private CompleteMultiPartUploadBody() {}
  
  /**
   * CompleteMultiPartUpload object
   * @author Joanna
   */
  public static class CompleteMultiPartUpload {
  
    @Getter
    @Setter
    @LinkedIn
    private URN mediaArtifact;
  
    @Getter
    @Setter
    @LinkedIn
    private String metadata;
  
    @Getter
    @Setter
    @LinkedIn
    private List<PartUploadResponses> partUploadResponses;
  }
  
  /**
   * PartUploadResponses object
   * @author Joanna
   */
  public static class PartUploadResponses {
  
    @Getter
    @Setter
    @LinkedIn
    private Headers headers;

    /**
     * The HTTP status code.
     */
    @Getter
    @Setter
    @LinkedIn
    public Integer httpStatusCode;
  }
  
  /**
   * Headers object
   * @author Joanna
   */
  @RequiredArgsConstructor
  public static class Headers {
    
    @Getter
    @Setter
    @NonNull
    @LinkedIn("ETag")
    private String eTag;
  
    private Headers() {}
  }
  
}
