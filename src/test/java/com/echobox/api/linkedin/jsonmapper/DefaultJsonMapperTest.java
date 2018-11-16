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

import com.echobox.api.linkedin.jsonmapper.LinkedInJsonMapperV1Test.TestJSONMapper;
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
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    TestJSONMapper result = mapper.toJavaObject("{\"name\":\"test\"}", TestJSONMapper.class);
    assertEquals("test", result.getName());
    assertEquals("TEST", result.getDerivedName());
  }
  
  /**
   * Ensure that the JSONMappingCompleted annotation is called
   */
  @Test
  public void testJSONListMappingCompleted() {
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    TestJSONMapper result = mapper.toJavaObject(
        "{\"sausages\":[{\"code\":\"F\",\"name\":\"frankfurter\"}]}", TestJSONMapper.class);
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
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    String json = mapper.toJson(company);
    assertEquals("{\"stockExchange\":null,\"ticker\":null,\"companyType\":null,"
        + "\"emailDomains\":null,\"description\":null,\"foundedYear\":null,\"endYear\":null,"
        + "\"logoUrl\":null,\"twitterId\":null,\"employeeCountRange\":null,\"specialties\":null,"
        + "\"websiteUrl\":null,\"squareLogoUrl\":null,\"industries\":null,\"numFollowers\":null,"
        + "\"name\":\"Test \\\"Quote\\\"\",\"blogRSSURL\":null,\"locations\":null,\"universalName"
        + "\":null,\"id\":123,\"status\":null}", json);
  }
  
  /**
   * Test toJSON can take a Java object and convert it into a JSON string, null values shouldn't be
   * included
   */
  @Test
  public void testToJSONWithIgnoreNullValuedProperties() {
    Company company = new Company();
    company.setId(123L);
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
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
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    String json = mapper.toJson(company, false);
    assertEquals("{\"stockExchange\":null,\"ticker\":null,\"companyType\":null,"
        + "\"emailDomains\":null,\"description\":null,\"foundedYear\":null,\"endYear\":null,"
        + "\"logoUrl\":null,\"twitterId\":null,\"employeeCountRange\":null,\"specialties\":null,"
        + "\"websiteUrl\":null,\"squareLogoUrl\":null,\"industries\":null,\"numFollowers\":null,"
        + "\"name\":null,\"blogRSSURL\":null,\"locations\":null,\"universalName\":null,"
        + "\"id\":123,\"status\":null}", json);
  }

}
