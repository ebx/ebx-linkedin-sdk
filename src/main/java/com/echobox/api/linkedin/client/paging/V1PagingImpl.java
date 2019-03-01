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

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

/**
 * The paging strategy for V1 JSON responses
 * @author Joanna
 *
 */
public class V1PagingImpl extends PagingStrategy {
  
  private static final String DATA_KEY = "values";
  
  @Override
  public String getDataKey() {
    return DATA_KEY;
  }
  
  @Override
  protected void discoverPages(JsonObject jsonObject, String fullEndpoint) {
    if (jsonObject.get(DATA_KEY) != null) {
      JsonArray values = jsonObject.get(DATA_KEY).asArray();
      if (jsonObject.get("_count") != null && jsonObject.get("_start") != null
          && jsonObject.get("_total") != null) {
        // Paging is available
        int count = jsonObject.getInt("_count", 0);
        int start = jsonObject.getInt("_start", 0);
        int total = jsonObject.getInt("_total", 0);
        // You will have paged through all the results once your
        // start value + count value >= the value of "_total" in the result set.
        if (start + count >= total || values.isEmpty()) {
          // Reached the end of the paging
          previousPageUrl = null;
          setPreviousPageURL(fullEndpoint, start, count);
        } else {
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
