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

package com.echobox.api.linkedin.types.request;

import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.ShareText;
import com.eclipsesource.json.Json;

/**
 * Update share request body
 * @author joanna
 */
public class UpdateShareRequestBody {
  
  @LinkedIn("patch")
  private PatchBody patch;
  
  /**
   * Initialise update share request body
   * @param shareText the share text to update
   * @param jsonMapper the json mapper to serialise the share text
   */
  public UpdateShareRequestBody(ShareText shareText, JsonMapper jsonMapper) {
    PatchBody patch = new PatchBody();
    patch.setSet(Json.object().set("text", Json.parse(jsonMapper.toJson(shareText)).asObject()));
    this.patch = patch;
  }

}
