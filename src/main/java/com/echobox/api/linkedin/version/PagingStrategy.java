package com.echobox.api.linkedin.version;

import org.json.JSONObject;

import lombok.Getter;

public abstract class PagingStrategy {
  
  @Getter
  protected String previousPageUrl;
  
  @Getter
  protected String nextPageUrl;
  
  public abstract void discoverPages(JSONObject jsonObject, String fullEndpoint);
  
}
