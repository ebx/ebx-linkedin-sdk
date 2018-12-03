/**
 * ***********************************************************************
 *
 * ECHOBOX CONFIDENTIAL
 *
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains the property of Echobox Ltd. and its
 * suppliers, if any. The intellectual and technical concepts contained herein are proprietary to
 * Echobox Ltd. and its suppliers and may be covered by Patents, patents in process, and are
 * protected by trade secret or copyright law. Dissemination of this information or reproduction of
 * this material, in any format, is strictly forbidden unless prior written permission is obtained
 * from Echobox Ltd.
 */

package com.echobox.api.linkedin.types.statistics;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author clementcaylux on 03/12/2018.
 */
public class StatisticsTest {

  /**
   * test that an statistics JSON is correctly deserialized
   * json taken from : https://developer.linkedin.com/docs/guide/v2/organizations/follower-statistics
   * 
   */
  @Test
  public void testEngagementJson() {
    
    String json = "{\n" + "  \"paging\": {\n" + "    \"count\": 10,\n" + "    \"start\": 0\n"
        + "  },\n" + "  \"elements\": [\n" + "    {\n" + "      \"totalFollowerCounts\": {\n"
        + "        \"organicFollowerCount\": 33916,\n" + "        \"paidFollowerCount\": 268\n"
        + "      },\n" + "      \"followerCountsByStaffCountRange\": [\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 6,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_1\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 29,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_2_TO_10\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 5,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_11_TO_50\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 29,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_51_TO_200\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 2,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_501_TO_1000\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_1001_TO_5000\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 25,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_5001_TO_10000\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 51,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"staffCountRange\": \"SIZE_10001_OR_MORE\"\n" + "        }\n" + "      ],\n"
        + "      \"followerCountsByIndustry\": [\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 0,\n"
        + "            \"paidFollowerCount\": 18\n" + "          },\n"
        + "          \"industry\": \"REAL_ESTATE\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 0,\n"
        + "            \"paidFollowerCount\": 34\n" + "          },\n"
        + "          \"industry\": \"FUNDRAISING\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 24,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"ACCOUNTING\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 0,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"EDUCATION_MANAGEMENT\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 2,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"COMPUTER_SOFTWARE\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 0,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"ALTERNATIVE_MEDICINE\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"ANIMATION\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"APPAREL_AND_FASHION\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 0,\n"
        + "            \"paidFollowerCount\": 12\n" + "          },\n"
        + "          \"industry\": \"LIBRARIES\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"industry\": \"INFORMATION_TECHNOLOGY_AND_SERVICES\"\n" + "        },\n"
        + "        {\n" + "          \"followerCounts\": {\n"
        + "            \"organicFollowerCount\": 1,\n" + "            \"paidFollowerCount\": 0\n"
        + "          },\n" + "          \"industry\": \"FINANCIAL_SERVICES\"\n" + "        }\n"
        + "      ],\n" + "      \"followerCountsByFunction\": [\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1662,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:22\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 21,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:11\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 3,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:12\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 674,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:13\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 62,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:25\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:15\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 5,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:16\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 9\n" + "          },\n"
        + "          \"function\": \"urn:li:function:17\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 22,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:18\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:1\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:2\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:3\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:4\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 2,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:5\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 3,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:6\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:7\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1237,\n"
        + "            \"paidFollowerCount\": 18\n" + "          },\n"
        + "          \"function\": \"urn:li:function:8\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 22,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"function\": \"urn:li:function:10\"\n" + "        }\n" + "      ],\n"
        + "      \"followerCountsByAssociationType\": [\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1228,\n"
        + "            \"paidFollowerCount\": 184\n" + "          }\n" + "        }\n"
        + "      ],\n" + "      \"followerCountsBySeniority\": [\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:2\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 2484,\n"
        + "            \"paidFollowerCount\": 18\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:3\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 799,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:4\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 708,\n"
        + "            \"paidFollowerCount\": 204\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:5\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 3,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:6\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 2,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:7\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 3,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:8\"\n" + "        },\n" + "        {\n"
        + "          \"followerCounts\": {\n" + "            \"organicFollowerCount\": 1,\n"
        + "            \"paidFollowerCount\": 0\n" + "          },\n"
        + "          \"seniority\": \"urn:li:seniority:9\"\n" + "        }\n" + "      ],\n"
        + "      \"organizationalEntity\": \"urn:li:organization:1234\"\n" + "    }\n" + "  ]\n"
        + "}\n";

    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    OrganizationFollowerStatistics organizationFollowerStatistics =
        defaultJsonMapper.toJavaObject(json, OrganizationFollowerStatistics.class);

    OrganizationFollowerStatisticsElement organizationFollowerStatisticsElement =
        organizationFollowerStatistics.getElements().get(0);

  }
}
