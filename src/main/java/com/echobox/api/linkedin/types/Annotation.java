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

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.Getter;
import lombok.Setter;

/**
 * Annotation POJO
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/shares/share-api#share-text">
 * Annotations</a>
 * @author joanna
 *
 */
public class Annotation {
  
  /**
   * The starting character index beginning the annotation link
   * Zero-based indexing
   */
  @Getter
  @Setter
  @LinkedIn
  private Integer start;
  
  /**
   * The length of the annotation link
   */
  @Getter
  @Setter
  @LinkedIn
  private Integer length;
  
  /**
   * The entity referred to in the share text.
   * Must either be in the format urn:li:person:{id} or urn:li:company:{id}
   */
  @Getter
  @Setter
  @LinkedIn
  private String entity;

}
