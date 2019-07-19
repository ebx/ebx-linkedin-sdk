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
    assertEquals(new URN(URNEntityType.SHARE, "1"), new URN("urn:li:share:1"));
  }
  
  @Test
  public void testUGCPostURNConstructor() {
    assertEquals(new URN(URNEntityType.UGCPOST, "1"), new URN("urn:li:ugcPost:1"));
  }
  
  @Test
  public void testOrganizationURNConstructor() {
    assertEquals(new URN(URNEntityType.ORGANIZATION, "1"), 
        new URN("urn:li:organization:1"));
  }
  
  @Test
  public void testPlaceURNConstructor() {
    assertEquals(new URN(URNEntityType.PLACE, "1"), new URN("urn:li:place:1"));
  }
  
  @Test
  public void testCountryURNConstructor() {
    assertEquals(new URN(URNEntityType.COUNTRY, "1"), new URN("urn:li:country:1"));
  }
  
  @Test
  public void testSeniorityURNConstructor() {
    assertEquals(new URN(URNEntityType.SENIORITY, "1"), new URN("urn:li:seniority:1"));
  }
  
  @Test
  public void testFunctionURNConstructor() {
    assertEquals(new URN(URNEntityType.FUNCTION, "1"), new URN("urn:li:function:1"));
  }
}
