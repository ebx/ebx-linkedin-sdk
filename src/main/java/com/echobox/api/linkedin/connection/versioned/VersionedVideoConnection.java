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

package com.echobox.api.linkedin.connection.versioned;

import com.echobox.api.linkedin.client.BinaryAttachment;
import com.echobox.api.linkedin.client.DefaultVersionedLinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.VersionedLinkedInClient;
import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.videos.FinalizeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadResponse;
import com.echobox.api.linkedin.util.ValidationUtils;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Videos connection class to handle video operations
 *
 * @author sergio
 * @see
 * <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/videos-api">Videos API</a>
 */
public class VersionedVideoConnection extends VersionedConnection {
  
  /**
   * endpoint path
   */
  private static final String VIDEOS = "/videos";
  private static final String ACTION_KEY = "action";
  private static final String INITIALIZE_UPLOAD = "initializeUpload";
  private static final String FINALIZE_UPLOAD = "finalizeUpload";
  
  /**
   * Headers
   */
  private static final String HEADER_ETAG = "etag";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public VersionedVideoConnection(VersionedLinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  public URN uploadVideo(InitializeUploadRequest initializeUploadRequest, String filePath)
      throws IOException {
  
    Path videoFilePath = Paths.get(filePath);
    long fileSizeBytes = Files.size(videoFilePath);
    ValidationUtils.validateVideoFile(fileSizeBytes);
  
    initializeUploadRequest.getInitializeUploadRequest().setFileSizeBytes(fileSizeBytes);
    InitializeUploadResponse initializeUploadResponse = initializeUpload(initializeUploadRequest);
    InitializeUploadResponse.Value value = initializeUploadResponse.getValue();
    
    List<String> uploadedPartIds = uploadVideoFile(filePath, value.getUploadInstructions());
    
    FinalizeUploadRequest finalizeUploadRequest =
        new FinalizeUploadRequest(value.getVideo(), value.getUploadToken(), uploadedPartIds);
    finalizeUploadProd(finalizeUploadRequest);
    
    return value.getVideo();
  }
  
  public InitializeUploadResponse initializeUpload(
      InitializeUploadRequest initializeUploadRequest) {
    return linkedinClient.publish(VIDEOS, InitializeUploadResponse.class, initializeUploadRequest,
        Parameter.with(ACTION_KEY, INITIALIZE_UPLOAD));
  }
  
  public List<String> uploadVideoFile(String filePath,
      List<InitializeUploadResponse.UploadInstruction> uploadInstructions) throws IOException {
    
    File file = new File(filePath);
    byte[] fileBytes = convertToBytes(file);
    
    List<String> uploadPartIds = new ArrayList<>();
    for (InitializeUploadResponse.UploadInstruction instruction : uploadInstructions) {
      String etag = uploadVideoFileChunk(filePath, fileBytes, instruction);
      uploadPartIds.add(etag);
    }
    
    return uploadPartIds;
  }
  
  public String uploadVideoFileChunk(String filePath, byte[] fileBytes,
      InitializeUploadResponse.UploadInstruction instruction) throws IOException {
    WebRequestor webRequestor = linkedinClient.getWebRequestor();
    
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put(DefaultVersionedLinkedInClient.HEADER_NAME_VERSION,
        DefaultVersionedLinkedInClient.DEFAULT_VERSIONED_MONTH);
    
    byte[] chunkBytes = Arrays.copyOfRange(fileBytes,
        Math.toIntExact(instruction.getFirstByte()),
        Math.toIntExact(instruction.getLastByte() + 1));
    BinaryAttachment attachment = BinaryAttachment.with(filePath, chunkBytes,
        ContentType.APPLICATION_OCTET_STREAM.toString());
    
    URL url = new URL(instruction.getUploadUrl());
    WebRequestor.Response response =
        webRequestor.executePut(url.toString(), null, null, requestHeaders, attachment);
    Map<String, String> responseHeaders = response.getHeaders();
    ValidationUtils.validateRequiredResponseHeader(responseHeaders, HEADER_ETAG);
    
    return responseHeaders.get(HEADER_ETAG);
  }
  
  public void finalizeUploadProd(FinalizeUploadRequest finalizeUploadRequest) {
    linkedinClient.publish(VIDEOS, finalizeUploadRequest,
        Parameter.with(ACTION_KEY, FINALIZE_UPLOAD));
  }
  
  private static byte[] convertToBytes(File file) throws IOException {
    try (InputStream videoInputStream = Files.newInputStream(file.toPath())) {
      byte[] bytes = new byte[(int) file.length()];
      videoInputStream.read(bytes);
      return bytes;
    }
  }
}
