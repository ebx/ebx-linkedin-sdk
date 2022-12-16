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
import com.echobox.api.linkedin.connection.ConnectionBase;
import com.echobox.api.linkedin.version.Version;

/**
 * Base class for all connections using versioned API
 * @author Kenneth Wong
 */
public abstract class VersionedConnection extends ConnectionBase {
  
  /**
   * The query key.
   */
  protected static final String QUERY_KEY = "q";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  protected VersionedConnection(VersionedLinkedInClient linkedinClient) {
    super(linkedinClient);
    if (Version.VERSIONED != linkedinClient.getVersion()) {
      throw new IllegalArgumentException("The version of linkedInClient is not VERSIONED");
    }
  }
}
