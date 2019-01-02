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

package com.echobox.api.linkedin.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Valiation utility class
 * @author Joanna
 *
 */
public class ValidationUtils {
  

  /**
   * Ensures that {@code parameter} isn't {@code null} or an empty string.
   * 
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws IllegalArgumentException
   *           If {@code parameter} is {@code null} or an empty string.
   */
  public static void verifyParameterPresence(String parameterName, String parameter) {
    verifyParameterPresence(parameterName, (Object) parameter);
    if (StringUtils.isBlank(parameter)) {
      throw new IllegalArgumentException("The '" + parameterName
          + "' parameter cannot be an empty string.");
    }
  }

  /**
   * Ensures that {@code parameter} isn't {@code null}.
   * 
   * @param parameterName
   *          The name of the parameter (to be used in exception message).
   * @param parameter
   *          The parameter to check.
   * @throws NullPointerException
   *           If {@code parameter} is {@code null}.
   */
  public static void verifyParameterPresence(String parameterName, Object parameter) {
    if (parameter == null) {
      throw new NullPointerException("The '" + parameterName + "' parameter cannot be null.");
    }
  }

}
