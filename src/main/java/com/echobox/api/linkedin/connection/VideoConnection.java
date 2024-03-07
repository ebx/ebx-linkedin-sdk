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

package com.echobox.api.linkedin.connection;

import com.echobox.api.linkedin.client.BinaryAttachment;
import com.echobox.api.linkedin.client.DefaultLinkedInClient;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.videos.FinalizeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadRequest;
import com.echobox.api.linkedin.types.videos.InitializeUploadResponse;
import com.echobox.api.linkedin.types.videos.VideoDetails;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.IOException;
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
public class VideoConnection extends Connection {
  
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
  public VideoConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  public URN uploadVideoFromURL(InitializeUploadRequest initializeUploadRequest, String videoURL,
      String thumbnailImageURL) throws IOException {
  
    URL url = UploadHelper.extractUploadURL(videoURL);
    byte[] fileBytes = UploadHelper.convertURLToBytes(url);
    long videoFileSizeBytes = fileBytes.length;
  
    return getVideoURN(videoFileSizeBytes, initializeUploadRequest, videoURL, fileBytes,
        thumbnailImageURL);
  }
  
  public URN uploadVideoFromFile(InitializeUploadRequest initializeUploadRequest, String filePath,
      String thumbnailImageURL) throws IOException {
  
    File file = new File(filePath);
    Path videoFilePath = Paths.get(filePath);
    byte[] fileBytes = UploadHelper.convertFileToBytes(file);
    long videoFileSizeBytes = Files.size(videoFilePath);
    
    return getVideoURN(videoFileSizeBytes, initializeUploadRequest, filePath,
        fileBytes, thumbnailImageURL);
  }
  
  private URN getVideoURN(long videoFileSizeBytes, InitializeUploadRequest initializeUploadRequest,
      String videoLocation, byte[] fileBytes, String thumbnailImageURL) throws IOException {
  
    initializeUploadRequest.getInitializeUploadRequest().setFileSizeBytes(videoFileSizeBytes);
    
    InitializeUploadResponse initializeUploadResponse = initializeUpload(initializeUploadRequest);
    InitializeUploadResponse.Value value = initializeUploadResponse.getValue();
  
    List<String> uploadedPartIds = new ArrayList<>();
    for (InitializeUploadResponse.UploadInstruction instruction : value.getUploadInstructions()) {
      String etag = uploadVideoFileChunk(videoLocation, fileBytes, instruction);
      uploadedPartIds.add(etag);
    }
  
    uploadThumbnailImage(thumbnailImageURL, value);
  
    FinalizeUploadRequest finalizeUploadRequest =
        new FinalizeUploadRequest(value.getVideo(), value.getUploadToken(), uploadedPartIds);
    finalizeUpload(finalizeUploadRequest);
  
    return value.getVideo();
  }
  
  public InitializeUploadResponse initializeUpload(
      InitializeUploadRequest initializeUploadRequest) {
    ValidationUtils.validateVideoFileSize(
        initializeUploadRequest.getInitializeUploadRequest().getFileSizeBytes());
    return linkedinClient.publish(VIDEOS, InitializeUploadResponse.class, initializeUploadRequest,
        Parameter.with(ACTION_KEY, INITIALIZE_UPLOAD));
  }
  
  public String uploadVideoFileChunk(String filePath, byte[] fileBytes,
      InitializeUploadResponse.UploadInstruction instruction) throws IOException {
    WebRequestor webRequestor = linkedinClient.getWebRequestor();
    
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put(DefaultLinkedInClient.HEADER_NAME_VERSION,
        linkedinClient.getVersionedMonth());
    
    byte[] chunkBytes = Arrays.copyOfRange(fileBytes,
        Math.toIntExact(instruction.getFirstByte()),
        Math.toIntExact(instruction.getLastByte() + 1));
    BinaryAttachment attachment = BinaryAttachment.with(filePath, chunkBytes,
        ContentType.APPLICATION_OCTET_STREAM.toString());
    
    URL url = UploadHelper.extractUploadURL(instruction.getUploadUrl());
    WebRequestor.Response response =
        webRequestor.executePut(url.toString(), null, null, requestHeaders, attachment);
    Map<String, String> responseHeaders = response.getHeaders();
    ValidationUtils.validateRequiredResponseHeader(responseHeaders, HEADER_ETAG);
    
    return responseHeaders.get(HEADER_ETAG);
  }
  
  public void finalizeUpload(FinalizeUploadRequest finalizeUploadRequest) {
    linkedinClient.publish(VIDEOS, finalizeUploadRequest,
        Parameter.with(ACTION_KEY, FINALIZE_UPLOAD));
  }
  
  private Map<String, String> uploadThumbnailImage(String thumbnailImageURL,
      InitializeUploadResponse.Value initializeUploadResponseValue) throws IOException {
    
    Map<String, String> responseHeaders = new HashMap<>();
    
    if (StringUtils.isNotEmpty(thumbnailImageURL)) {
      byte[] bytes =
          UploadHelper.convertURLToBytes(UploadHelper.extractUploadURL(thumbnailImageURL));
      String thumbnailUploadUrl = initializeUploadResponseValue.getThumbnailUploadUrl();
      if (StringUtils.isNotEmpty(thumbnailUploadUrl)) {
        responseHeaders = UploadHelper.uploadImageBytes(linkedinClient.getWebRequestor(),
            new URL(thumbnailUploadUrl), new HashMap<>(), thumbnailImageURL, bytes);
      }
    }
    
    return responseHeaders;
  }

  public VideoDetails retrieveVideoDetails(URN videoURN) {
    return linkedinClient.fetchObject(
        VIDEOS + "/" + URLUtils.urlEncode(videoURN.toString()), VideoDetails.class);
  }
}
