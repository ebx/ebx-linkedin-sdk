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

package com.echobox.api.linkedin.types.objectype;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Locale POJO
 * @see <a href="https://developer.linkedin.com/docs/ref/v2/object-types#Locale">
 * Locale</a>
 * @author joanna
 *
 */
@EqualsAndHashCode
public class Locale implements Serializable {
  
  private static final long serialVersionUID = -1L;

  /**
   * An uppercase two-letter country code as defined by ISO-3166.
   */
  @Getter
  @Setter
  @LinkedIn
  private String country;
  
  /**
   * A lowercase two-letter language code as defined by ISO-639.
   */
  @Getter
  @Setter
  @LinkedIn
  private String language;
  
  @Override
  public String toString() {
    return String.format("%s_%s", language, country);
  }
  
}
