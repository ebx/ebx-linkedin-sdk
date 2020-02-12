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
 * RegisterUploadRequestBodyTest
 * @author joanna
 */
public class RegisterUploadRequestBodyTest {

  @Test
  public void testRegisterUploadRequestBody() {
    String json = "{\n" + "    \"registerUploadRequest\": {\n"
        + "        \"owner\": \"urn:li:organization:10000\",\n" + "        \"recipes\": [\n"
        + "            \"urn:li:digitalmediaRecipe:feedshare-video\"\n" + "        ],\n"
        + "        \"serviceRelationships\": [\n" + "            {\n"
        + "                \"identifier\": \"urn:li:userGeneratedContent\",\n"
        + "                \"relationshipType\": \"OWNER\"\n" + "            }\n" + "        ]\n"
        + "    }\n" + "}";
  
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    RegisterUploadRequestBody registerUploadRequestBody =
        defaultJsonMapper.toJavaObject(json, RegisterUploadRequestBody.class);
  
    RegisterUploadRequestBody.RegisterUploadRequest registerUploadRequest =
        registerUploadRequestBody.getRegisterUploadRequest();
    
    Assert.assertEquals(new URN("urn:li:organization:10000"),
        registerUploadRequest.getOwner());
    List<URN> recipes = registerUploadRequest.getRecipes();
    Assert.assertEquals(1, recipes.size());
    Assert.assertEquals(new URN("urn:li:digitalmediaRecipe:feedshare-video"),
        recipes.get(0));
  
    List<RegisterUploadRequestBody.ServiceRelationships> serviceRelationships =
        registerUploadRequest.getServiceRelationships();
    Assert.assertEquals(1, serviceRelationships.size());
    Assert.assertEquals("urn:li:userGeneratedContent",
        serviceRelationships.get(0).getIdentifier());
    Assert.assertEquals(RelationshipType.OWNER, serviceRelationships.get(0).getRelationshipType());
  }

}
