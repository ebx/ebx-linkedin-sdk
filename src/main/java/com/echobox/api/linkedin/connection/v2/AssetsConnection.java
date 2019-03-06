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

package com.echobox.api.linkedin.connection.v2;

import com.echobox.api.linkedin.client.BinaryAttachment;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.assets.CheckStatusUpload;
import com.echobox.api.linkedin.types.assets.CompleteMultiPartUploadBody;
import com.echobox.api.linkedin.types.assets.RegisterUpload;
import com.echobox.api.linkedin.types.assets.RegisterUploadRequestBody;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

/**
 * Assets connection
 */
public class AssetsConnection extends ConnectionBaseV2 {
  
  private static final String ASSETS = "/assets";

  public AssetsConnection(LinkedInClient linkedInClient) {
    super(linkedInClient);
  }
  
  public RegisterUpload registerUpload(RegisterUploadRequestBody registerUploadRequestBody) {
    return linkedinClient.publish(ASSETS, RegisterUpload.class, registerUploadRequestBody,
        Parameter.with("action", "registerUpload"));
  }
  
  public Map<String, String> uploadAsset(URL uploadURL, Map<String, String> headers,
      String filename, File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    return uploadAsset(uploadURL, headers, filename, bytes);
  }
  
  public Map<String, String> uploadAsset(URL uploadURL, Map<String, String> headers,
      String filename, byte[] bytes) throws IOException {
    return linkedinClient
        .publishForHeader(uploadURL.toString(), BinaryAttachment.with(filename, bytes), headers);
  }
  
  public void completeMultiPartUpload(CompleteMultiPartUploadBody completeMultiPartUploadBody) {
    linkedinClient.publish(ASSETS, Void.class, completeMultiPartUploadBody, Parameter.with(
        "action", "completeMultiPartUpload"));
  }
  
  public CheckStatusUpload checkStatusOfUpload(String digitalMediaAsset) {
    return linkedinClient.fetchObject(ASSETS + "/" + digitalMediaAsset, CheckStatusUpload.class);
  }

}
