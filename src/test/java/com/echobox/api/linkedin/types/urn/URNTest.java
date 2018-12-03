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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author clementcaylux on 03/12/2018.
 */
public class URNTest {
  
  @Test
  public void testParseURNFromString() {
    String urnString = "urn:li:organization:1234";

    URN urn = URN.extractFromString(urnString);

    Assert.assertEquals(URNRessourceType.ORGANIZATION, urn.getEntityType());
    Assert.assertEquals("1234", urn.getId());

  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testThrowExpectionWhenURNStringNotPrecededByURN() {
    String urnString = "li:organization:1234";

    URN urn = URN.extractFromString(urnString);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testThrowExceptionWhenURNSTringIsNotLinkedIn() {
    String urnString = "urn:ebx:organization:1234";

    URN urn = URN.extractFromString(urnString);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testThrowExceptionWhenRessourceTypeNotFound() {
    String urnString = "urn:li:dontexist:1234";

    URN urn = URN.extractFromString(urnString);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testThrowExceptionWhenURNIncorrect() {
    String urnString = "urn:li:organization1234";

    URN urn = URN.extractFromString(urnString);
  }
  
}
