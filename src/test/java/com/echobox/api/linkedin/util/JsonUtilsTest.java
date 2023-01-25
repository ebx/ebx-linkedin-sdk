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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        + "    },"
        + "   \"numbers\": {\"long\":523412423423, \"double\":2.434343423432, "
        + "    \"int\": 42}"
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
  
    Map<String, Object> numbers = (Map) map.get("numbers");
    assertEquals(42L, numbers.get("int"));
    assertEquals(523412423423L, numbers.get("long"));
    assertEquals(2.434343423432, numbers.get("double"));
    
  }
  
  @Test
  public void testJsonWithBooleanValues() {
    String json = "{ \"initializeUploadRequest\": {\n"
        + "       \"owner\": \"urn:li:organization:2414183\",\n"
        + "       \"fileSizeBytes\": 1055736 ,\n" + "       \"uploadCaptions\": false,\n"
        + "       \"uploadThumbnail\": true\n" + "}\n" + "}";
  
    JsonObject asObject = Json.parse(json).asObject();
    Map<String, Object> map = JsonUtils.toMap(asObject);
    Map<String, Object> initializeUploadRequest = (Map) map.get("initializeUploadRequest");
    assertEquals("urn:li:organization:2414183", initializeUploadRequest.get("owner"));
    assertEquals(1055736L, initializeUploadRequest.get("fileSizeBytes"));
    assertFalse((Boolean) initializeUploadRequest.get("uploadCaptions"));
    assertTrue((Boolean) initializeUploadRequest.get("uploadThumbnail"));
  }

}
