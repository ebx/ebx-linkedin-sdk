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
 * EmployeeCountRange
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum EmployeeCountRange implements CodeType<String> {

  /**
   * XXXS employee count range.
   */
  XXXS("A", "1", null, 1),
  /**
   * XXS employee count range.
   */
  XXS("B", "2-10", 2, 10),
  /**
   * XS employee count range.
   */
  XS("C", "11-50", 11, 50),
  /**
   * S employee count range.
   */
  S("D", "51-200", 51, 200),
  /**
   * M employee count range.
   */
  M("E", "201-500", 201, 500),
  /**
   * L employee count range.
   */
  L("F", "501-1000", 501, 1000),
  /**
   * XL employee count range.
   */
  XL("G", "1001-5000", 1001, 5000),
  /**
   * XXL employee count range.
   */
  XXL("H", "5001-10,000", 5001, 10000),
  /**
   * XXXL employee count range.
   */
  XXXL("I", "10,000+", 10001, null);
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  @Getter
  private final String code;
  
  @Getter
  private final String name;

  /**
   * The minimum employee count range.
   */
  @Getter
  public final Integer min;

  /**
   * The maximum employee count range.
   */
  @Getter
  public final Integer max;
  
  /**
   * Convert the provided code into a company type
   *
   * @param code the code
   * @return if successful the desired company type otherwise null
   */
  public static EmployeeCountRange fromCode(String code) {
    for (EmployeeCountRange employeeCountRange : EmployeeCountRange.values()) {
      if (employeeCountRange.getCode().equals(code)) {
        return employeeCountRange;
      }
    }
    LOGGER.warn("Could not get employee count range from code " + code);
    return null;
  }

}
