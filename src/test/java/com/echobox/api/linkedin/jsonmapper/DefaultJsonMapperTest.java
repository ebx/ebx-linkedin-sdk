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

package com.echobox.api.linkedin.jsonmapper;

import static org.junit.Assert.assertEquals;

import com.echobox.api.linkedin.types.Company;

import org.junit.Test;

/**
 * Default Json Mapper test
 * @author Joanna
 *
 */
public class DefaultJsonMapperTest {
  
  /**
   * Ensure that the JSONMappingCompleted annotation is called
   */
  @Test
  public void testJSONMappingCompleted() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    SimpleJSON result = mapper.toJavaObject("{\"name\":\"test\"}", SimpleJSON.class);
    assertEquals("test", result.getName());
    assertEquals("TEST", result.getDerivedName());
  }
  
  /**
   * Ensure that the JSONMappingCompleted annotation is called
   */
  @Test
  public void testJSONListMappingCompleted() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    SimpleJSON result = mapper.toJavaObject(
        "{\"sausages\":[{\"code\":\"F\",\"name\":\"frankfurter\"}]}", SimpleJSON.class);
    assertEquals(1, result.getSausages().size());
    assertEquals("F", result.getSausages().get(0).getCode());
    assertEquals("frankfurter", result.getSausages().get(0).getName());
  }
  
  /**
   * Test toJSON can take a Java object and convert it into a JSON string
   */
  @Test
  public void testToJSON() {
    Company company = new Company();
    company.setId(123L);
    company.setName("Test \"Quote\"");
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String json = mapper.toJson(company);
    assertEquals("{\"universalName\":null,\"emailDomains\":null,\"companyType\":null,"
        + "\"ticker\":null,\"websiteUrl\":null,\"industries\":null,\"status\":null,"
        + "\"logoUrl\":null,\"squareLogoUrl\":null,\"blogRSSURL\":null,\"twitterId\":null,"
        + "\"employeeCountRange\":null,\"specialties\":null,\"locations\":null,"
        + "\"description\":null,\"stockExchange\":null,\"foundedYear\":null,\"endYear\":null,"
        + "\"numFollowers\":null,\"name\":\"Test \\\"Quote\\\"\",\"id\":123}", json);
  }
  
  /**
   * Test toJSON can take a Java object and convert it into a JSON string, null values shouldn't be
   * included
   */
  @Test
  public void testToJSONWithIgnoreNullValuedProperties() {
    Company company = new Company();
    company.setId(123L);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String json = mapper.toJson(company, true);
    assertEquals("{\"id\":123}", json);
  }
  
  /**
   * Test toJSON can take a Java object and convert it into a JSON string, null values shouldn't be
   * included
   */
  @Test
  public void testToJSONWithDoNotIgnoreNullValuedProperties() {
    Company company = new Company();
    company.setId(123L);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    String json = mapper.toJson(company, false);
    assertEquals("{\"universalName\":null,\"emailDomains\":null,\"companyType\":null,"
        + "\"ticker\":null,\"websiteUrl\":null,\"industries\":null,\"status\":null,"
        + "\"logoUrl\":null,\"squareLogoUrl\":null,\"blogRSSURL\":null,\"twitterId\":null,"
        + "\"employeeCountRange\":null,\"specialties\":null,\"locations\":null,"
        + "\"description\":null,\"stockExchange\":null,\"foundedYear\":null,\"endYear\":null,"
        + "\"numFollowers\":null,\"name\":null,\"id\":123}", json);
  }

}
