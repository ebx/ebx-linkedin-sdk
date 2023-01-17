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

package com.echobox.api.linkedin.types.image;

import static org.junit.Assert.assertEquals;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.images.InitializeUpload;
import com.echobox.api.linkedin.types.images.InitializeUploadRequestBody;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Test;

public class ImageUploadTest extends DefaultJsonMapperTestBase {
  
  @Test
  public void testInitializeUpload() {
    String json = readFileToString("com.echobox.api.linkedin.jsonmapper/initializeUpload.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    InitializeUpload initializeUpload =
        defaultJsonMapper.toJavaObject(json, InitializeUpload.class);
  
    InitializeUpload.InitializeUploadValue value = initializeUpload.getValue();
    assertEquals(1650567510704L, value.getUploadUrlExpiresAt().longValue());
    assertEquals("https://www.linkedin.com/dms-uploads/C4E10AQFoyyAjHPMQuQ/uploaded-image/0?ca"
        + "=vector_ads&cn=uploads&sync=0&v=beta&ut=08zHQjMjAOLqc1", value.getUploadUrl());
    assertEquals(new URN("urn:li:image:C4E10AQFoyyAjHPMQuQ"), value.getImage());
  }
  
  @Test
  public void testInitializeUploadRequestBody() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/initializeUploadRequest.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    InitializeUploadRequestBody initializeUploadRequestBody =
        defaultJsonMapper.toJavaObject(json, InitializeUploadRequestBody.class);
    
    assertEquals(new URN("urn:li:organization:5583111"),
        initializeUploadRequestBody.getInitializeUploadRequest().getOwner());
  }
  
}
