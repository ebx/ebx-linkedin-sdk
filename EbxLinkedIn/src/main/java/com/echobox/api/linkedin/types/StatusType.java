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

package com.echobox.api.linkedin.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.echobox.api.linkedin.logging.LinkedInLogger.TYPE_LOGGER;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Status type
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum StatusType implements CodeType<String> {
  
  OPERATING("OPR"),
  OPERATING_SUBSIDIARY("OPS"),
  REORGANISING("RRG"),
  OUT_OF_BUSINESS("OOB"),
  ACQUIRED("ACQ");
  
  @Getter
  private final String code;
  
  @Override
  public String getName() {
    String name = toString();
    return WordUtils.capitalize(name.replaceAll("_", " ").toLowerCase());
  }
  
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
    TYPE_LOGGER.warn("Could not get status type from code " + code);
    return null;
  }

}
