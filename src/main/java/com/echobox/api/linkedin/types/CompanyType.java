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

import org.apache.commons.lang3.text.WordUtils;

/**
 * Company type
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum CompanyType implements CodeType<String> {
  
  PUBLIC_COMPANY("C"),
  EDUCATIONAL("D"),
  SELF_EMPLOYED("E"),
  GOVERNMENT_AGENCY("G"),
  NON_PROFIT("N"),
  SELF_OWNED("O"),
  PRIVATELY_HELD("P"),
  PARTNERSHIP("S");
  
  @Getter
  private final String code;
  
  @Override
  public String getName() {
    String name = toString();
    return WordUtils.capitalize(name.replaceAll("_", " ").toLowerCase());
  }
  
  /**
   * Convert the provided code into a company type
   *
   * @param code the code
   * @return if successful the desired company type otherwise null
   */
  public static CompanyType fromCode(String code) {
    for (CompanyType companyType : CompanyType.values()) {
      if (companyType.getCode().equals(code)) {
        return companyType;
      }
    }
    return null;
  }

}
