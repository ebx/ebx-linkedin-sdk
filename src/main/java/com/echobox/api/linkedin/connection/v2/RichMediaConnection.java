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
import com.echobox.api.linkedin.types.RichMediaLocation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Rich media connection
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
 * community-management/shares/rich-media-shares">Rich Media Shares</a>
 * @author joanna
 */
public class RichMediaConnection extends ConnectionBaseV2 {
  
  /**
   * Initialise RichMediaConnection
   * @param linkedInClient the LinkedIn client
   */
  public RichMediaConnection(LinkedInClient linkedInClient) {
    super(linkedInClient);
  }
  
  /**
   * Upload a file to the rich media API
   * @param filename the file name
   * @param file the file to upload
   * @return the location of the of the rich media upload
   * @throws IOException IOException
   */
  public RichMediaLocation uploadRichMedia(String filename, File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    return linkedinClient.publish("", RichMediaLocation.class, null,
        BinaryAttachment.with(filename, array));
  }
  
}
