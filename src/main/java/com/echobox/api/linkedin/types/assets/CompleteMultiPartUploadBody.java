package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class CompleteMultiPartUploadBody {
  
  @NonNull
  @Getter
  @Setter
  @LinkedIn
  private CompleteMultiPartUpload completeMultipartUploadRequest;
  
  public static class CompleteMultiPartUpload {
  
    @Getter
    @Setter
    @LinkedIn
    private String mediaArtifact;
  
    @Getter
    @Setter
    @LinkedIn
    private String metaData;
  
    @Getter
    @Setter
    @LinkedIn
    private List<PartUploadResponses> partUploadResponses;
  }
  
  public static class PartUploadResponses {
  
    @Getter
    @Setter
    @LinkedIn
    private Headers headers;
  
    @Getter
    @Setter
    @LinkedIn
    public Integer httpStatusCode;
  }
  
  @RequiredArgsConstructor
  public static class Headers {
    
    @Getter
    @Setter
    @NonNull
    @LinkedIn("ETag")
    private String eTag;
  }
  
}
