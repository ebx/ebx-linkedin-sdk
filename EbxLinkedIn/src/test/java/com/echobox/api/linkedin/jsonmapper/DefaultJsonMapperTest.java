package com.echobox.api.linkedin.jsonmapper;

import com.echobox.api.linkedin.types.Company;
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
    Company javaObject = mapper.toJavaObject(companyJSON, Company.class);
    System.out.println();
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
