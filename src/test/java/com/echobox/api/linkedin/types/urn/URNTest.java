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

package com.echobox.api.linkedin.types.urn;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for URN class
 * 
 * @author Alexandros
 */
public class URNTest {
  
  @Test
  public void testShareURNConstructor() {
    URN urn = new URN("urn:li:share:1");
    assertEquals(new URN(URNEntityType.SHARE, "1"), urn);
    assertEquals(URNEntityType.SHARE, urn.resolveURNEntityType());
  }
  
  @Test
  public void testUGCPostURNConstructor() {
    URN urn = new URN("urn:li:ugcPost:1");
    assertEquals(new URN(URNEntityType.UGCPOST, "1"), urn);
    assertEquals(URNEntityType.UGCPOST, urn.resolveURNEntityType());
  }
  
  @Test
  public void testOrganizationURNConstructor() {
    URN urn = new URN("urn:li:organization:1");
    assertEquals(new URN(URNEntityType.ORGANIZATION, "1"), urn);
    assertEquals(URNEntityType.ORGANIZATION, urn.resolveURNEntityType());
  }
  
  @Test
  public void testPlaceURNConstructor() {
    URN urn = new URN("urn:li:place:1");
    assertEquals(new URN(URNEntityType.PLACE, "1"), urn);
    assertEquals(URNEntityType.PLACE, urn.resolveURNEntityType());
  }
  
  @Test
  public void testCountryURNConstructor() {
    URN urn = new URN("urn:li:country:1");
    assertEquals(new URN(URNEntityType.COUNTRY, "1"), urn);
    assertEquals(URNEntityType.COUNTRY, urn.resolveURNEntityType());
  }
  
  @Test
  public void testSeniorityURNConstructor() {
    URN urn = new URN("urn:li:seniority:1");
    assertEquals(new URN(URNEntityType.SENIORITY, "1"), urn);
    assertEquals(URNEntityType.SENIORITY, urn.resolveURNEntityType());
  }
  
  @Test
  public void testFunctionURNConstructor() {
    URN urn = new URN("urn:li:function:1");
    assertEquals(new URN(URNEntityType.FUNCTION, "1"), urn);
    assertEquals(URNEntityType.FUNCTION, urn.resolveURNEntityType());
  }
}
