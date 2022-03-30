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

package com.echobox.api.linkedin.types.organization;

import static org.junit.Assert.assertEquals;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import org.junit.Test;

/**
 * The type Organization brand test.
 * @author alexandros
 */
public class OrganizationBrandTest extends DefaultJsonMapperTestBase {
  
  /**
   * Test organization brand json can be deserialized.
   */
  @Test
  public void testOrganizationBrandJsonCanBeDeserialized() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/organizationBrand.json");
  
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
  
    OrganizationBrand organizationBrand = 
        defaultJsonMapper.toJavaObject(json, OrganizationBrand.class);
  
    assertEquals(11111111L, organizationBrand.getId());
    assertEquals("example.com", organizationBrand.getLocalizedName());
    
    assertEquals("urn:li:media:/p/8/000/206/3de/1111111.png", 
        organizationBrand.getLogo().getCropped().toString());
    assertEquals("urn:li:media:/p/8/000/206/3de/1111111.png",
        organizationBrand.getLogo().getOriginal().toString());
    assertEquals(0, organizationBrand.getLogo().getCropInfo().getHeight());
    assertEquals(0, organizationBrand.getLogo().getCropInfo().getWidth());
    assertEquals(0, organizationBrand.getLogo().getCropInfo().getXAxis());
    assertEquals(0, organizationBrand.getLogo().getCropInfo().getYAxis());
    
    assertEquals("test_product", organizationBrand.getName().getLocalized().get("en_US"));
    assertEquals("US", organizationBrand.getName().getPreferredLocale().getCountry());
    assertEquals("en", organizationBrand.getName().getPreferredLocale().getLanguage());
    
    assertEquals("example.com", organizationBrand.getVanityName());
    
    assertEquals("2", organizationBrand.getVersionTag());
    
    assertEquals("urn:li:organization:2222222", 
        organizationBrand.getParentRelationship().getParent().toString());
    assertEquals(PrimaryOrganizationType.BRAND, organizationBrand.getPrimaryOrganizationType());
  }
  
}
