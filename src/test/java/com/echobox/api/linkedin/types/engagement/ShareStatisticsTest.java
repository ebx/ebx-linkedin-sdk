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

package com.echobox.api.linkedin.types.engagement;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.types.Paging;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author clementcaylux on 27/11/2018.
 */
public class ShareStatisticsTest {

  /**
   * test that an engagement JSON is correctly deserialized
   * json taken from : 
   * https://developer.linkedin.com/docs/guide/v2/organizations/share-statistics#specific
   */
  @Test
  public void testEngagementJson() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    ShareStatistics shareStatistics = mapper.toJavaObject(
        "{\n" + "  \"elements\": [\n" + "    {\n" + "      \"totalShareStatistics\": {\n"
            + "        \"shareCount\": 5,\n" + "        \"clickCount\": 78,\n"
            + "        \"engagement\": 0.022886324947985624,\n" + "        \"likeCount\": 14,\n"
            + "        \"impressionCount\": 5287,\n" + "        \"commentCount\": 24\n"
            + "      },\n" + "      \"share\": \"urn:li:share:1000000\",\n"
            + "      \"organizationalEntity\": \"urn:li:organization:2414183\"\n" + "    },\n"
            + "    {\n" + "      \"totalShareStatistics\": {\n" + "        \"shareCount\": 0,\n"
            + "        \"clickCount\": 1,\n" + "        \"engagement\": 0.2,\n"
            + "        \"likeCount\": 0,\n" + "        \"impressionCount\": 5,\n"
            + "        \"commentCount\": 0\n" + "      },\n"
            + "      \"share\": \"urn:li:share:1000001\",\n"
            + "      \"organizationalEntity\": \"urn:li:organization:2414183\"\n" + "    }\n"
            + "  ],\n" + "  \"paging\": {\n" + "    \"count\": 10,\n" + "    \"start\": 0,\n"
            + "    \"links\": []\n" + "  }\n" + "}", ShareStatistics.class);

    Paging paging = shareStatistics.getPaging();
    
    Assert.assertEquals(10, paging.getCount());
    Assert.assertEquals(0, paging.getStart());
    Assert.assertEquals(0, paging.getLinks().size());

    List<ShareStatisticsElement> elements = shareStatistics.getElements();
    ShareStatisticsElement shareStatisticsElement = elements.get(0);
    TotalShareStatistics totalShareStatistics = shareStatisticsElement.getTotalShareStatistics();

    Assert.assertEquals(78, totalShareStatistics.getClickCount());
    Assert.assertEquals(5, totalShareStatistics.getShareCount());
    Assert.assertEquals("urn:li:share:1000000", shareStatisticsElement.getShare());

    ShareStatisticsElement shareStatisticsElement1 = elements.get(1);
    TotalShareStatistics totalShareStatistics1 = shareStatisticsElement1.getTotalShareStatistics();

    Assert.assertEquals(0.2d, totalShareStatistics1.getEngagement(), 0.0);
    Assert.assertEquals(5, totalShareStatistics1.getImpressionCount());
    Assert.assertEquals("urn:li:organization:2414183", 
        shareStatisticsElement1.getOrganizationalEntity());

  }
}
