
package com.echobox.api.linkedin.version;

import org.json.JSONArray;
import org.json.JSONObject;

public class V2PagingImpl extends PagingStrategy {

  @Override
  public void discoverPages(JSONObject jsonObject, String fullEndpoint) {
    JSONArray jsonData = jsonObject.getJSONArray("values");
    // Pull out paging info, if present
    if (jsonObject.has("paging")) {
      JSONObject jsonPaging = jsonObject.getJSONObject("paging");
      Integer count = jsonPaging.has("count") ? jsonPaging.getInt("count") : null;
      Integer start = jsonPaging.has("start") ? jsonPaging.getInt("start") : null;
      // Paging is available
      if (count != null && start != null) {
        // You will know that you have reached the end of the dataset when your response contains
        // less elements in the entities block of the response than your count parameter requested.
        if (jsonData.length() < count) {
          previousPageUrl = null;
          nextPageUrl = null;
        } else {
          nextPageUrl = String.format("%s?start=%s&count=%s", fullEndpoint, start, count);
          if (start > 0) {
            // There's a previous page
            previousPageUrl = String.format("%s?start=%s&count=%s", fullEndpoint, start - count,
                count);
          }
        }
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }
  }

}
