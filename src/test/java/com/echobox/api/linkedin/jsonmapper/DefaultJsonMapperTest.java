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

import com.echobox.api.linkedin.types.objectype.AuditStamp;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.objectype.LocaleString;
import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
import com.echobox.api.linkedin.types.social.actions.CommentAction;
import com.echobox.api.linkedin.types.statistics.page.Statistics;
import com.echobox.api.linkedin.types.statistics.page.TotalPageStatistics;
import org.junit.Test;

import java.util.Map;

/**
 * Default Json Mapper test
 * @author Joanna
 *
 */
public class DefaultJsonMapperTest extends DefaultJsonMapperTestBase {

  private static final String AUDIT_STAMP_JSON =
      "com.echobox.api.linkedin.jsonmapper/auditStamp.json";
  
  private static final String ORGANIZATION_STAISTICS_JSON = "com.echobox.api.linkedin"
      + ".jsonmapper/organizationStatistics.json";

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
