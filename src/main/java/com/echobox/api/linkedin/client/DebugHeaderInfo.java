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

package com.echobox.api.linkedin.client;

import lombok.Getter;

/**
 * Debug header info
 * @author Joanna
 *
 */
public class DebugHeaderInfo {
  
  /**
   * x-li-fabric
   */
  @Getter
  private final String fabric;

  /**
   * x-li-format
   */
  @Getter
  private final String format;

  /**
   * x-li-request-id
   */
  @Getter
  private final String requestId;
  
  /**
   * x-li-uuid
   */
  @Getter
  private final String uuid;

  /**
   * Initialise the debug header info
   * @param fabic x-li-fabric header
   * @param format x-li-format header
   * @param requestId x-li-request-id request id
   * @param uuid x-li-uuid header
   */
  public DebugHeaderInfo(String fabic, String format, String requestId, String uuid) {
    this.fabric = fabic;
    this.format = format;
    this.requestId = requestId;
    this.uuid = uuid;
  }

}
