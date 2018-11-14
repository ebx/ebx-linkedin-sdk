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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The paging strategy for V1 JSON responses
 * @author Joanna
 *
 */
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
          setNextPageURL(fullEndpoint, start, count);
          setPreviousPageURL(fullEndpoint, start, count);
        }
      }
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }
  }

}
