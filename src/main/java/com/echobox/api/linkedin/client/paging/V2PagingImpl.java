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
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * The paging strategy for V1 JSON responses
 * @author Joanna
 *
 */
public class V2PagingImpl extends PagingStrategy {
  
  private static final String DATA_KEY = "elements";
  
  @Override
  public String getDataKey() {
    return DATA_KEY;
  }

  @Override
  protected void discoverPages(JsonObject jsonObject, String fullEndpoint) {
    if (jsonObject.get(DATA_KEY) != null) {
      JsonArray elements = jsonObject.get(DATA_KEY).asArray();

      // Pull out paging info, if present
      if (jsonObject.get("paging") != null) {
        JsonObject jsonPaging = jsonObject.get("paging").asObject();

        if (jsonPaging.get("count") != null && jsonPaging.get("start") != null) {
          int count = jsonPaging.getInt("count", 0);
          int start = jsonPaging.getInt("start", 0);
          // You will know that you have reached the end of the dataset when your response
          // contains less elements in the entities block of the response than your count
          // parameter requested.
//          Map<String, List<String>> extractParametersFromUrl =
//              URLUtils.extractParametersFromUrl(fullEndpoint);
//          if (extractParametersFromUrl.containsKey("count")) {
//            // Check if the count is less than the elements returned - if so we're at the last page
//            int requestedCount = Integer.parseInt(extractParametersFromUrl.get("count").get(0));
//            if (elements.size() <= requestedCount) {
//              nextPageUrl = null;
//              setPreviousPageURL(fullEndpoint, start, count);
//              return;
//            }
//          }
  
          // LinkedIn paging seems to be currently broken and it might return return elements
          // where the count is less than the number of items requested but in fact there are
          // more elements and LinkedIn API has not returned them. Instead, continue to explore
          // the next page until there are no more pages.
          if (elements.size() <= 0) {
            nextPageUrl = null;
            setPreviousPageURL(fullEndpoint, start, count);
            return;
          }

          // Paging is available
          setNextPageURL(fullEndpoint, start, count);
          setPreviousPageURL(fullEndpoint, start, count);
        }

      } else {
        previousPageUrl = null;
        nextPageUrl = null;
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }
  }

}
