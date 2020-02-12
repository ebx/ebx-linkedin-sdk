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

package com.echobox.api.linkedin.util;

import static org.junit.Assert.assertEquals;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.util.Map;

/**
 * JsonUtils test class
 * @author Joanna
 *
 */
public class JsonUtilsTest {
  
  /**
   * Test the JSON object can be converted into a map
   */
  @Test
  public void testJsonObjectIsConvertedToMap() {
    String json = "{"
        + "    \"visibility\": { \"code\": \"anyone\" },"
        + "    \"comment\": \"Testing a full company share!\","
        + "    \"content\": {"
        + "       \"submitted­-url\": \"https://www.bbc.co.uk/news/technology-46277028\","
        + "       \"title\": \"Test Share with Content\","
        + "       \"description\": \"content description\","
        + "       \"submitted-image-url\": \"https://ichef.bbci.co.uk/news/660/cpsprodpb"
        + "/13398/production/_104444787_whatsubject.jpg\""
        + "    }"
        + "}";
    JsonObject asObject = Json.parse(json).asObject();
    Map<String, Object> map = JsonUtils.toMap(asObject);
    
    Map<String, Object> visibility = (Map) map.get("visibility");
    assertEquals("anyone", visibility.get("code"));
    
    Object comment = map.get("comment");
    assertEquals("Testing a full company share!", comment);
    
    Map<String, Object> content = (Map) map.get("content");
    assertEquals("https://www.bbc.co.uk/news/technology-46277028", content.get("submitted­-url"));
    assertEquals("Test Share with Content", content.get("title"));
    assertEquals("content description", content.get("description"));
    assertEquals("https://ichef.bbci.co.uk/news/660/cpsprodpb/13398/production/_104444787_"
        + "whatsubject.jpg", content.get("submitted-image-url"));
  }

}
