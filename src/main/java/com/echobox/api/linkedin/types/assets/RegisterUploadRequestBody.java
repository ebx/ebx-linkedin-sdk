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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Register Upload Request body
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/vector-asset-api#register-an-upload">Register an Upload</a>
 * @author Joanna
 */
@RequiredArgsConstructor
public class RegisterUploadRequestBody {
  
  @NonNull
  @Setter
  @Getter
  @LinkedIn
  private RegisterUploadRequest registerUploadRequest;
  
  private RegisterUploadRequestBody() {}
  
  /**
   * RegisterUploadRequest object
   * @author Joanna
   */
  @RequiredArgsConstructor
  public static class RegisterUploadRequest {
    /**
     * Owner of the video
     */
    @NonNull
    @Setter
    @Getter
    @LinkedIn
    private URN owner;
  
    /**
     * Supported upload mechanism
     */
    @Getter
    @Setter
    @LinkedIn
    private SupportedUploadMechanism supportedUploadMechanism;
  
    /**
     * Size in bytes
     */
    @Getter
    @Setter
    @LinkedIn
    private Long fileSize;
  
    /**
     * The value is an URN that identifies the use case for which the upload is requested
     */
    @Getter
    @LinkedIn
    private List<URN> recipes = new ArrayList<>();
  
    @Getter
    @Setter
    @LinkedIn
    private List<ServiceRelationships> serviceRelationships;
    
    private RegisterUploadRequest() {}
  
    /**
     * Set recipes
     * @param recipes the recipes
     */
    public void setRecipes(List<RecipeURN> recipes) {
      this.recipes = recipes.stream().map(RecipeURN ::getRecipeURN).collect(Collectors.toList());
    }
  }
  
  /**
   * ServiceRelationships object
   * @author Joanna
   */
  @RequiredArgsConstructor
  public static class ServiceRelationships {
    
    @Getter
    @NonNull
    @LinkedIn
    private String identifier;
  
    @Getter
    @NonNull
    @LinkedIn
    private RelationshipType relationshipType;
    
    private ServiceRelationships() {}
  
  }
  
  /**
   * The upload mechanism type
   * @author Joanna
   */
  public enum SupportedUploadMechanism {
    
    /**
     * Content filesize less than 200MB
     */
    SINGLE_REQUEST_UPLOAD,
  
    /**
     * Content filesize greater than 200MB
     */
    MULTIPART_UPLOAD;
  }
  
  /**
   * Recipe URN enum
   * @author Joanna
   */
  public enum RecipeURN {
    /**
     * The feed share video.
     */
    FEED_SHARE_VIDEO(new URN("urn:li:digitalmediaRecipe:feedshare-video")),
    /**
     * The learning image.
     */
    LEARNING_IMAGE(new URN("urn:li:digitalmediaRecipe:learning-image")),
    /**
     * The ads video.
     */
    ADS_VIDEO(new URN("urn:li:digitalmediaRecipe:ads-video")),
    /**
     * The feed share video
     */
    FEED_SHARE_IMAGE(new URN("urn:li:digitalmediaRecipe:feedshare-image")),
    /**
     * Company update article image
     */
    COMPANY_UPDATE_ARTICLE_IMAGE(new URN("urn:li:digitalmediaRecipe:companyUpdate-article-image")),
    /**
     * SSU carousel card image
     */
    SSU_CAROUSEL_CARD_IMAGE(new URN("urn:li:digitalmediaRecipe:ssu-carousel-card-image")),
    /**
     * Right rail logo image
     */
    RIGHT_RAIL_LOGO_IMAGE(new URN("urn:li:digitalmediaRecipe:rightRail-logo-image"));
    
    @Getter
    private URN recipeURN;

    /**
     * Instantiates new recipe URN
     * @param recipeURN the recipe URN
     */
    RecipeURN(URN recipeURN) {
      this.recipeURN = recipeURN;
    }
  }
  
}
