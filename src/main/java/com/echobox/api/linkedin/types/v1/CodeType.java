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

import org.apache.commons.lang3.text.WordUtils;

/**
 * Code type interface
 * @author Joanna
 *
 * @param <T> type
 */
public interface CodeType<T> {
  
  /**
   * Get the code
   * @return code
   */
  T getCode();
  
  /**
   * Get the name
   * @return name
   */
  default String getName() {
    String name = toString();
    return WordUtils.capitalize(name.replaceAll("_", " ").toLowerCase());
  }

}
