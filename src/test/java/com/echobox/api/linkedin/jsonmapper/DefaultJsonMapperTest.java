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

import com.echobox.api.linkedin.types.Annotation;
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
import com.echobox.api.linkedin.types.social.actions.CommentAction;
import com.echobox.api.linkedin.types.social.actions.LikeAction;
import com.echobox.api.linkedin.types.social.actions.SocialAction;
import com.echobox.api.linkedin.types.statistics.page.Statistics;
import com.echobox.api.linkedin.types.statistics.page.TotalPageStatistics;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.function.FunctionURN;
import com.echobox.api.linkedin.types.urn.location.CountryGroupURN;
import com.echobox.api.linkedin.types.urn.location.CountryURN;
import com.echobox.api.linkedin.types.urn.location.PlaceURN;
import com.echobox.api.linkedin.types.urn.location.RegionURN;
import com.echobox.api.linkedin.types.urn.location.StateURN;
import com.echobox.api.linkedin.types.v1.Company;
import org.junit.Assert;
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
  
  private static final String ORGANIZATION_STAISTICS_JSON = "com.echobox.api.linkedin"
      + ".jsonmapper/organizationStatistics.json";

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
    assertEquals(new URN("urn:li:article:0"), contentEntity.getEntity());
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
    assertEquals(new URN("urn:li:industry:12"), distributionTarget.getIndustries().get(0));
    assertEquals(new URN("urn:li:industry:37"), distributionTarget.getIndustries().get(1));
    assertEquals(2, distributionTarget.getSeniorities().size());
    assertEquals(new URN("urn:li:seniority:4"), distributionTarget.getSeniorities().get(0));
    assertEquals(new URN("urn:li:seniority:8"), distributionTarget.getSeniorities().get(1));

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
    assertEquals(new URN("urn:li:industry:12"), distributionTarget.getIndustries().get(0));
    assertEquals(new URN("urn:li:industry:37"), distributionTarget.getIndustries().get(1));
    assertEquals(2, distributionTarget.getSeniorities().size());
    assertEquals(new URN("urn:li:seniority:4"), distributionTarget.getSeniorities().get(0));
    assertEquals(new URN("urn:li:seniority:8"), distributionTarget.getSeniorities().get(1));
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
    assertEquals(new URN("urn:li:article:0"), contentEntity.getEntity());
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
    assertEquals(new URN("function", "13"), functionURN.getUrn());
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
    assertEquals(new URN("countryGroup", "AF"), countryGroupURN.getUrn());
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
    assertEquals(new URN("country", "us"), countryURN.getUrn());
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
    assertEquals(new URN("state", "(urn:li:country:us,CA)"), stateURN.getUrn());
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
    assertEquals(new URN("region", "84"), regionURN.getUrn());
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
    assertEquals(new URN("country", "us"), placeURN.getCountry());
    assertEquals(new URN("place", "(urn:li:country:us,7-1-0-43-18)"), placeURN.getUrn());
    assertEquals("San Francisco, California", placeURN.getName().getValue());
    assertEquals("US", placeURN.getName().getLocale().getCountry());
    assertEquals("en", placeURN.getName().getLocale().getLanguage());
    assertEquals(new URN("place", "(urn:li:country:us,7-1-0-43)"), placeURN.getParent());
    assertEquals("7-1-0-43-18", placeURN.getPlaceCode());
  }
  
  @Test
  public void testSocialActionJSONToJavaObject() {
    String socialActionJSON = "{\n"
        + "\"commentsSummary\": "
        + "{\n"
        + " \"totalFirstLevelComments\": 7,\n"
        + " \"aggregatedTotalComments\": 10\n"
        + "},\n"
        + "\"likesSummary\": {\n"
        + "        \"totalLikes\": 3\n"
        + "},\n"
        + "\"$URN\": \"urn:li:activity:6250751010101421234\"\n" + "}";
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    SocialAction socialAction = mapper.toJavaObject(socialActionJSON, SocialAction.class);
    
    assertEquals(new Integer(7), socialAction.getCommentsSummary().getTotalFirstLevelComments());
    assertEquals(new Integer(10), socialAction.getCommentsSummary().getAggregatedTotalComments());
    assertEquals(new Integer(3), socialAction.getLikesSummary().getTotalLikes());
    assertNull(socialAction.getLikesSummary().getSelectedLikes());
    assertNull(socialAction.getLikesSummary().getAggregatedTotalLikes());
    assertEquals("urn:li:activity:6250751010101421234", socialAction.getUrn().toString());
  }
  
  @Test
  public void testLikeActionJSONToJavaObject() {
    String likeActionJSON = "{\n"
        + "\"actor\": \"urn:li:person:YI9MSsWFny\",\n"
        + "\"created\": {\n"
        + "     \"actor\": \"urn:li:person:YI9MSsWFny\",\n"
        + "     \"time\": 1501865637259\n"
        + " },\n"
        + "\"lastModified\": {\n"
        + "     \"actor\": \"urn:li:person:YI9MSsWFny\",\n"
        + "     \"time\": 1501865637259\n"
        + " },\n"
        + "\"object\": \"urn:li:activity:62990729270024273898\",\n"
        + "\"$URN\": \"urn:li:like:(urn:li:person:123ABC,urn:li:activity:62990729270024273898)\"\n"
        + " }";
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    LikeAction likeAction = mapper.toJavaObject(likeActionJSON, LikeAction.class);
    
    assertEquals("urn:li:person:YI9MSsWFny", likeAction.getActor());
    assertEquals("urn:li:person:YI9MSsWFny", likeAction.getCreated().getActor());
    assertEquals(new Long(1501865637259L), likeAction.getCreated().getTime());
    assertNull(likeAction.getCreated().getImpersonator());
    assertEquals("urn:li:person:YI9MSsWFny", likeAction.getLastModified().getActor());
    assertEquals(new Long(1501865637259L), likeAction.getLastModified().getTime());
    assertNull(likeAction.getLastModified().getImpersonator());
    assertEquals("urn:li:activity:62990729270024273898", likeAction.getObject());
    assertEquals("urn:li:like:(urn:li:person:123ABC,"
        + "urn:li:activity:62990729270024273898)", likeAction.getUrn().toString());
  }
  
  @Test
  public void testCommentActionJSONToJavaObject() {
    String commentActionJSON = "{\n"
        + "\"actor\": \"urn:li:person:PPT1JOhhnE\",\n"
        + " \"content\": [\n"
        + "  {\n"
        + "   \"type\": \"IMAGE\",\n"
        + "   \"url\": \"http://image-store.slidesharecdn.com/dcdd972a-5142-499"
        + "1-af1b-5d6094039c5b-original.png\"\n"
        + "  }\n"
        + " ],\n"
        + " \"created\": {\n"
        + "    \"actor\": \"urn:li:person:PPT1JOhhnE\",\n"
        + "    \"time\": 1497973425097\n"
        + " },\n"
        + " \"id\": \"6282955928685940736\",\n"
        + " \"lastModified\": {\n"
        + "   \"actor\": \"6282955928685940736\",\n"
        + "   \"time\": 1497973425097\n"
        + " },\n"
        + " \"likesSummary\": {\n"
        + "   \"aggregatedTotalLikes\": 2,\n"
        + "   \"likedByCurrentUser\": false,\n"
        + "   \"selectedLikes\": [\n"
        + "      \"urn:li:like:(urn:li:person:y2eHFCCpWi,urn:li:activity:6273189577469632512)\",\n"
        + "      \"urn:li:like:(urn:li:person:V4MtLfifrq,urn:li:activity:6273189577469632512)\"\n"
        + "    ],\n"
        + "   \"totalLikes\": 2\n"
        + "  },\n"
        + "  \"message\": {\n"
        + "    \"attributes\": [],\n"
        + "    \"text\": \"Test Pic in comment\"\n"
        + "  },\n"
        + "  \"object\": \"urn:li:activity:6273189577469632512\",\n"
        + "  \"$URN\": \"urn:li:comment:(urn:li:activity:"
        + "6273189577469632512,6282955928685940736)\"\n"
        + "}";
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    CommentAction commentAction = mapper.toJavaObject(commentActionJSON, CommentAction.class);
    
    assertEquals("PPT1JOhhnE", commentAction.getActor().getId());
    assertEquals(1, commentAction.getContent().size());
    assertEquals("IMAGE", commentAction.getContent().get(0).getType());
    assertEquals("http://image-store.slidesharecdn.com/dcdd972a-5142"
        + "-4991-af1b-5d6094039c5b-original.png", commentAction.getContent().get(0).getUrl());
    assertEquals(new Long(6282955928685940736L), commentAction.getId());
    assertEquals(new Integer(2), commentAction.getLikesSummary().getAggregatedTotalLikes());
    assertEquals(new Integer(2), commentAction.getLikesSummary().getTotalLikes());
    assertFalse(commentAction.getLikesSummary().getLikedByCurrentUser());
    assertEquals(2, commentAction.getLikesSummary().getSelectedLikes().size());
    assertEquals("urn:li:like:(urn:li:person:y2eHFCCpWi,urn:li:activity:"
        + "6273189577469632512)", commentAction.getLikesSummary().getSelectedLikes().get(0));
    assertEquals("urn:li:like:(urn:li:person:V4MtLfifrq,urn:li:activity"
        + ":6273189577469632512)", commentAction.getLikesSummary().getSelectedLikes().get(1));
    
    assertTrue(commentAction.getMessage().getAttributes().isEmpty());
    assertEquals("Test Pic in comment", commentAction.getMessage().getText());
    
    assertEquals("6273189577469632512", commentAction.getObject().getId());
    assertEquals("urn:li:comment:(urn:li:activity:"
        + "6273189577469632512,6282955928685940736)", commentAction.getUrn().toString());
  }
  
  @Test
  public void testMessageJson() {
    String commentMessageJSON = " {\n" 
        + "        \"attributes\": [\n" 
        + "            {\n"
        + "                \"length\": 17,\n" 
        + "                \"start\": 0,\n"
        + "                \"value\": {\n"
        + "                    \"com.linkedin.common.CompanyAttributedEntity\": {\n"
        + "                        \"company\": \"urn:li:organization:2414183\"\n"
        + "                    }\n" 
        + "                }\n" 
        + "            },\n" 
        + "            {\n"
        + "                \"length\": 14,\n" 
        + "                \"start\": 38,\n"
        + "                \"value\": {\n"
        + "                    \"com.linkedin.common.MemberAttributedEntity\": {\n"
        + "                        \"member\": \"urn:li:person:uOeeiwWoxO\"\n"
        + "                    }\n" 
        + "                }\n" 
        + "            }\n" 
        + "        ],\n"
        + "        \"text\": \"Dunder Mifflin's Dundie Award goes to Dwight Schrute!\"\n" 
        + "    }";
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    CommentAction.CommentMessage commentMessage = mapper.toJavaObject(commentMessageJSON,
        CommentAction.CommentMessage.class);
    
    assertNotNull(commentMessage);
    assertEquals("Dunder Mifflin's Dundie Award goes to Dwight Schrute!", 
        commentMessage.getText());
    assertEquals(2, commentMessage.getAttributes().size());
    CommentAction.Attribute companyAttribute = commentMessage.getAttributes().get(0);
    assertEquals(new Integer(17), companyAttribute.getLength());
    assertEquals(new Integer(0), companyAttribute.getStart());
    assertNull(companyAttribute.getMemberVaue().getMember());
    assertEquals("2414183", companyAttribute
        .getCompanyValue().getCompany().getCompany().getId());
  
    CommentAction.Attribute memberAttribute = commentMessage.getAttributes().get(1);
  
    assertNull(memberAttribute.getCompanyValue().getCompany());
    assertEquals("uOeeiwWoxO", memberAttribute
        .getMemberVaue().getMember().getMember().getId());
    
  }
  
  
  /**
   * Test urns are serialized as strings
   */
  @Test
  public void testURNsAreSerializedAsStrings() {
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
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    PlaceURN placeURN = defaultJsonMapper.toJavaObject(placeURNJSON, PlaceURN.class);
    String serialized = defaultJsonMapper.toJson(placeURN, true);
    
    Assert.assertTrue(serialized.contains("\"country\":\"urn:li:country:us\""));
    
  } 
  
  @Test
  public void organizationStatisticsTest() {
    String organizationStatisticsJson = readFileToString(ORGANIZATION_STAISTICS_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Statistics.OrganizationStatistics organizationStatistics = mapper.toJavaObject(
        organizationStatisticsJson, Statistics.OrganizationStatistics.class);
    assertNotNull(organizationStatistics);
    assertNull(organizationStatistics.getTimeRange());
    assertEquals("2414183", organizationStatistics.getOrganization().getId());
    assertNotNull(organizationStatistics.getTotalPageStatistics());
    TotalPageStatistics totalPageStatistics = organizationStatistics.getTotalPageStatistics();
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getCareersPageClicks().getCareersPageBannerPromoClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getCareersPageClicks().getCareersPageEmployeesClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getCareersPageClicks().getCareersPagePromoLinksClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getCareersPageClicks().getCareersPageJobsClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getMobileCareersPageClicks().getCareersPageJobsClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getMobileCareersPageClicks().getCareersPageJobsClicks());
    assertEquals(new Integer(0), totalPageStatistics.getClicks()
        .getMobileCareersPageClicks().getCareersPageJobsClicks());
    
    assertEquals(new Integer(0), totalPageStatistics.getViews()
        .getMobileJobsPageViews().getPageViews());
    assertEquals(new Integer(5), totalPageStatistics.getViews()
        .getCareersPageViews().getPageViews());
    assertEquals(new Integer(0), totalPageStatistics.getViews()
        .getMobileLifeAtPageViews().getPageViews());
    assertEquals(new Integer(17321), totalPageStatistics.getViews()
        .getAllDesktopPageViews().getPageViews());
    assertEquals(new Integer(465), totalPageStatistics.getViews()
        .getAllMobilePageViews().getPageViews());
    assertEquals(new Integer(0), totalPageStatistics.getViews()
        .getJobsPageViews().getPageViews());
    assertEquals(new Integer(17781), totalPageStatistics.getViews()
        .getOverviewPageViews().getPageViews());
    assertEquals(new Integer(5), totalPageStatistics.getViews()
        .getDesktopCareersPageViews().getPageViews());
    assertEquals(new Integer(465), totalPageStatistics.getViews()
        .getMobileOverviewPageViews().getPageViews());
    assertEquals(new Integer(5), totalPageStatistics.getViews()
        .getLifeAtPageViews().getPageViews());
    assertEquals(new Integer(17316), totalPageStatistics.getViews()
        .getDesktopOverviewPageViews().getPageViews());
    assertEquals(new Integer(5), totalPageStatistics.getViews()
        .getDesktopLifeAtPageViews().getPageViews());
    assertEquals(new Integer(0), totalPageStatistics.getViews()
        .getMobileCareersPageViews().getPageViews());
    assertEquals(new Integer(17786), totalPageStatistics.getViews()
        .getAllPageViews().getPageViews());
    
    assertEquals("10",
        organizationStatistics.getPageStatisticsBySeniority().get(0).getSeniority().getId());
    assertEquals("us",
        organizationStatistics.getPageStatisticsByCountry().get(0).getCountry().getId());
    assertEquals("1", organizationStatistics.getPageStatisticsByIndustry()
        .get(0).getIndustry().getId());
    assertEquals("SIZE_10001_OR_MORE",
        organizationStatistics.getPageStatisticsByStaffCountRange().get(0).getStaffCountRange());
    assertEquals("7", organizationStatistics.getPageStatisticsByRegion()
        .get(0).getRegion().getId());
    assertEquals("1", organizationStatistics.getPageStatisticsByFunction()
        .get(0).getFunction().getId());
  }
  
  @Test
  public void testOrganizationStatistcsWithTimeRange() {
    String organizationStatisticsJson = "{\n"
        + "      \"organization\": \"urn:li:organization:1000\",\n"
        + "      \"timeRange\": {\n"
        + "        \"start\": 1451606400000,\n"
        + "        \"end\": 1451692800000\n"
        + "      },\n"
        + "      \"totalPageStatistics\": {\n"
        + "        \"clicks\": {\n"
        + "          \"careersPageClicks\": {\n"
        + "            \"careersPageBannerPromoClicks\": 0,\n"
        + "            \"careersPagePromoLinksClicks\": 0,\n"
        + "            \"careersPageEmployeesClicks\": 0,\n"
        + "            \"careersPageJobsClicks\": 0\n"
        + "          }\n"
        + "        },\n"
        + "        \"views\": {\n"
        + "          \"careersPageViews\": {\n"
        + "            \"uniquePageViews\": 0,\n"
        + "            \"pageViews\": 0\n"
        + "          },\n"
        + "          \"overviewPageViews\": {\n"
        + "            \"uniquePageViews\": 0,\n"
        + "            \"pageViews\": 0\n"
        + "          },\n"
        + "          \"allPageViews\": {\n"
        + "            \"pageViews\": 0\n"
        + "          }\n"
        + "        }\n"
        + "      }\n"
        + "    }";
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Statistics.OrganizationStatistics organizationStatistics = mapper.toJavaObject(
        organizationStatisticsJson, Statistics.OrganizationStatistics.class);
    
    assertNotNull(organizationStatistics);
    assertNotNull(organizationStatistics.getTimeRange());
    assertEquals("1000", organizationStatistics.getOrganization().getId());
    assertEquals(new Long(1451606400000L), organizationStatistics.getTimeRange().getStart());
    assertEquals(new Long(1451692800000L), organizationStatistics.getTimeRange().getEnd());
    assertEquals(new Integer(0), organizationStatistics.getTotalPageStatistics()
        .getViews().getCareersPageViews().getUniquePageViews());
    assertNotNull(organizationStatistics.getTotalPageStatistics());
    assertNull(organizationStatistics.getPageStatisticsByCountry());
    assertNull(organizationStatistics.getPageStatisticsBySeniority());
    assertNull(organizationStatistics.getPageStatisticsByIndustry());
    assertNull(organizationStatistics.getPageStatisticsByFunction());
    assertNull(organizationStatistics.getPageStatisticsByStaffCountRange());
  }
  
  @Test
  public void testBrandStatistics() {
    String brandStatisticsJson = "{\n"
        + "      \"pageStatisticsBySeniority\": [],\n"
        + "      \"pageStatisticsByCountry\": [],\n"
        + "      \"pageStatisticsByIndustry\": [],\n"
        + "      \"totalPageStatistics\": {\n"
        + "        \"views\": {\n"
        + "          \"overviewPageViews\": {\n"
        + "            \"pageViews\": 1\n"
        + "          },\n"
        + "          \"allDesktopPageViews\": {\n"
        + "            \"pageViews\": 1\n"
        + "          },\n"
        + "          \"mobileOverviewPageViews\": {\n"
        + "            \"pageViews\": 0\n"
        + "          },\n"
        + "          \"allMobilePageViews\": {\n"
        + "            \"pageViews\": 0\n"
        + "          },\n"
        + "          \"desktopOverviewPageViews\": {\n"
        + "            \"pageViews\": 1\n"
        + "          },\n"
        + "          \"allPageViews\": {\n"
        + "            \"pageViews\": 1\n"
        + "          }\n"
        + "        }\n"
        + "      },\n"
        + "      \"brand\": \"urn:li:organizationBrand:18085185\",\n"
        + "      \"pageStatisticsByStaffCountRange\": [],\n"
        + "      \"pageStatisticsByRegion\": [],\n"
        + "      \"pageStatisticsByFunction\": []\n"
        + "    }" ;
    
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Statistics.BrandStatistics brandStatistics = mapper.toJavaObject(
        brandStatisticsJson, Statistics.BrandStatistics.class);
    assertNotNull(brandStatistics);
    assertEquals("18085185", brandStatistics.getBrand().getId());
    assertTrue(brandStatistics.getPageStatisticsBySeniority().isEmpty());
    assertEquals(new Integer(1), brandStatistics.getTotalPageStatistics()
        .getViews().getOverviewPageViews().getPageViews());
    assertEquals(new Integer(1), brandStatistics.getTotalPageStatistics()
        .getViews().getAllDesktopPageViews().getPageViews());
    assertEquals(new Integer(0), brandStatistics.getTotalPageStatistics()
        .getViews().getMobileOverviewPageViews().getPageViews());
    assertEquals(new Integer(0), brandStatistics.getTotalPageStatistics()
        .getViews().getAllMobilePageViews().getPageViews());
    assertEquals(new Integer(1), brandStatistics.getTotalPageStatistics()
        .getViews().getDesktopOverviewPageViews().getPageViews());
    assertEquals(new Integer(1), brandStatistics.getTotalPageStatistics()
        .getViews().getAllPageViews().getPageViews());
  }

}
