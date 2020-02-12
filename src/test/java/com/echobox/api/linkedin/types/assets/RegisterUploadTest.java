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

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * RegisterUploadTest
 * @author joanna
 */
public class RegisterUploadTest {
  
  @Test
  public void testRegisterUpload() {
    String json = "{\n" + "    \"value\": {\n"
        + "        \"asset\": \"urn:li:digitalmediaAsset:C5400AQHpR1ANqMWqNA\",\n"
        + "        \"mediaArtifact\": \"urn:li:digitalmediaMediaArtifact:"
        + "(urn:li:digitalmediaAsset:C5400AQHpR1ANqMWqNA,"
        + "urn:li:digitalmediaMediaArtifactClass:aws-userUploadedVideo)\",\n"
        + "        \"uploadMechanism\": {\n"
        + "            \"com.linkedin.digitalmedia.uploading.MediaUploadHttpRequest\": {\n"
        + "                \"headers\": {\n"
        + "                    \"Content-Type\": \"application/octet-stream\",\n"
        + "                    \"x-amz-server-side-encryption\": \"aws:kms\",\n"
        + "                    \"x-amz-server-side-encryption-aws-kms-key-id\": "
        + "\"e10ace24-blah-4977-bar-89foo193e2ab\"\n"
        + "                },\n"
        + "                \"uploadUrl\": \"https://video-uploads.s3-accelerate.amazonaws"
        + ".com/C5400AQHpR1ANqMWqNA/aws-userUploadedVideo?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz"
        + "-Date=20180120T000018Z&X-Amz-SignedHeaders=content-type%3Bhost%3Bx-amz-server-side"
        + "-encryption%3Bx-amz-server-side-encryption-aws-kms-key-id&X-Amz-Expires=86400&X-Amz"
        + "-Credential=AKIAJYU2MA%2F20180120%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature"
        + "=f7c0756a80998786766588878768778768977687d4c687b3f1a0e8\"\n"
        + "            }\n" + "        }\n" + "    }\n" + "}";
    
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    RegisterUpload registerUpload = defaultJsonMapper.toJavaObject(json, RegisterUpload.class);
    
    RegisterUpload.RegisterUploadValue value = registerUpload.getValue();
    
    Assert.assertEquals(new URN("urn:li:digitalmediaAsset:C5400AQHpR1ANqMWqNA"), value.getAsset());
    Assert.assertEquals(new URN(
            "urn:li:digitalmediaMediaArtifact:" + "(urn:li:digitalmediaAsset:C5400AQHpR1ANqMWqNA,"
                + "urn:li:digitalmediaMediaArtifactClass:aws-userUploadedVideo)"),
        value.getMediaArtifact());
    
    RegisterUpload.UploadMechanism uploadMechanism = value.getUploadMechanism();
    RegisterUpload.MediaUploadHttpRequest mediaUploadHttpRequest =
        uploadMechanism.getMediaUploadHttpRequest();
    Map<String, String> headers = mediaUploadHttpRequest.getHeaders();
    Assert.assertEquals(3, headers.size());
    Assert.assertEquals(headers.get("Content-Type"), "application/octet-stream");
    Assert.assertEquals(headers.get("x-amz-server-side-encryption"), "aws:kms");
    Assert.assertEquals(headers.get("x-amz-server-side-encryption-aws-kms-key-id"),
        "e10ace24-blah-4977-bar-89foo193e2ab");
    Assert.assertEquals(
        "https://video-uploads.s3-accelerate.amazonaws"
            + ".com/C5400AQHpR1ANqMWqNA/aws-userUploadedVideo?X-Amz-Algorithm=AWS4-HMAC-SHA256&X"
            + "-Amz-Date=20180120T000018Z&X-Amz-SignedHeaders=content-type%3Bhost%3Bx-amz-server"
            + "-side-encryption%3Bx-amz-server-side-encryption-aws-kms-key-id&X-Amz-Expires=86400"
            + "&X-Amz-Credential=AKIAJYU2MA%2F20180120%2Fus-east-1%2Fs3%2Faws4_request&X-Amz"
            + "-Signature=f7c0756a80998786766588878768778768977687d4c687b3f1a0e8",
        mediaUploadHttpRequest.getUploadUrl());
  }
  
}
