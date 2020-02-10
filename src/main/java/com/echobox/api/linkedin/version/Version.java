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

package com.echobox.api.linkedin.version;

import com.echobox.api.linkedin.client.paging.PagingStrategy;
import com.echobox.api.linkedin.client.paging.V1PagingImpl;
import com.echobox.api.linkedin.client.paging.V2PagingImpl;
import com.echobox.api.linkedin.logging.LinkedInLogger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

/**
 * The LinkedIn API version
 * This enum should contain all current versions of the LinkedIn API
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum Version {
  
  /**
   * LinkedIn V1 API version
   */
  V1("v1", new V1PagingImpl(), true),
  
  /**
   * LinkedIn V2 API version
   */
  V2("v2", new V2PagingImpl(), false);
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  /**
   * The default version - should be the most up to date version of LinkedIn API
   */
  public static final Version DEFAULT_VERSION = V2;
  
  @Getter
  private final String urlElement;
  
  @Getter
  private final PagingStrategy pagingStrategy;
  
  @Getter
  private final boolean specifyFormat;
  
  /**
   * Convert the provided URL element into a version
   *
   * @param urlElement the URL element
   * @return if successful the desired Version otherwise will default to the default version
   */
  public static Version getVersionFromString(String urlElement) {
    for (Version version : Version.values()) {
      if (version.getUrlElement().equals(urlElement)) {
        return version;
      }
    }
    LOGGER.warn("Could not get company type from code " + urlElement + ", defaulting to "
        + DEFAULT_VERSION);
    return DEFAULT_VERSION;
  }

}
