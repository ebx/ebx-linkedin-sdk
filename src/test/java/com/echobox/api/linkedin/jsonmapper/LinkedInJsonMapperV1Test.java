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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.types.v1.Company;
import com.echobox.api.linkedin.types.v1.Location;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * DefaultJsonMapperTest
 * @author Joanna
 *
 */
public class LinkedInJsonMapperV1Test extends DefaultJsonMapperTestBase {
  
  /**
   * Valid Company JSON data file
   */
  private static final String COMPANY_JSON = "com.echobox.api.linkedin.jsonmapper/company.json";
  
  /**
   * Test the JSON string can be serialised to a JSON object
   */
  @Test
  public void testToJavaObject() {
    String companyJSON = readFileToString(COMPANY_JSON);
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    Company company = mapper.toJavaObject(companyJSON, Company.class);
    
    List<Location> locations = company.getLocations();
    
    assertEquals(123, company.getId());
    assertEquals("Test \"Quote\"​", company.getName());
    assertEquals("test", company.getUniversalName());
    assertTrue(company.getEmailDomainsRaw().isEmpty());
    assertEquals("C", company.getCompanyTypeRaw().getCode());
    assertEquals("Public Company", company.getCompanyTypeRaw().getName());
    assertNull(company.getTicker());
    assertEquals("http://123.com", company.getWebsiteURL());
    assertEquals(1, company.getIndustriesRaw().size());
    assertEquals("137", company.getIndustriesRaw().get(0).getCode());
    assertEquals("Human Resources", company.getIndustriesRaw().get(0).getName());
    assertEquals("ACQ", company.getStatusRaw().getCode());
    assertEquals("Acquired", company.getStatusRaw().getName());
    assertEquals("https://media.licdn.com/dms/image/C560BAQG94DRkxZXHUg/company-logo_200_200/0?"
        + "e=1548288000&v=beta&t=Y1zXBOEPH8S7a1SU0qv-uXW8aKCgXR4egM5KQdnnZTg",
        company.getLogoURL());
    assertEquals("https://media.licdn.com/dms/image/C560BAQG94DRkxZXHUg/company-logo_200_200/0?"
        + "e=1548288000&v=beta&t=Y1zXBOEPH8S7a1SU0qv-uXW8aKCgXR4egM5KQdnnZTg",
        company.getSquareLogoURL());
    assertNull(company.getBlogRSSURL());
    assertEquals("", company.getTwitterId());
    assertEquals("C", company.getEmployeeCountRangeRaw().getCode());
    assertEquals("11-50", company.getEmployeeCountRangeRaw().getName());
    assertEquals(Arrays.asList("slacking", "computer science", "IT", "DevTest", "bug bash"),
        company.getSpecialties());
    
    assertEquals(2, locations.size());
    
    assertTrue(locations.get(0).isHeadquarters());
    assertTrue(locations.get(0).isActive());
    assertNotNull(locations.get(0).getAddress());
    assertEquals("12 Swanee Dr", locations.get(0).getAddress().getFirstStreet());
    assertNull(locations.get(0).getAddress().getSecondStreet());
    assertEquals("Goddard", locations.get(0).getAddress().getCity());
    assertEquals("Kansas", locations.get(0).getAddress().getState());
    assertEquals("67051", locations.get(0).getAddress().getPostalCode());
    assertEquals("us", locations.get(0).getAddress().getCountryCode());
    assertEquals(new Integer(904), locations.get(0).getAddress().getRegionCode());
    assertNotNull(locations.get(0).getContactInfo());
    assertNull(locations.get(0).getContactInfo().getPhoneOne());
    assertNull(locations.get(0).getContactInfo().getPhoneTwo());
    assertNull(locations.get(0).getContactInfo().getFax());
    assertNull(locations.get(0).getDescription());
    
    assertFalse(locations.get(1).isHeadquarters());
    assertTrue(locations.get(1).isActive());
    assertNotNull(locations.get(1).getAddress());
    assertEquals("12345 Gigli Ct", locations.get(1).getAddress().getFirstStreet());
    assertNull(locations.get(1).getAddress().getSecondStreet());
    assertEquals("Los Altos Hills", locations.get(1).getAddress().getCity());
    assertEquals("California", locations.get(1).getAddress().getState());
    assertEquals("94857", locations.get(1).getAddress().getPostalCode());
    assertEquals("us", locations.get(1).getAddress().getCountryCode());
    assertEquals(new Integer(0), locations.get(1).getAddress().getRegionCode());
    assertNotNull(locations.get(1).getContactInfo());
    assertNull(locations.get(0).getContactInfo().getPhoneOne());
    assertNull(locations.get(0).getContactInfo().getPhoneTwo());
    assertNull(locations.get(0).getContactInfo().getFax());
    assertNull(locations.get(1).getDescription());
    
    assertEquals("Some sort of description", company.getDescription());
    assertNull(company.getStockExchangeRaw());
    assertEquals(new Integer(2018), company.getFoundedYear());
    assertNull(company.getEndYear());
    assertEquals(new Integer(959), company.getNumFollowers());
  }
  
  /**
   * Test for the case LinkedIn may return a JSON array rather than the expected nested JSON array
   * within an object can still convert to the correct object
   */
  @Test
  public void testToJavaListForJSONArray() {
    String json = "{\"sausages\": [{\"code\":\"123\",\"name\":\"Bratwurst\"}]}";
    LinkedInJsonMapperV1 mapper = new LinkedInJsonMapperV1();
    SimpleJSON result = mapper.toJavaObject(json, SimpleJSON.class);
    assertEquals(1, result.getSausages().size());
    assertEquals("123", result.getSausages().get(0).getCode());
    assertEquals("Bratwurst", result.getSausages().get(0).getName());
  }
}
