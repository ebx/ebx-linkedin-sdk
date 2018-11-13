
package com.echobox.api.linkedin.version;

import com.echobox.api.linkedin.util.URLUtils;

import org.json.JSONObject;

import lombok.Getter;

public abstract class PagingStrategy {

  @Getter
  protected String previousPageUrl;

  @Getter
  protected String nextPageUrl;

  public abstract void discoverPages(JSONObject jsonObject, String fullEndpoint);

  protected void setNextPageURL(String fullEndpoint, int start, int count) {
    nextPageUrl = createPagedURL(fullEndpoint, start + count, count);
  }

  protected void setPreviousPageURL(String fullEndpoint, int start, int count) {
    if (start > 0) {
      // There's a previous page
      previousPageUrl = createPagedURL(fullEndpoint, start - count, count);
    }
  }
  
  private String createPagedURL(String fullEndpoint, int start, int count) {
    fullEndpoint = URLUtils.replaceOrAddQueryParameter(fullEndpoint, "start", Integer.toString(start));
    fullEndpoint = URLUtils.replaceOrAddQueryParameter(fullEndpoint, "count", Integer.toString(count));
    return fullEndpoint;
  }

}
