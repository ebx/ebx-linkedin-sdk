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

package com.echobox.api.linkedin.types.v1;

import com.echobox.api.linkedin.logging.LinkedInLogger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

/**
 * Status type
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum StatusType implements CodeType<String> {

  /**
   * Operating status type.
   */
  OPERATING("OPR"),
  /**
   * Operating subsidiary status type.
   */
  OPERATING_SUBSIDIARY("OPS"),
  /**
   * Reorganising status type.
   */
  REORGANISING("RRG"),
  /**
   * Out of business status type.
   */
  OUT_OF_BUSINESS("OOB"),
  /**
   * Acquired status type.
   */
  ACQUIRED("ACQ");
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  @Getter
  private final String code;
  
  /**
   * Convert the provided code into a status type
   *
   * @param code the code
   * @return if successful the desired status type otherwise null
   */
  public static StatusType fromCode(String code) {
    for (StatusType statusType : StatusType.values()) {
      if (statusType.getCode().equals(code)) {
        return statusType;
      }
    }
    LOGGER.warn("Could not get status type from code " + code);
    return null;
  }

}
