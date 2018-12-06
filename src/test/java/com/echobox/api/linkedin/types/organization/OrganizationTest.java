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
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.objectype.LocaleString;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * The type Organization test.
 * @author clementcaylux 
 */
public class OrganizationTest extends DefaultJsonMapperTestBase {

  /**
   * Test organization json can be deserialized.
   */
  @Test
  public void testOrganizationJsonCanBeDeserialized() {

    String json = readFileToString("com.echobox.api.linkedin.jsonmapper/organization.json");

    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    
    Organization organization = defaultJsonMapper.toJavaObject(json, Organization.class);
    List<LocaleString> alternativeNames = organization.getAlternativeNames();
    LocationInfo location = organization.getLocations().get(0);
    
    Assert.assertEquals("Mountain View", location.getAddress().getCity());
    Assert.assertEquals("HEADQUARTERS", location.getLocationType());
    Assert.assertEquals("linkedin", organization.getVanityName());
    Assert.assertEquals("OPERATING", organization.getOrganizationStatus());
    Assert.assertEquals("US", organization.getDescription().getPreferredLocale().getCountry());
    Assert.assertTrue(organization.getName().getLocalized().keySet().contains("fr_FR"));
    Assert.assertEquals("PUBLIC_COMPANY", organization.getOrganizationType());
    Assert.assertEquals("Online Professional Network", organization.getLocalizedSpecialties()
        .get(0));
    Assert.assertEquals("SIZE_5001_TO_10000", organization.getStaffCountRange());
  }

}
