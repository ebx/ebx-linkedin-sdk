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

import java.util.List;

/**
 * Share content POJO
 * @author joanna
 *
 */
public class ShareContent {
  
  /**
   * Content description
   * This field is displayed to a small percentage of users on the mobile web version of our site
   * It is not displayed on the desktop site or native mobile apps
   * Max 256 characters
   */
  @Getter
  private String description;
  
  /**
   * Content title
   * Max 400 characters, recommended length is <70 characters
   */
  @Getter
  private String title;
  
  /**
   * Details of content being shared
   */
  @Getter
  private List<ContentEntity> contentEntities;
  
  /**
   * The type of media represented by the contentEntities
   */
  @Getter
  private String shareMediaCategory;

}
