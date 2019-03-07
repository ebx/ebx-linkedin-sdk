package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.Map;

import lombok.Getter;

public class RegisterUpload {
  
  @Getter
  @LinkedIn
  private RegisterUploadValue value;
  
  public static class RegisterUploadValue {
    @Getter
    @LinkedIn
    private URN asset;
  
    /**
     * TODO: this is a nested URN...
     */
    @Getter
    @LinkedIn
    private String mediaArtifact;
  
    @Getter
    @LinkedIn
    private UploadMechanism uploadMechanism;
  }
  
  public static class UploadMechanism {
    @Getter
    @LinkedIn("com.linkedin.digitalmedia.uploading.MediaUploadHttpRequest")
    private MediaUploadHttpRequest mediaUploadHttpRequest;
  }
  
  public static class MediaUploadHttpRequest {
    @Getter
    @LinkedIn
    private Map<String, String> headers;
  
    @Getter
    @LinkedIn
    private String uploadUrl;
  }

}
