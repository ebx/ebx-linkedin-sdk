package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class RegisterUploadRequestBody {
  
  @NonNull
  @Setter
  @Getter
  @LinkedIn
  private RegisterUploadRequest registerUploadRequest;
  
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
    private supportedUploadMechanism supportedUploadMechanism;
  
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
  
    public void setReciepies(List<RecipeURN> recipes) {
      this.recipes = recipes.stream().map(RecipeURN ::getRecipeURN).collect(Collectors.toList());
    }
  }
  
  public static class ServiceRelationships {
    
    @Getter
    @LinkedIn
    private String identifier;
  
    @Getter
    @LinkedIn
    private RelationshipType relationshipType;
  
    public ServiceRelationships(String identifier, RelationshipType relationshipType) {
      this.identifier = identifier;
      this.relationshipType = relationshipType;
    }
  
  }
  
  /**
   * The upload mechanism type
   * @author Joanna
   */
  public enum supportedUploadMechanism {
    
    /**
     *  Content filesize < 200MB
     */
    SINGLE_REQUEST_UPLOAD,
  
    /**
     * Content filesize greater than 200MB
     */
    MULTIPART_UPLOAD;
  }
  
  public enum RecipeURN {
    FEED_SHARE_VIDEO(new URN("urn:li:digitalmediaRecipe:feedshare-video")),
    LEARNING_IMAGE(new URN("urn:li:digitalmediaRecipe:feedshare-video")),
    ADS_VIDEO(new URN("urn:li:digitalmediaRecipe:feedshare-video"));
    
    @Getter
    private URN recipeURN;
    
    RecipeURN(URN recipeURN) {
      this.recipeURN = recipeURN;
    }
  }
  
}
