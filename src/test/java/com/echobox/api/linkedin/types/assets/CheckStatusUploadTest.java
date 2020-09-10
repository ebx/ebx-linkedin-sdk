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

package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * CheckStatusUploadTest
 * @author joanna
 */
public class CheckStatusUploadTest {
  
  @Test
  public void testCheckStatusUploadTest() {
    String json = "{\n" + "    \"created\": 1521582700662,\n"
        + "    \"id\": \"C5405AQEOFHXqeM2vRA\",\n" + "    \"lastModified\": 1521583180818,\n"
        + "    \"mediaTypeFamily\": \"VIDEO\",\n" + "    \"recipes\": [\n" + "        {\n"
        + "            \"recipe\": \"urn:li:digitalmediaRecipe:feedshare-video\",\n"
        + "            \"status\": \"PROCESSING\"\n" + "        }\n" + "    ],\n"
        + "    \"serviceRelationships\": [\n" + "        {\n"
        + "            \"identifier\": \"urn:li:userGeneratedContent\",\n"
        + "            \"relationshipType\": \"OWNER\"\n" + "        }\n" + "    ],\n"
        + "    \"status\": \"ALLOWED\"\n" + "}";
  
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    CheckStatusUpload checkStatusUpload = defaultJsonMapper.toJavaObject(json,
        CheckStatusUpload.class);
  
    Assert.assertEquals(new Long(1521582700662L), checkStatusUpload.getCreated());
    Assert.assertEquals("C5405AQEOFHXqeM2vRA", checkStatusUpload.getId());
    Assert.assertEquals(new Long(1521583180818L), checkStatusUpload.getLastModified());
    Assert.assertEquals("VIDEO", checkStatusUpload.getMediaTypeFamily());
  
    List<CheckStatusUpload.Recipe> recipes = checkStatusUpload.getRecipes();
    Assert.assertEquals(1, recipes.size());
    Assert.assertEquals(new URN("urn:li:digitalmediaRecipe:feedshare-video"),
        recipes.get(0).getRecipe());
    Assert.assertEquals("PROCESSING", recipes.get(0).getStatus());
  
    List<RegisterUploadRequestBody.ServiceRelationships> serviceRelationships =
        checkStatusUpload.getServiceRelationships();
    Assert.assertEquals(1, serviceRelationships.size());
    Assert.assertEquals("urn:li:userGeneratedContent",
        serviceRelationships.get(0).getIdentifier());
    Assert.assertEquals(RelationshipType.OWNER, serviceRelationships.get(0).getRelationshipType());
    
    Assert.assertEquals("ALLOWED", checkStatusUpload.getStatus());
  }
  
}
