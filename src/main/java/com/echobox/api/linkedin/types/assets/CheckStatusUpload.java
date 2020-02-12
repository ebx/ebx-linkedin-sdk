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

package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;

import java.util.List;

/**
 * Check status upload
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/vector-asset-api#check-status-of-upload">Check Status of Upload</a>
 * @author Joanna
 */
public class CheckStatusUpload {

  @Getter
  @LinkedIn
  private Long created;
  
  @Getter
  @LinkedIn
  private String id;
  
  @Getter
  @LinkedIn
  private Long lastModified;
  
  @Getter
  @LinkedIn
  private String mediaTypeFamily;
  
  @Getter
  @LinkedIn
  private List<Recipe> recipes;
  
  @Getter
  @LinkedIn
  private List<RegisterUploadRequestBody.ServiceRelationships> serviceRelationships;
  
  @Getter
  @LinkedIn
  private String status;
  
  /**
   * Recipe object
   * @author Joanna
   */
  public static class Recipe {
    @Getter
    @LinkedIn
    private URN recipe;
    @Getter
    @LinkedIn
    private String status;
  }
  
  /**
   * The status enum
   * @author Joanna
   */
  public enum Status {
  
    /**
     * The content is allowed to be served.
     */
    ALLOWED,
  
    /**
     * The content must not be served.
     */
    BLOCKED,
  
    /**
     * The asset was abandoned by its owner.
     */
    ABANDONED,
  
    /**
     * The asset was fully deleted and cannot be processed again.
     */
    DELETED;
  }
  
}
