package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class RegisterUploadRequestBody {
  
  /**
   * Owner of the video
   */
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
  
  public void setReciepies(List<Recipe> recipes) {
    this.recipes = recipes.stream().map(Recipe::getRecipeURN).collect(Collectors.toList());
  }
  
  public static class ServiceRelationships {
  
    @Setter
    @Getter
    @LinkedIn
    private URN identifier;
    
    @Getter
    @LinkedIn
    private String relationshipType;
    
    public void setRelationshipType(RelationshipType relationshipType) {
      this.relationshipType = relationshipType.name();
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
  
  public enum Recipe {
    FEED_SHARE_VIDEO(new URN("urn:li:digitalmediaRecipe:feedshare-video")),
    LEARNING_IMAGE(new URN("urn:li:digitalmediaRecipe:feedshare-video")),
    ADS_VIDEO(new URN("urn:li:digitalmediaRecipe:feedshare-video"));
    
    @Getter
    private URN recipeURN;
    
    Recipe(URN recipeURN) {
      this.recipeURN = recipeURN;
    }
  }
  
}
