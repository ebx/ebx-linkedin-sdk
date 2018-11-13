
package com.echobox.api.linkedin.version;

import org.json.JSONArray;
import org.json.JSONObject;

public class V1PagingImpl extends PagingStrategy {

  @Override
  public void discoverPages(JSONObject jsonObject, String fullEndpoint) {
    JSONArray values = jsonObject.getJSONArray("values");
    if (jsonObject.has("_count") && jsonObject.has("_start") && jsonObject.has("_total")) {
      // Paging is available
      int count = jsonObject.getInt("_count");
      int start = jsonObject.getInt("_start");
      int total = jsonObject.getInt("_total");
      // You will have paged through all the results once your
      // start value + count value >= the value of "_total" in the result set.
      if (start + count >= total || values.isEmpty()) {
        // Reached the end of the paging
        previousPageUrl = null;
        nextPageUrl = null;
      } else {
        setNextPageURL(fullEndpoint, start, count);
        setPreviousPageURL(fullEndpoint, start, count);
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }
  }

}
