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

import com.echobox.api.linkedin.client.VersionedLinkedInClient;

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
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  protected VersionedVideoConnection(VersionedLinkedInClient linkedinClient) {
    super(linkedinClient);
  }
}
