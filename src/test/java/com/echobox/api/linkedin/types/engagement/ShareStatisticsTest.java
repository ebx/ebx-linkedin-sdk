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
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Share statistics test
 * @author clementcaylux on 27/11/2018.
 */
public class ShareStatisticsTest extends DefaultJsonMapperTestBase {

  /**
   * test that an engagement JSON is correctly deserialized
   * json taken from : 
   * https://developer.linkedin.com/docs/guide/v2/organizations/share-statistics#specific
   */
  @Test
  public void testEngagementJson() {
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    ShareStatistic shareStatistic = mapper.toJavaObject(
        "{\"totalShareStatistics\": {"
            + "\"shareCount\": 5,\"clickCount\": 78,\"engagement\": 0.022886324947985624,"
            + "\"likeCount\": 14,\"impressionCount\": 5287,\"commentCount\": 24, " 
            + "\"uniqueImpressionsCount\": 10},\"share\": "
            + "\"urn:li:share:1000000\","
            + "\"organizationalEntity\": \"urn:li:organization:2414183\"}", ShareStatistic.class);

    TotalShareStatistics totalShareStatistics = shareStatistic.getTotalShareStatistics();

    Assert.assertEquals(78, totalShareStatistics.getClickCount());
    Assert.assertEquals(5, totalShareStatistics.getShareCount());
    Assert.assertEquals("urn:li:share:1000000", shareStatistic.getShare());
    Assert.assertEquals(10, totalShareStatistics.getUniqueImpressionsCount());

  }
}
