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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.echobox.api.linkedin.types.Annotation;
import com.echobox.api.linkedin.types.Company;
import com.echobox.api.linkedin.types.ContentEntity;
import com.echobox.api.linkedin.types.DistributionTarget;
import com.echobox.api.linkedin.types.Share;
import com.echobox.api.linkedin.types.ShareContent;
import com.echobox.api.linkedin.types.ShareDistribution;
import com.echobox.api.linkedin.types.ShareText;
import com.echobox.api.linkedin.types.Thumbnail;
import com.echobox.api.linkedin.types.objectype.AuditStamp;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.objectype.LocaleString;
import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
import com.echobox.api.linkedin.types.urn.function.FunctionURN;
import com.echobox.api.linkedin.types.urn.location.CountryGroupURN;
import com.echobox.api.linkedin.types.urn.location.CountryURN;
import com.echobox.api.linkedin.types.urn.location.PlaceURN;
import com.echobox.api.linkedin.types.urn.location.RegionURN;
import com.echobox.api.linkedin.types.urn.location.StateURN;

import org.junit.Test;

import java.util.Map;

/**
 * Default Json Mapper test
 * @author Joanna
 *
 */
public class DefaultJsonMapperTest extends DefaultJsonMapperTestBase {

  private static final String SHARE_JSON = "com.echobox.api.linkedin.jsonmapper/share.json";

  private static final String SHARE_TEXT_JSON =
      "com.echobox.api.linkedin.jsonmapper/shareText.json";

  private static final String SHARE_DISTRIBUTION_TARGET_JSON =
      "com.echobox.api.linkedin.jsonmapper/shareDistributionTarget.json";

  private static final String SHARE_CONTENT_JSON =
      "com.echobox.api.linkedin.jsonmapper/shareContent.json";

  private static final String AUDIT_STAMP_JSON =
      "com.echobox.api.linkedin.jsonmapper/auditStamp.json";

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
    assertEquals("{\"stockExchange\":null,\"ticker\":null,\"companyType\":null,"
        + "\"emailDomains\":null,\"description\":null,\"foundedYear\":null,\"endYear\":null,"
        + "\"logoUrl\":null,\"twitterId\":null,\"employeeCountRange\":null,\"specialties\":null,"
        + "\"websiteUrl\":null,\"squareLogoUrl\":null,\"industries\":null,\"numFollowers\":null,"
        + "\"name\":null,\"blogRSSURL\":null,\"locations\":null,\"universalName\":null,"
        + "\"id\":123,\"status\":null}", json);
  }

  /**
   * Test Share deserialisation form JSON to Java object
   */
  @Test
  public void testShareDeserialisesToJavaObject() {
    String shareJSON = readFileToString(SHARE_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Share share = mapper.toJavaObject(shareJSON, Share.class);
    assertEquals("urn:li:activity:12345657", share.getActivity());
    assertNull(share.getAgent());
    assertNotNull(share.getContent());
    assertNotNull(share.getCreated());
    assertNotNull(share.getDistribution());
    assertNull(share.getEdited());
    assertNotNull(share.getLastModified());
    assertNull(share.getOriginalShare());
    assertEquals("urn:li:organization:1000", share.getOwner());
    assertNull(share.getResharedShare());
    assertNull(share.getSubject());
    assertNull(share.getServiceProvider());
    assertNotNull(share.getText());

    ShareContent content = share.getContent();
    assertEquals(1, content.getContentEntities().size());
    assertEquals("content description", content.getDescription());
    assertEquals("Test Share with Content", content.getTitle());
    assertNull(content.getShareMediaCategory());
    ContentEntity contentEntity = content.getContentEntities().get(0);
    assertEquals("urn:li:article:0", contentEntity.getEntity());
    assertEquals("https://www.example.com/content.html", contentEntity.getEntityLocation());
    assertEquals(1, contentEntity.getThumbnails().size());
    Thumbnail thumbnail = contentEntity.getThumbnails().get(0);
    assertEquals("https://www.example.com/image.jpg", thumbnail.getResolvedUrl());
    assertNull(thumbnail.getImageSpecificContent().getWidth());
    assertNull(thumbnail.getImageSpecificContent().getHeight());

    AuditStamp created = share.getCreated();
    assertEquals("urn:li:person:A8xe03Qt10", created.getActor());
    assertEquals(new Long(1471967236000L), created.getTime());
    assertNull(created.getImpersonator());

    DistributionTarget distributionTarget = share.getDistribution().getDistributionTarget();
    assertNotNull(distributionTarget);
    assertEquals(2, distributionTarget.getIndustries().size());
    assertEquals("urn:li:industry:12", distributionTarget.getIndustries().get(0));
    assertEquals("urn:li:industry:37", distributionTarget.getIndustries().get(1));
    assertEquals(2, distributionTarget.getSeniorities().size());
    assertEquals("urn:li:seniority:4", distributionTarget.getSeniorities().get(0));
    assertEquals("urn:li:seniority:8", distributionTarget.getSeniorities().get(1));

    assertEquals("Test Share!", share.getText().getText());
  }

  /**
   * Test share text deserialisation form JSON to Java object
   */
  @Test
  public void testShareTextDeserialisesToJavaObject() {
    String sharetextJSON = readFileToString(SHARE_TEXT_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    ShareText shareText = mapper.toJavaObject(sharetextJSON, ShareText.class);

    assertEquals(1, shareText.getAnnotations().size());
    Annotation annotation = shareText.getAnnotations().get(0);
    assertEquals(new Integer(6), annotation.getStart());
    assertEquals(new Integer(8), annotation.getLength());
    assertEquals("urn:li:organization:1337", annotation.getEntity());
    assertEquals("Hello LinkedIn world!", shareText.getText());
  }

  /**
   * Test share distribution deserialisation form JSON to Java object
   */
  @Test
  public void testShareDistributionDeserialisesToJavaObject() {
    String shareDistributionJSON = readFileToString(SHARE_DISTRIBUTION_TARGET_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    ShareDistribution shareDistribution = mapper.toJavaObject(shareDistributionJSON,
        ShareDistribution.class);

    DistributionTarget distributionTarget = shareDistribution.getDistributionTarget();
    assertEquals(2, distributionTarget.getIndustries().size());
    assertEquals("urn:li:industry:12", distributionTarget.getIndustries().get(0));
    assertEquals("urn:li:industry:37", distributionTarget.getIndustries().get(1));
    assertEquals(2, distributionTarget.getSeniorities().size());
    assertEquals("urn:li:seniority:4", distributionTarget.getSeniorities().get(0));
    assertEquals("urn:li:seniority:8", distributionTarget.getSeniorities().get(1));
  }

  /**
   * Test share content deserialisation form JSON to Java object
   */
  @Test
  public void testShareContentDeserialisesToJavaObject() {
    String shareContentJSON = readFileToString(SHARE_CONTENT_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    ShareContent shareContent = mapper.toJavaObject(shareContentJSON, ShareContent.class);

    assertEquals("content description", shareContent.getDescription());
    assertEquals("Test Company Share with Content", shareContent.getTitle());
    assertEquals(1, shareContent.getContentEntities().size());
    ContentEntity contentEntity = shareContent.getContentEntities().get(0);
    assertEquals("https://www.example.com/content.html", contentEntity.getEntityLocation());
    assertEquals("urn:li:article:0", contentEntity.getEntity());
    assertEquals(1, contentEntity.getThumbnails().size());
    Thumbnail thumbnails = contentEntity.getThumbnails().get(0);
    assertNotNull(thumbnails.getImageSpecificContent());
    assertNull(thumbnails.getImageSpecificContent().getHeight());
    assertNull(thumbnails.getImageSpecificContent().getWidth());
    assertEquals("https://www.example.com/image.jpg", thumbnails.getResolvedUrl());
  }

  /**
   * Test audit stamp deserialisation form JSON to Java object
   */
  @Test
  public void testAuditStampDeserialisesToJavaObject() {
    String auditStampJSON = readFileToString(AUDIT_STAMP_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    AuditStamp auditStamp = mapper.toJavaObject(auditStampJSON, AuditStamp.class);

    assertEquals("urn:li:person:123ABC", auditStamp.getActor());
    assertEquals(new Long(1332187798000L), auditStamp.getTime());
    assertNull(auditStamp.getImpersonator());
  }

  /**
   * Test multiLocaleString deserialisation form JSON to Java object
   */
  @Test
  public void testMultiLocaleStringDeserialisesToJavaObject() {
    String mulitLocaleStringJSON =
        "  {"
            + "    \"preferredLocale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"localized\": {"
            + "      \"en_US\": \"2029 Stierlin Ct, Mountain View, CA 94043\""
            + "    }"
            + "  }";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    MultiLocaleString multiLocaleString = mapper.toJavaObject(mulitLocaleStringJSON,
        MultiLocaleString.class);

    assertEquals("US", multiLocaleString.getPreferredLocale().getCountry());
    assertEquals("en", multiLocaleString.getPreferredLocale().getLanguage());
    Map<String, String> localized = multiLocaleString.getLocalized();
    assertEquals("2029 Stierlin Ct, Mountain View, CA 94043", localized.get("en_US"));
  }

  /**
   * Test localeString deserialisation form JSON to Java object
   */
  @Test
  public void testLocaleStringDeserialisesToJavaObject() {
    String localeStringJSON =
        "{"
            + "    \"locale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"value\": \"California\""
            + "  }";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    LocaleString localeString = mapper.toJavaObject(localeStringJSON, LocaleString.class);

    assertEquals("US", localeString.getLocale().getCountry());
    assertEquals("en", localeString.getLocale().getLanguage());
    assertEquals("California", localeString.getValue());
  }

  /**
   * Test Locale deserialisation form JSON to Java object
   */
  @Test
  public void testLocaleDeserialisesToJavaObject() {
    String localeJSON =
        "{"
            + "    \"country\": \"US\","
            + "    \"language\": \"en\""
            + "  }";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Locale locale = mapper.toJavaObject(localeJSON, Locale.class);

    assertEquals("US", locale.getCountry());
    assertEquals("en", locale.getLanguage());
  }

  /**
   * Test Function URN deserialisation form JSON to Java object
   */
  @Test
  public void testFunctionURNDeserialisesToJavaObject() {
    String functionURNJSON =
        "{"
            + "  \"name\": {"
            + "    \"localized\": {"
            + "      \"en_US\": \"Information Technology\""
            + "    }"
            + "  },"
            + "  \"$URN\": \"urn:li:function:13\","
            + "  \"id\": 13"
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    FunctionURN functionURN = mapper.toJavaObject(functionURNJSON, FunctionURN.class);

    assertEquals(13, functionURN.getId());
    assertEquals("urn:li:function:13", functionURN.getUrn());
    assertEquals(1, functionURN.getName().getLocalized().size());
    assertEquals("Information Technology", functionURN.getName().getLocalized().get("en_US"));
  }

  /**
   * Test country group URN deserialisation form JSON to Java object
   */
  @Test
  public void testCountryGroupURNDeserialisesToJavaObject() {
    String countryGroupURNJSON =
        "{"
            + "  \"name\": {"
            + "    \"locale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"value\": \"Africa\""
            + "  },\r\n"
            + "  \"$URN\": \"urn:li:countryGroup:AF\","
            + "  \"countryGroupCode\": \"AF\""
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    CountryGroupURN countryGroupURN = mapper.toJavaObject(countryGroupURNJSON,
        CountryGroupURN.class);

    assertEquals("AF", countryGroupURN.getCountryGroupCode());
    assertEquals("urn:li:countryGroup:AF", countryGroupURN.getUrn());
    assertEquals("US", countryGroupURN.getName().getLocale().getCountry());
    assertEquals("en", countryGroupURN.getName().getLocale().getLanguage());
    assertEquals("Africa", countryGroupURN.getName().getValue());
  }

  /**
   * Test country URN deserialisation form JSON to Java object
   */
  @Test
  public void testCountryURNDeserialisesToJavaObject() {
    String countryURNJSON =
        "{"
            + "  \"name\": {"
            + "    \"locale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"value\": \"United States\""
            + "  },"
            + "  \"countryGroup\": \"urn:li:countryGroup:NA\","
            + "  \"$URN\": \"urn:li:country:us\","
            + "  \"countryCode\": \"us\""
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    CountryURN countryURN = mapper.toJavaObject(countryURNJSON, CountryURN.class);

    assertEquals("us", countryURN.getCountryCode());
    assertEquals("urn:li:countryGroup:NA", countryURN.getCountryGroup());
    assertEquals("urn:li:country:us", countryURN.getUrn());
    assertEquals("US", countryURN.getName().getLocale().getCountry());
    assertEquals("en", countryURN.getName().getLocale().getLanguage());
    assertEquals("United States", countryURN.getName().getValue());
  }

  /**
   * Test state URN deserialisation form JSON to Java object
   */
  @Test
  public void testStateURNDeserialisesToJavaObject() {
    String stateURNJSON =
        "{"
            + "  \"name\": {"
            + "    \"locale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"value\": \"California\""
            + "  },"
            + "  \"country\": \"urn:li:country:us\","
            + "  \"stateCode\": \"CA\","
            + "  \"$URN\": \"urn:li:state:(urn:li:country:us,CA)\""
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    StateURN stateURN = mapper.toJavaObject(stateURNJSON, StateURN.class);

    assertEquals("urn:li:country:us", stateURN.getCountry());
    assertEquals("urn:li:state:(urn:li:country:us,CA)", stateURN.getUrn());
    assertEquals("CA", stateURN.getStateCode());
    assertEquals("California", stateURN.getName().getValue());
    assertEquals("US", stateURN.getName().getLocale().getCountry());
    assertEquals("en", stateURN.getName().getLocale().getLanguage());
  }

  /**
   * Test region URN deserialisation form JSON to Java object
   */
  @Test
  public void testRegionGroupURNDeserialisesToJavaObject() {
    String regionURNJSON =
        "{"
            + "  \"name\": {"
            + "    \"locale\": {"
            + "      \"country\": \"US\","
            + "      \"language\": \"en\""
            + "    },"
            + "    \"value\": \"San Francisco Bay Area\""
            + "  },"
            + "  \"country\": \"urn:li:country:us\","
            + "  \"id\": 84,"
            + "  \"$URN\": \"urn:li:region:84\","
            + "  \"states\": ["
            + "    \"urn:li:state:(urn:li:country:us,CA)\""
            + "  ]"
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    RegionURN regionURN = mapper.toJavaObject(regionURNJSON, RegionURN.class);

    assertEquals("urn:li:country:us", regionURN.getCountry());
    assertEquals(new Integer(84), regionURN.getId());
    assertEquals("urn:li:region:84", regionURN.getUrn());
    assertEquals("San Francisco Bay Area", regionURN.getName().getValue());
    assertEquals("US", regionURN.getName().getLocale().getCountry());
    assertEquals("en", regionURN.getName().getLocale().getLanguage());
    assertEquals(1, regionURN.getStates().size());
    assertEquals("urn:li:state:(urn:li:country:us,CA)", regionURN.getStates().get(0));
  }

  /**
   * Test place URN deserialisation form JSON to Java object
   */
  @Test
  public void testPlaceURNDeserialisesToJavaObject() {
    String placeURNJSON =
        "{"
            + "    \"$URN\": \"urn:li:place:(urn:li:country:us,7-1-0-43-18)\","
            + "    \"name\": {"
            + "        \"locale\": {"
            + "            \"country\": \"US\","
            + "            \"language\": \"en\""
            + "        },"
            + "        \"value\": \"San Francisco, California\""
            + "    },"
            + "    \"parent\": \"urn:li:place:(urn:li:country:us,7-1-0-43)\","
            + "    \"placeCode\": \"7-1-0-43-18\","
            + "    \"country\": \"urn:li:country:us\","
            + "    \"adminLevel\": \"CITY\""
            + "}";

    DefaultJsonMapper mapper = new DefaultJsonMapper();
    PlaceURN placeURN = mapper.toJavaObject(placeURNJSON, PlaceURN.class);

    assertEquals("CITY", placeURN.getAdminLevel());
    assertEquals("urn:li:country:us", placeURN.getCountry());
    assertEquals("urn:li:place:(urn:li:country:us,7-1-0-43-18)", placeURN.getUrn());
    assertEquals("San Francisco, California", placeURN.getName().getValue());
    assertEquals("US", placeURN.getName().getLocale().getCountry());
    assertEquals("en", placeURN.getName().getLocale().getLanguage());
    assertEquals("urn:li:place:(urn:li:country:us,7-1-0-43)", placeURN.getParent());
    assertEquals("7-1-0-43-18", placeURN.getPlaceCode());
  }

}
