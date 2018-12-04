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

package com.echobox.api.linkedin.types.statistics;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author clementcaylux on 03/12/2018.
 */
public class StatisticsTest extends DefaultJsonMapperTestBase {

  /**
   * test that an statistics JSON is correctly deserialized
   * json taken from : https://developer.linkedin.com/docs/guide/v2/organizations/
   * follower-statistics
   * 
   */
  @Test
  public void testEngagementJson() {

    String json =
        readFileToString("com.echobox.api.linkedin.jsonmapper/organizationFollowerStatistics.json");

    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    OrganizationFollowerStatistics organizationFollowerStatistics =
        defaultJsonMapper.toJavaObject(json, OrganizationFollowerStatistics.class);

    OrganizationFollowerStatisticsElement firstElement =
        organizationFollowerStatistics.getElements().get(0);

    FollowerCount totalFollowerCounts = firstElement.getTotalFollowerCounts();
    List<CountByStaffCountRange> followerCountsByStaffCountRange =
        firstElement.getFollowerCountsByStaffCountRange();
    List<CountByIndustry> followerCountsByIndustry = firstElement.getFollowerCountsByIndustry();
    List<CountByFunction> followerCountsByFunction = firstElement.getFollowerCountsByFunction();
    List<CountBySeniority> followerCountsBySeniority = firstElement.getFollowerCountsBySeniority();
    List<CountByAssociationType> followerCountsByAssociationType =
        firstElement.getFollowerCountsByAssociationType();

    Assert.assertEquals(33916, (long) totalFollowerCounts.getOrganicFollowerCount());
    Assert.assertEquals(268, (long) totalFollowerCounts.getPaidFollowerCount());
    Assert.assertEquals(6,
        (long) followerCountsByStaffCountRange.get(0).getFollowerCounts()
            .getOrganicFollowerCount());
    Assert.assertEquals("SIZE_2_TO_10", 
        followerCountsByStaffCountRange.get(1).getStaffCountRange());
    Assert.assertEquals(18,
        (long) followerCountsByIndustry.get(0).getFollowerCounts().getPaidFollowerCount());
    Assert.assertEquals("FUNDRAISING", followerCountsByIndustry.get(1).getIndustry());
    Assert.assertEquals(1662,
        (long) followerCountsByFunction.get(0).getFollowerCounts().getOrganicFollowerCount());
    Assert.assertEquals("urn:li:function:12", followerCountsByFunction.get(2).getFunction());
    Assert.assertEquals(1228,
        (long) followerCountsByAssociationType.get(0).getFollowerCounts()
            .getOrganicFollowerCount());
    Assert.assertEquals(1,
        (long) followerCountsBySeniority.get(0).getFollowerCounts().getOrganicFollowerCount());
    Assert.assertEquals("urn:li:seniority:3", followerCountsBySeniority.get(1).getSeniority());
    Assert.assertEquals("urn:li:organization:1234", firstElement.getOrganizationalEntity());
  }
}
