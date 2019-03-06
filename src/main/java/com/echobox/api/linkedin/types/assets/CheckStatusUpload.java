package com.echobox.api.linkedin.types.assets;

import java.util.List;

public class CheckStatusUpload {
  
  private Long created;
  
  private String id;
  
  private Long lastModified;
  
  private String mediaTypeFamily;
  
  private List<RegisterUploadRequestBody.RecipeURN> recipies;
  
  private List<RegisterUploadRequestBody.ServiceRelationships> serviceRelationships;
  
  private String status;
  
  public enum Status {
    ALLOWED,
    BLOCKED,
    ABANDONED,
    DELETED;
  }
  
}
