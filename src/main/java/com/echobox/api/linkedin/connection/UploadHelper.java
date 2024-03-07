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
import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.exception.LinkedInNetworkException;
import com.echobox.api.linkedin.exception.LinkedInResponseException;
import com.echobox.api.linkedin.util.ValidationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

/**
 * Helper class for uploading files to LinkedIn
 * @author Rado S
 */
public class UploadHelper {
  
  /**
   * Upload the image bytes, this should be used to upload each image chunk
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/images-api#uploading-an-image">Upload the Image</a>
   * @param webRequestor the web requestor - Note that it must not have any existing authorization
   * tokens
   * @param uploadURL the uploadUrl from the initialize upload response
   * @param headers the headers from the initialize upload response
   * @param filename the file name of the file to be uploaded
   * @param bytes the bytes to upload
   * @return the map of headers from the request
   */
  public static Map<String, String> uploadImageBytes(WebRequestor webRequestor, URL uploadURL,
      Map<String, String> headers, String filename, byte[] bytes) {
    WebRequestor.Response response;
    try {
      response = webRequestor.executePut(uploadURL.toString(), null, null, headers,
          BinaryAttachment.with(filename, bytes));
    } catch (Exception ex) {
      throw new LinkedInNetworkException("LinkedIn request failed to upload the image", ex);
    }
    
    ValidationUtils.validateResponse(response);
    
    return response.getHeaders();
  }
  
  public static byte[] convertFileToBytes(File file) throws IOException {
    try (InputStream videoInputStream = Files.newInputStream(file.toPath())) {
      byte[] bytes = new byte[(int) file.length()];
      videoInputStream.read(bytes);
      return bytes;
    }
  }
  
  public static byte[] convertURLToBytes(URL url) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (InputStream inputStream = url.openStream()) {
      
      int byteChunk;
      
      while ((byteChunk = inputStream.read()) != -1) {
        baos.write(byteChunk);
      }
    } catch (IOException e) {
      throw new IOException(
          String.format("Failed while reading bytes from %s: %s", url.toExternalForm(),
              e.getMessage()));
    }
    
    return baos.toByteArray();
  }
  
  public static URL extractUploadURL(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new LinkedInResponseException("Invalid upload url returned from LinkedIn.", e);
    }
  }
}
