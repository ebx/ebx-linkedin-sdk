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

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.types.images.ImageDetails;
import com.echobox.api.linkedin.types.images.InitializeUpload;
import com.echobox.api.linkedin.types.images.InitializeUploadRequestBody;
import com.echobox.api.linkedin.types.urn.URN;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Images connection class to handle image operations
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api">Images API</a>
 *
 * @author Sergio Abplanalp
 *
 */
public class ImageConnection extends Connection {
  
  /**
   * endpoint path
   */
  private static final String IMAGES = "/images";
  private static final String ACTION_KEY = "action";
  private static final String INITIALIZE_UPLOAD = "initializeUpload";

  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public ImageConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Upload an image
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#initialize-image-upload">Upload an Image</a>
   * @param initializeUploadRequestBody the initialize upload request body
   * @param filename the file name
   * @param file the file to upload as an image
   * @return the digital asset URN
   * @throws IOException IOException
   */
  public URN uploadImage(InitializeUploadRequestBody initializeUploadRequestBody,
      String filename, File file) throws IOException {
    try (InputStream imageInputStream = Files.newInputStream(file.toPath())) {
      byte[] bytes = new byte[(int) file.length()];
      imageInputStream.read(bytes);
      InitializeUpload initializeUpload = uploadImage(initializeUploadRequestBody, filename, bytes);
      return initializeUpload.getValue().getImage();
    }
  }
  
  /**
   * Upload an image
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#initialize-image-upload">Upload an Image</a>
   * @param initializeUploadRequestBody the initialize upload request body
   * @param filename the file name
   * @param bytes the image bytes to upload as an image
   * @return the upload response
   * @throws MalformedURLException MalformedURLException
   */
  public InitializeUpload uploadImage(InitializeUploadRequestBody initializeUploadRequestBody,
      String filename, byte[] bytes) throws MalformedURLException {
    // Initialize the file upload
    InitializeUpload initializeUploadResponse = initializeUpload(initializeUploadRequestBody);
    
    // Upload the image
    UploadHelper.uploadImageBytes(linkedinClient.getWebRequestor(),
        new URL(initializeUploadResponse.getValue().getUploadUrl()),
        new HashMap<>(), filename, bytes);
    
    return initializeUploadResponse;
  }
  
  /**
   * Initialize an upload to declare the upcoming upload
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#initialize-image-upload">Initialize an Upload</a>
   * @param initializeUploadRequestBody the initialize upload request body
   * @return the initialize upload response
   */
  public InitializeUpload initializeUpload(
      InitializeUploadRequestBody initializeUploadRequestBody) {
    return linkedinClient.publish(IMAGES, InitializeUpload.class, initializeUploadRequestBody,
        Parameter.with(ACTION_KEY, INITIALIZE_UPLOAD));
  }
  
  /**
   * Upload the image file
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#uploading-an-image">Upload the Image</a>
   * @param webRequestor the web requestor - Note that it must not have any existing authorization
   * tokens
   * @param uploadURL the uploadUrl from the initialize upload response
   * @param headers the headers from the initialize upload response
   * @param filename the file name of the file to be uploaded
   * @param file the file to upload
   * @return the map of headers from the request
   * @throws IOException IOException
   */
  public Map<String, String> uploadImageFile(WebRequestor webRequestor, URL uploadURL,
      Map<String, String> headers, String filename, File file) throws IOException {
    byte[] bytes = UploadHelper.convertFileToBytes(file);
    return UploadHelper.uploadImageBytes(webRequestor, uploadURL, headers, filename, bytes);
  }
  
  /**
   * Retrieve the details of an image using the image URN
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#get-a-single-image">GET a single image</a>
   * @param imageURN the image URN to retrieve the details of
   * @return the image details
   */
  public ImageDetails retrieveImageDetails(URN imageURN) {
    return linkedinClient.fetchObject(IMAGES + "/" + imageURN.toString(),
        ImageDetails.class);
  }
}
