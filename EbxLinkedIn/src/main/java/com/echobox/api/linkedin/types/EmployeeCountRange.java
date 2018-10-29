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

/**
 * EmployeeCountRange
 * @author Joanna
 *
 */
@RequiredArgsConstructor
public enum EmployeeCountRange implements CodeType<String> {
  
  XXXS("A", 0, 1),
  XXS("B", 2, 10),
  XS("C", 11, 50),
  S("D", 51, 200),
  M("E", 201, 500),
  L("F", 501, 1000),
  XL("G", 1001, 5000),
  XXL("H", 5001, 10000),
  XXXL("I", 10001, null);
  
  @Getter
  private final String code;
  
  @Getter
  public final Integer min;
  
  @Getter
  public final Integer max;

}
