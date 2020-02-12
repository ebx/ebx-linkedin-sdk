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

package com.echobox.api.linkedin.client.paging;

import com.echobox.api.linkedin.util.URLUtils;
import com.eclipsesource.json.JsonObject;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Discover and build the previous and next page URL if paging is available in the JSON object
 * @author Joanna
 *
 */
public abstract class PagingStrategy {

  /**
   * The previous page URL
   */
  @Getter
  protected String previousPageUrl;

  /**
   * The next page URL
   */
  @Getter
  protected String nextPageUrl;

  /**
   * Discover the paging URLs for the given JSON object and build it for the full endpoint
   * @param jsonObject The JSON object to discover the paging
   * @param fullEndpoint The full endpoint to build the next page URL
   */
  public void populatePages(JsonObject jsonObject, String fullEndpoint) {
    if (StringUtils.isBlank(fullEndpoint)) {
      throw new IllegalStateException("The fullEndpoint cannot be blank to discover pages.");
    }
    if (jsonObject == null) {
      previousPageUrl = null;
      nextPageUrl = null;
    } else {
      discoverPages(jsonObject, fullEndpoint);
    }
  }
  
  /**
   * Get the data key to get the entries
   * @return the data key
   */
  public abstract String getDataKey();

  /**
   * Discover pages.
   *
   * @param jsonObject   The JSON object to discover the paging
   * @param fullEndpoint The full endpoint to build the next page URL
   */
  protected abstract void discoverPages(JsonObject jsonObject, String fullEndpoint);

  /**
   * Sets next page url.
   *
   * @param fullEndpoint The full endpoint to build the next page URL
   * @param start        The index of the first item you want results for
   * @param count        The number of items needed to be included on each page of results.
   */
  protected void setNextPageURL(String fullEndpoint, int start, int count) {
    nextPageUrl = createPagedURL(fullEndpoint, start + count, count);
  }

  /**
   * Sets previous page url.
   *
   * @param fullEndpoint The full endpoint
   * @param start        The index of the first item you want results for
   * @param count        The number of items needed to be included on each page of results.
   */
  protected void setPreviousPageURL(String fullEndpoint, int start, int count) {
    if (start > 0) {
      // There's a previous page
      previousPageUrl = createPagedURL(fullEndpoint, start - count, count);
    } else {
      previousPageUrl = null;
    }
  }
  
  private String createPagedURL(String fullEndpoint, int start, int count) {
    fullEndpoint = URLUtils.replaceOrAddQueryParameter(fullEndpoint, "start",
        Integer.toString(start));
    fullEndpoint = URLUtils.replaceOrAddQueryParameter(fullEndpoint, "count",
        Integer.toString(count));
    return fullEndpoint;
  }

}
