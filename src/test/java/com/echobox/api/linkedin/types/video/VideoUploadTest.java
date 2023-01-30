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

package com.echobox.api.linkedin.types.video;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.videos.FinalizeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadResponse;
import org.junit.Test;

public class VideoUploadTest extends DefaultJsonMapperTestBase {
  @Test
  public void testInitializeUploadRequest() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/initializeVideoUploadRequest.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    InitializeUploadRequest initializeUploadRequest =
        defaultJsonMapper.toJavaObject(json, InitializeUploadRequest.class);
  
    InitializeUploadRequest.RequestBody requestBody =
        initializeUploadRequest.getInitializeUploadRequest();
    assertEquals(new URN("urn:li:organization:5637409"), requestBody.getOwner());
    assertEquals(Long.valueOf(731829), requestBody.getFileSizeBytes());
  }

  @Test
  public void testInitializeUploadResponse() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/initializeVideoUploadResponse.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    InitializeUploadResponse initializeUploadRequest =
        defaultJsonMapper.toJavaObject(json, InitializeUploadResponse.class);
  
    InitializeUploadResponse.Value value = initializeUploadRequest.getValue();
    assertEquals(Long.valueOf(1222398767), value.getUploadUrlExpiresAt());
    assertEquals(new URN("urn:li:video:C4E10AQFoyyAjHPMQuQ"), value.getVideo());
    assertEquals("cbR43NsIOs", value.getUploadToken());
    assertEquals(2, value.getUploadInstructions().size());
  }
  
  @Test
  public void testFinalizeUploadRequest() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/finalizeVideoUploadRequest.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    FinalizeUploadRequest finalizeUploadRequest =
        defaultJsonMapper.toJavaObject(json, FinalizeUploadRequest.class);
  
    FinalizeUploadRequest.RequestBody requestBody =
        finalizeUploadRequest.getFinalizeUploadRequest();
    assertEquals(new URN("urn:li:video:C5F10AQGKQg_6y2a4sQ"), requestBody.getVideo());
    assertEquals("cbR43NsIOs", requestBody.getUploadToken());
    assertTrue(requestBody.getUploadedPartIds().contains("e4383924336106965d6cd2a111beaceb"));
    assertTrue(requestBody.getUploadedPartIds().contains("24259a14365734f1f1b4abcdb5e55d01e"));
  }
}
