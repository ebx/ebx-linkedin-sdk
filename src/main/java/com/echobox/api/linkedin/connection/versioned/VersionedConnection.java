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

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.connection.ConnectionBase;
import com.echobox.api.linkedin.version.Version;

public abstract class VersionedConnection extends ConnectionBase {
  
  private static final String DEFAULT_VERSION = "202209";
  
  /**
   * The query key.
   */
  protected static final String QUERY_KEY = "q";
  
  /**
   * Versioning month
   */
  protected final String version;
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  protected VersionedConnection(LinkedInClient linkedinClient) {
    this(linkedinClient, DEFAULT_VERSION);
  }
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   * @param version versioning month
   */
  protected VersionedConnection(LinkedInClient linkedinClient, String version) {
    super(linkedinClient);
    if (Version.VERSIONING != linkedinClient.getVersion()) {
      throw new IllegalArgumentException("The linkedInClient should be VERSIONING");
    }
    this.version = version;
  }
  
  
}
