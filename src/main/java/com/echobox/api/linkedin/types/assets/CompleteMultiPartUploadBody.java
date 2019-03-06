package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import java.util.List;

public class CompleteMultiPartUploadBody {
  
  private String mediaArtifact;
  
  private String metaData;
  
  private List<PartUploadResponses> partUploadResponses;
  
  public static class PartUploadResponses {
    private Headers headers;
    public Integer httpStatusCode;
  }
  
  public static class Headers {
    @LinkedIn("ETag")
    private String eTag;
  }
  
}
