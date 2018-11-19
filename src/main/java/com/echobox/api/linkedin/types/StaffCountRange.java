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
 * Staff count range enum
 * This can be used to determine the integer ranges of the staff count
 * @author joanna
 *
 */
@RequiredArgsConstructor
public enum StaffCountRange {
  
  /**
   * 1 staff
   */
  SIZE_1(null, 1),
  
  /**
   * 2 - 10 staff
   */
  SIZE_2_TO_10(2, 10),
  
  /**
   * 11 - 50 staff
   */
  SIZE_11_TO_50(11, 50),
  
  /**
   * 51 - 200 staff
   */
  SIZE_51_TO_200(51, 200),
  
  /**
   * 201 - 500 staff
   */
  SIZE_201_TO_500(201, 500),
  
  /**
   * 501 - 1000 staff
   */
  SIZE_501_TO_1000(501, 1000),
  
  /**
   * 1001 - 5000 staff
   */
  SIZE_1001_TO_5000(1001, 5000),
  
  /**
   * 5001 - 10000 staff
   */
  SIZE_5001_TO_10000(5001, 10000),
  
  /**
   * 10001+ staff
   */
  SIZE_10001_OR_MORE(10001, null);
  
  @Getter
  private final Integer min;
  
  @Getter
  private final Integer max;

}
