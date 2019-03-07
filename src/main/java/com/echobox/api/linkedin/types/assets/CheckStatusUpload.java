package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.List;

import lombok.Getter;

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
  
  public static class Recipe {
    @Getter
    @LinkedIn
    private URN recipe;
    @Getter
    @LinkedIn
    private String status;
  }
  
  public enum Status {
    ALLOWED,
    BLOCKED,
    ABANDONED,
    DELETED;
  }
  
}
