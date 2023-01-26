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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class FinalizeUploadRequest {

  @NonNull
  @Setter
  @Getter
  @LinkedIn
  private RequestBody finalizeUploadRequest;
  
  public FinalizeUploadRequest(URN videoURN, String uploadToken, List<String> uploadedPartIds) {
    this(buildRequestBody(videoURN, uploadToken, uploadedPartIds));
  }
  
  private static RequestBody buildRequestBody(URN videoURN, String uploadToken,
      List<String> uploadedPartIds) {
    return new RequestBody(videoURN, uploadToken, uploadedPartIds);
  }
  
  @ToString
  @RequiredArgsConstructor
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class RequestBody {
    @NonNull
    @Setter
    @Getter
    @LinkedIn
    private URN video;
  
    @Setter
    @Getter
    @LinkedIn
    private String uploadToken = "";
  
    @NonNull
    @Setter
    @Getter
    @LinkedIn
    private List<String> uploadedPartIds;
  }
}
