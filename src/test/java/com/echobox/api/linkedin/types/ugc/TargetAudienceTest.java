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

package com.echobox.api.linkedin.types.ugc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.objectype.Locale;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Test;

import java.util.List;

/**
 * Target Audience Tests
 * @author Alexandros
 */
public class TargetAudienceTest extends DefaultJsonMapperTestBase {
  
  private DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
  
  private TargetAudience targetAudience = getInstance();
  
  @Test
  public void targetAudienceTest() {
    assertNotNull(targetAudience);
    assertEquals(1, targetAudience.getTargetedEntities().size());
  
    TargetAudience.TargetAudienceEntity entity = targetAudience.getTargetedEntities().get(0);
    assertEquals(2, entity.getDegrees().size());
    assertEquals(new URN("urn:li:degree:258"), entity.getDegrees().get(0));
  
    List<Locale> locales = entity.getInterfaceLocales();
    assertEquals(1, locales.size());
    assertEquals("US", locales.get(0).getCountry());
    assertEquals("en", locales.get(0).getLanguage());
  }
  
  @Test
  public void testEqualWithSameInstance() {
    assertEquals(targetAudience, targetAudience);
  }
  
  @Test
  public void testEqualsAndHashCodeWithDifferentInstance() {
    TargetAudience other = getInstance();
    assertEquals(targetAudience, other);
    assertEquals(targetAudience.hashCode(), other.hashCode());
  }
  
  @Test
  public void testNotEqualWithANewModifiedInstance() {
    String json = "{}";
    TargetAudience other = defaultJsonMapper.toJavaObject(json, TargetAudience.class);
    assertNotEquals(this.targetAudience, other);
    assertNotEquals(this.targetAudience.hashCode(), other.hashCode());
  }
  
  private TargetAudience getInstance() {
    String json = readFileToString("com.echobox.api.linkedin.jsonmapper/targetAudience.json");
    return defaultJsonMapper.toJavaObject(json, TargetAudience.class);
  }
}
