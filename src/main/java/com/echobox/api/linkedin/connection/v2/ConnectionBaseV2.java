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

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.connection.ConnectionBase;
import com.echobox.api.linkedin.version.Version;

/**
 * Connection base for all V2 connections
 * @author joanna
 */
public class ConnectionBaseV2 extends ConnectionBase {
  
  protected ConnectionBaseV2(LinkedInClient linkedinClient) {
    super(linkedinClient);
    if (!Version.V2.equals(linkedinClient.getVersion())) {
      throw new IllegalStateException(
          "The LinkedIn clinet should be set to V2 to access the endpoints");
    }
  }
}
