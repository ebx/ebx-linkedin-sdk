package com.echobox.api.linkedin.jsonmapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.types.Company;
import com.echobox.api.linkedin.types.CompanyType;
import com.echobox.api.linkedin.types.EmployeeCountRange;
import com.echobox.api.linkedin.types.IndustryCode;
import com.echobox.api.linkedin.types.Location;
import com.echobox.api.linkedin.types.StatusType;
import com.google.common.io.Files;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class DefaultJsonMapperTest {
  
  /**
   * Valid Chartbeat JSON data file
   */
  private static final String COMPANY_JSON = "com.echobox.api.linkedin.jsonmapper/company.json";
  
  /**
   * Test the JSON string can be serialised to a JSON object
   */
  @Test
  public void testToJavaObject() {
    String companyJSON = readFileToString(COMPANY_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Company company = mapper.toJavaObject(companyJSON, Company.class);
    
    List<Location> locations = company.getLocations();
    
    assertEquals(123, company.getId());
    assertEquals("Test \"Quote\"​", company.getName());
    assertEquals("test", company.getUniversalName());
    assertTrue(company.getEmailDomainsRaw().isEmpty());
    assertEquals("C", company.getCompanyTypeRaw().getCode());
    assertEquals("Public Company", company.getCompanyTypeRaw().getName());
    assertEquals(CompanyType.PUBLIC_COMPANY, company.getCompanyType());
    assertNull(company.getTicker());
    assertEquals("http://123.com", company.getWebsiteURL());
    assertEquals(1, company.getIndustriesRaw().size());
    assertEquals("137", company.getIndustriesRaw().get(0).getCode());
    assertEquals("Human Resources", company.getIndustriesRaw().get(0).getName());
    assertTrue(company.getIndustries().size() == 1);
    assertEquals(IndustryCode.HUMAN_RESOURCES, company.getIndustries().get(0));
    assertEquals("ACQ", company.getStatusRaw().getCode());
    assertEquals("Acquired", company.getStatusRaw().getName());
    assertEquals(StatusType.ACQUIRED, company.getStatus());
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
    assertEquals(EmployeeCountRange.XS, company.getEmployeeCountRange());
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
    assertNull(company.getStockExchange());
    assertEquals(new Integer(2018), company.getFoundedYear());
    assertNull(company.getEndYear());
    assertEquals(new Integer(959), company.getNumFollowers());
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
    assertEquals("{\"name\":\"Test \\\"Quote\\\"\",\"id\":123}", json);
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
    assertEquals("{\"id\":123}", json);
  }
  
  private String readFileToString(final String fileName) {
    try {
      return Files.toString(new File(ClassLoader.getSystemResource(fileName).toURI()),
          StandardCharsets.UTF_8);
    } catch (IOException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

}
