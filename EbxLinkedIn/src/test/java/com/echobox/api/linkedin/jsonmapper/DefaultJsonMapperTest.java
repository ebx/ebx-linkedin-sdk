package com.echobox.api.linkedin.jsonmapper;

import static org.junit.Assert.assertEquals;

import com.echobox.api.linkedin.types.Company;
import com.echobox.api.linkedin.types.CompanyType;
import com.google.common.io.Files;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class DefaultJsonMapperTest {
  
  /**
   * Valid Chartbeat JSON data file
   */
  private static final String COMPANY_JSON = "com.echobox.api.linkedin.jsonmapper/company.json";
  
  @Test
  public void testToJavaObject() {
    String companyJSON = readFileToString(COMPANY_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Company company = mapper.toJavaObject(companyJSON, Company.class);
    
    assertEquals("test", company.getUniversalName());
    assertEquals(CompanyType.PUBLIC_COMPANY, company.getCompanyType());
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
