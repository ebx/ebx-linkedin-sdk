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

package com.echobox.api.linkedin.types.assets;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * CompleteMultiPartUploadBodyTest
 * @author joanna
 */
public class CompleteMultiPartUploadBodyTest {
  
  @Test
  public void testCompleteMutliPartUploadBodyTest() {
    String json = "{\n" + "    \"completeMultipartUploadRequest\": {\n"
        + "        \"mediaArtifact\": \"urn:li:digitalmediaMediaArtifact:(urn:li:digitalmediaAsset"
        + ":C5500AQHGn3ZNkMNOCQ,urn:li:digitalmediaMediaArtifactClass:aws-userUploadedVideo)\",\n"
        + "        \"metadata\": \"eyJidWNrZXROYW1lIjoidmlkZW8tdXBsb2Fkcy1laSIsImtleU5hbWUiOiJDNTU"
        + "wMEFRSEduM1pOa01OT0NRL2F3cy11c2VyVXBsb2FkZWRWaWRlbyIsInVwbG9hZElkIjoicDB0djRSdE1ORThGN1"
        + "9haklGY21EN0lSeGNZNlhzTmp5ZlZCaE1qQ18yWjUxOUlaOHhOZm1vaFFFcGNPY0xSM1VXb080cWlnRFQ0YmN"
        + "kcExJMnhwZTgwa09aYzVZeHVDTzUxdS5FVTVwVVA5cjhsRUFpUm1ZMEdYNmhDQUk1ODEiLCJhc3NldElkIjoi"
        + "QzU1MDBBUUhHbjNaTmtNTk9DUSIsIm1lZGlhQXJ0aWZhY3RJZCI6ImF3cy11c2VyVXBsb2FkZWRWaWRlby"
        + "J9\",\n"
        + "        \"partUploadResponses\": [\n" + "            {\n"
        + "                \"headers\": {\n"
        + "                    \"ETag\": \"d8a5ce5adcdac4063feabcda7183396e\"\n"
        + "                },\n" + "                \"httpStatusCode\": 200\n" + "            },\n"
        + "            {\n" + "                \"headers\": {\n"
        + "                    \"ETag\": \"24259a14365734f1f1b4abcdb5e55d01e\"\n"
        + "                },\n" + "                \"httpStatusCode\": 200\n" + "            }\n"
        + "        ]\n" + "    }\n" + "}";
  
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    CompleteMultiPartUploadBody completeMultiPartUploadBody = defaultJsonMapper.toJavaObject(json,
        CompleteMultiPartUploadBody.class);
  
    CompleteMultiPartUploadBody.CompleteMultiPartUpload completeMultipartUploadRequest =
        completeMultiPartUploadBody.getCompleteMultipartUploadRequest();
    
    Assert.assertEquals(new URN("urn:li:digitalmediaMediaArtifact:(urn:li:digitalmedia"
        + "Asset:C5500AQHGn3ZNkMNOCQ,urn:li:digitalmediaMediaArtifactClass:aws-userUploadedVideo)"),
        completeMultipartUploadRequest.getMediaArtifact());
    Assert.assertEquals(
        "eyJidWNrZXROYW1lIjoidmlkZW8tdXBsb2Fkcy1laSIsImtleU5hbWUiOiJDNTUwMEFRSEduM1pOa0"
            + "1OT0NRL2F3cy11c2VyVXBsb2FkZWRWaWRlbyIsInVwbG9hZElkIjoicDB0djRSdE1ORThGN19haklGY2"
            + "1EN0lSeGNZNlhzTmp5ZlZCaE1qQ18yWjUxOUlaOHhOZm1vaFFFcGNPY0xSM1VXb080cWlnRFQ0YmNkcExJ"
            + "MnhwZTgwa09aYzVZeHVDTzUxdS5FVTVwVVA5cjhsRUFpUm1ZMEdYNmhDQUk1ODEiLCJhc3NldElkIjoiQz"
            + "U1MDBBUUhHbjNaTmtNTk9DUSIsIm1lZGlhQXJ0aWZhY3RJZCI6ImF3cy11c2VyVXBsb2FkZWRWaWRlbyJ9",
        completeMultipartUploadRequest.getMetadata());
  
    List<CompleteMultiPartUploadBody.PartUploadResponses> partUploadResponses =
        completeMultipartUploadRequest.getPartUploadResponses();
    Assert.assertEquals(2, partUploadResponses.size());
    Assert.assertEquals("d8a5ce5adcdac4063feabcda7183396e",
        partUploadResponses.get(0).getHeaders().getETag());
    Assert.assertEquals(new Integer(200), partUploadResponses.get(0).getHttpStatusCode());
    Assert.assertEquals("24259a14365734f1f1b4abcdb5e55d01e",
        partUploadResponses.get(1).getHeaders().getETag());
    Assert.assertEquals(new Integer(200), partUploadResponses.get(1).getHttpStatusCode());
  }
  
}
