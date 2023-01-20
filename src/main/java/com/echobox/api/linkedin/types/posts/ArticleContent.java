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

package com.echobox.api.linkedin.types.posts;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

/**
 * ArticleContent object
 *
 * @author Sergio Abplanalp
 */
public class ArticleContent {
  
  /**
   * Custom or saved description of the article. If empty, there is none.
   * The length must be less than 4086 characters.
   */
  @Getter
  @Setter
  @LinkedIn
  private String description;
  
  /**
   * A URL of the article. Typically the URL that was ingested to maintain URL parameters.
   */
  @Getter
  @Setter
  @LinkedIn
  private String source;
  
  /**
   * Custom or saved thumbnail for the article.
   */
  @Getter
  @Setter
  @LinkedIn
  private URN thumbnail;
  
  /**
   * Alt text for the custom thumbnail.
   */
  @Getter
  @Setter
  @LinkedIn
  private String thumbnailAltText;
  
  /**
   * Custom or saved title of the article.
   */
  @Getter
  @Setter
  @LinkedIn
  private String title;
}
