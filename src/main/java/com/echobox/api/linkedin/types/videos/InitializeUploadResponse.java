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

package com.echobox.api.linkedin.types.videos;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Initialize Video Upload Response
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/videos-api#initialize-video-upload">Initialize a Video Upload</a>
 * @author sergio
 */
@NoArgsConstructor
public class InitializeUploadResponse {
  @Getter
  @LinkedIn
  private Value value;
  
  @Getter
  @LinkedIn
  private String thumbnailUploadUrl;
  
  /**
   * Value object
   * @author sergio
   */
  @NoArgsConstructor
  public static class Value {

    /**
     * Milliseconds since the epoch (1970-01-01 00:00 UTC) when the upload URLs expire
     */
    @Getter
    @LinkedIn
    private Long uploadUrlExpiresAt;
  
    /**
     * Video URN that the uploaded Video is associated with
     */
    @Getter
    @LinkedIn
    private URN video;
  
    /**
     * Identifier for an upload session
     */
    @Getter
    @LinkedIn
    private String uploadToken;
  
    /**
     * Instructions for parts of multi-part video upload
     */
    @Getter
    @LinkedIn
    private List<UploadInstruction> uploadInstructions;
  }
  
  /**
   * UploadInstruction object
   * @author sergio
   */
  @NoArgsConstructor
  public static class UploadInstruction {
  
    /**
     * URL HTTP upload URL that you will use to make HTTP PUT request
     */
    @Getter
    @LinkedIn
    private String uploadUrl;
  
    /**
     * First byte (inclusive) in the byte range to upload in this part
     */
    @Getter
    @LinkedIn
    private Long firstByte;
  
    /**
     * The last byte (inclusive) in the range to upload in this part
     */
    @Getter
    @LinkedIn
    private Long lastByte;
  }
}
