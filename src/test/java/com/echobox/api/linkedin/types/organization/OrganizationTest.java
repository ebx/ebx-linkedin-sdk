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

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.types.Location;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OrganizationTest {
  
  @Test
  public void testOrganizationJson() {

    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    
    String json = "{\n"
        + "    \"localizedDescription\": \"Connecting the world's professionals to be more productive and successful.\\r\\n\\r\\nFounded in 2003, LinkedIn connects the world's professionals to make them more productive and successful. With more than 430 million members worldwide, including executives from every Fortune 500 company, LinkedIn is the world's largest professional network on the Internet. The company has a diversified business model with revenue coming from Talent Solutions, Marketing Solutions, and Premium Subscriptions products. Headquartered in Silicon Valley, LinkedIn has offices across the globe.\",\n"
        + "    \"locations\": [\n" + "        {\n"
        + "            \"staffCountRange\": \"SIZE_1\",\n"
        + "            \"locationType\": \"HEADQUARTERS\",\n" + "            \"address\": {\n"
        + "                \"postalCode\": \"94043\",\n" + "                \"country\": \"US\",\n"
        + "                \"geographicArea\": \"CA\",\n"
        + "                \"line1\": \"2029 Stierlin Court\",\n"
        + "                \"city\": \"Mountain View\"\n" + "            }\n" + "        }\n"
        + "    ],\n" + "    \"entityStatus\": \"ACTIVE\",\n" + "    \"logo\": {\n"
        + "        \"cropInfo\": {\n" + "            \"y\": 0,\n" + "            \"width\": 800,\n"
        + "            \"height\": 800,\n" + "            \"x\": 0\n" + "        },\n"
        + "        \"original\": \"urn:li:media:/AAEAAQAAAAAAAAH-AAAAJDRRlZTNlZDQwLTk4YTIt.png\",\n"
        + "        \"cropped\": \"urn:li:media:/AAEAAQAAAAAAAANyAAAAJGRlZTNlZDQwLTk4YTIt.png\"\n"
        + "    },\n" + "    \"vanityName\": \"linkedin\",\n" + "    \"alternativeNames\": [],\n"
        + "    \"id\": 1337,\n" + "    \"industries\": [\n" + "        \"INTERNET\"\n" + "    ],\n"
        + "    \"$URN\": \"urn:li:organization:1337\",\n" + "    \"localizedName\": \"LinkedIn\",\n"
        + "    \"foundedOn\": {\n" + "        \"year\": 2003\n" + "    },\n"
        + "    \"localizedWebsite\": \"http://www.linkedin.com\",\n" + "    \"website\": {\n"
        + "        \"preferredLocale\": {\n" + "            \"country\": \"US\",\n"
        + "            \"language\": \"en\"\n" + "        },\n" + "        \"localized\": {\n"
        + "            \"en_US\": \"http://www.linkedin.com\"\n" + "        }\n" + "    },\n"
        + "    \"organizationStatus\": \"OPERATING\",\n" + "    \"description\": {\n"
        + "        \"preferredLocale\": {\n" + "            \"country\": \"US\",\n"
        + "            \"language\": \"en\"\n" + "        },\n" + "        \"localized\": {\n"
        + "            \"ja_JP\": \"\\u4e16\\u754c3\\u5104\\u4eba\\u4ee5\\u4e0a\\u304c\\u767b\\u9332\\u3059\\u308b\\u30d3\\u30b8\\u30cd\\u30b9\\u7279\\u5316\\u578bSNS\\u300cLinkedIn\\uff08\\u30ea\\u30f3\\u30af\\u30c8\\u30a4\\u30f3\\uff09\\u300d\\u306f\\u3001\\u4e16\\u754c\\u4e2d\\u306e\\u30d7\\u30ed\\u30d5\\u30a7\\u30c3\\u30b7\\u30e7\\u30ca\\u30eb\\u306e\\u751f\\u7523\\u6027\\u3092\\u9ad8\\u3081\\u3001\\u6210\\u529f\\u3059\\u308b\\u3088\\u3046\\u3001\\u3064\\u306a\\u3044\\u3067\\u3044\\u304f\\u3053\\u3068\\u3092\\u4f7f\\u547d\\u3068\\u3057\\u3001\\u4f01\\u696d\\u306e\\u63a1\\u7528\\u3001\\u5e83\\u544a\\u3001\\u55b6\\u696d\\u3092\\u652f\\u63f4\\u3057\\u3066\\u3044\\u307e\\u3059\\u3002\\u30d7\\u30ed\\u30d5\\u30a7\\u30c3\\u30b7\\u30e7\\u30ca\\u30eb\\u304c\\u7d4c\\u6e08\\u7684\\u306a\\u6a5f\\u4f1a\\u3068\\u3064\\u306a\\u304c\\u308b\\u305f\\u3081\\u306b\\u3001\\u4e16\\u754c\\u521d\\u306e\\u300c\\u30a8\\u30b3\\u30ce\\u30df\\u30c3\\u30af\\u30b0\\u30e9\\u30d5\\u300d\\u306e\\u5b9f\\u73fe\\u3092\\u76ee\\u6307\\u3057\\u3001\\u5168\\u4e16\\u754c\\u306b\\u62e0\\u70b9\\u3092\\u5e83\\u3052\\u4e8b\\u696d\\u3092\\u5c55\\u958b\\u3057\\u3066\\u3044\\u307e\\u3059\\u3002\",\n"
        + "            \"en_US\": \"Connecting the world's professionals to be more productive and successful.\\r\\n\\r\\nFounded in 2003, LinkedIn connects the world's professionals to make them more productive and successful. With more than 430 million members worldwide, including executives from every Fortune 500 company, LinkedIn is the world's largest professional network on the Internet. The company has a diversified business model with revenue coming from Talent Solutions, Marketing Solutions, and Premium Subscriptions products. Headquartered in Silicon Valley, LinkedIn has offices across the globe.\"\n"
        + "        }\n" + "    },\n" + "    \"organizationType\": \"PUBLIC_COMPANY\",\n"
        + "    \"groups\": [],\n" + "    \"defaultLocale\": {\n" + "        \"country\": \"US\",\n"
        + "        \"language\": \"en\"\n" + "    },\n" + "    \"localizedSpecialties\": [\n"
        + "        \"Online Professional Network\",\n" + "        \"Jobs\",\n"
        + "        \"People Search\",\n" + "        \"Company Search\",\n"
        + "        \"Address Book\",\n" + "        \"Advertising\",\n"
        + "        \"Professional Identity\",\n" + "        \"Group Collaboration\"\n" + "    ],\n"
        + "    \"name\": {\n" + "        \"preferredLocale\": {\n"
        + "            \"country\": \"US\",\n" + "            \"language\": \"en\"\n"
        + "        },\n" + "        \"localized\": {\n" + "            \"fr_FR\": \"LinkedIn\",\n"
        + "            \"it_IT\": \"LinkedIn\",\n" + "            \"no_NO\": \"LinkedIn\",\n"
        + "            \"nl_NL\": \"LinkedIn\",\n" + "            \"en_US\": \"LinkedIn\",\n"
        + "            \"th_TH\": \"LinkedIn\",\n" + "            \"ro_RO\": \"LinkedIn\",\n"
        + "            \"ja_JP\": \"LinkedIn\",\n" + "            \"es_ES\": \"LinkedIn\",\n"
        + "            \"ru_RU\": \"LinkedIn\",\n" + "            \"zh_TW\": \"LinkedIn\",\n"
        + "            \"pt_BR\": \"LinkedIn\",\n" + "            \"da_DK\": \"LinkedIn\",\n"
        + "            \"ko_KR\": \"LinkedIn\",\n" + "            \"cs_CZ\": \"LinkedIn\",\n"
        + "            \"ms_MY\": \"LinkedIn\",\n" + "            \"tr_TR\": \"LinkedIn\",\n"
        + "            \"de_DE\": \"LinkedIn\",\n" + "            \"pl_PL\": \"LinkedIn\",\n"
        + "            \"zh_CN\": \"LinkedIn (\\u9886\\u82f1)\",\n"
        + "            \"sv_SE\": \"LinkedIn\",\n" + "            \"in_ID\": \"LinkedIn\"\n"
        + "        }\n" + "    },\n" + "    \"staffCountRange\": \"SIZE_5001_TO_10000\",\n"
        + "    \"specialties\": [\n" + "        {\n" + "            \"locale\": {\n"
        + "                \"country\": \"US\",\n" + "                \"language\": \"en\"\n"
        + "            },\n" + "            \"tags\": [\n"
        + "                \"Online Professional Network\",\n" + "                \"Jobs\",\n"
        + "                \"People Search\",\n" + "                \"Company Search\",\n"
        + "                \"Address Book\",\n" + "                \"Advertising\",\n"
        + "                \"Professional Identity\",\n"
        + "                \"Group Collaboration\"\n" + "            ]\n" + "        }\n"
        + "    ],\n" + "    \"coverPhoto\": {\n" + "        \"cropInfo\": {\n"
        + "            \"y\": 0,\n" + "            \"width\": 646,\n"
        + "            \"height\": 220,\n" + "            \"x\": 0\n" + "        },\n"
        + "        \"original\": \"urn:li:media:/AAEAAQAAAAAAAAfmAAAAJDQwLTk4YTItNGNlLWRj.png\",\n"
        + "        \"cropped\": \"urn:li:media:/AAEAAQAAAAAAAAkOAAAAJDA3NmYwMzRkLTA2MTAt.png\"\n"
        + "    }\n" + "}";

    Organization organization = defaultJsonMapper.toJavaObject(json, Organization.class);
    List<String> alternativeNames = organization.getAlternativeNames();
    Location location = organization.getLocations().get(0);
    
//    Assert.assertTrue(location.isHeadquarters());
    Assert.assertEquals("Mountain View", location.getAddress().getCity());
    Assert.assertEquals("linkedin", organization.getVanityName());
    Assert.assertEquals("OPERATING", organization.getOrganizationStatus());
    Assert.assertEquals("US", organization.getDescription().getPreferredLocale().getCountry());
    Assert.assertTrue(organization.getName().getLocalized().keySet().contains("fr_FR"));
    Assert.assertEquals("PUBLIC_COMPANY", organization.getOrganizationType());
    Assert.assertEquals("Online Professional Network", organization.getLocalizedSpecialties().get(0));
    Assert.assertEquals("SIZE_5001_TO_10000", organization.getStaffCountRange());
  }

}