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
import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
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
    
    Assert.assertFalse(organization.getLocalizedDescription().isEmpty());
    Assert.assertEquals(1, organization.getLocations().size());
    LocationInfo locationInfo = organization.getLocations().get(0);
    Assert.assertEquals("SIZE_1", locationInfo.getStaffCountRange());
    Assert.assertEquals("HEADQUARTERS", locationInfo.getLocationType());
    Assert.assertEquals("94043", locationInfo.getAddress().getPostalCode());
    Assert.assertEquals("US", locationInfo.getAddress().getCountry());
    Assert.assertEquals("CA", locationInfo.getAddress().getGeographicArea());
    Assert.assertEquals("2029 Stierlin Court", locationInfo.getAddress().getLine1());
    Assert.assertEquals("Mountain View", locationInfo.getAddress().getCity());
    
    Assert.assertEquals("ACTIVE", organization.getEntityStatus());
    
    CroppedImage logo = organization.getLogo();
    CropInfo cropInfo = logo.getCropInfo();
    Assert.assertEquals(10, cropInfo.getXAxis());
    Assert.assertEquals(10, cropInfo.getYAxis());
    Assert.assertEquals(800, cropInfo.getWidth());
    Assert.assertEquals(800, cropInfo.getHeight());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAAH-AAAAJDRRlZTNlZDQwLTk4YTIt.png", 
        logo.getOriginal().toString());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAANyAAAAJGRlZTNlZDQwLTk4YTIt.png", 
        logo.getCropped().toString());
    
    Assert.assertEquals("linkedin", organization.getVanityName());
    
    Assert.assertTrue(organization.getAlternativeNames().isEmpty());

    Assert.assertEquals(1337, organization.getId());
    
    Assert.assertEquals(1, organization.getIndustries().size());
    Assert.assertEquals("urn:li:industry:47", organization.getIndustries().get(0).toString());
    
    Assert.assertEquals("urn:li:organization:1337", organization.getUrn().toString());
    
    Assert.assertEquals("LinkedIn", organization.getLocalizedName());
    
    Assert.assertEquals(new Integer(2003), organization.getFoundedOn().getYear());

    Assert.assertEquals("http://www.linkedin.com", organization.getLocalizedWebsite());

    MultiLocaleString website = organization.getWebsite();
    Assert.assertEquals("US", website.getPreferredLocale().getCountry());
    Assert.assertEquals("en", website.getPreferredLocale().getLanguage());
    Assert.assertEquals("http://www.linkedin.com", website.getLocalized().get("en_US"));

    Assert.assertEquals("OPERATING", organization.getOrganizationStatus());
    
    MultiLocaleString description = organization.getDescription();
    Assert.assertEquals("US", description.getPreferredLocale().getCountry());
    Assert.assertEquals("en", description.getPreferredLocale().getLanguage());
    Assert.assertTrue(description.getLocalized().containsKey("ja_JP"));
    Assert.assertTrue(description.getLocalized().containsKey("en_US"));

    Assert.assertEquals("PUBLIC_COMPANY", organization.getOrganizationType());
    
    Assert.assertTrue(organization.getGroups().isEmpty());
    
    Assert.assertEquals("US", organization.getDefaultLocale().getCountry());
    Assert.assertEquals("en", organization.getDefaultLocale().getLanguage());

    Assert.assertEquals(8, organization.getLocalizedSpecialties().size());

    MultiLocaleString name = organization.getName();
    Assert.assertEquals("US", name.getPreferredLocale().getCountry());
    Assert.assertEquals("en", name.getPreferredLocale().getLanguage());
    Assert.assertEquals(22, name.getLocalized().size());

    Assert.assertEquals("SIZE_5001_TO_10000", organization.getStaffCountRange());
    
    List<Specialty> specialties = organization.getSpecialties();
    Assert.assertEquals(1, specialties.size());
    Specialty specialty = specialties.get(0);
    Assert.assertEquals("US", specialty.getLocale().getCountry());
    Assert.assertEquals("en", specialty.getLocale().getLanguage());
    Assert.assertEquals(8, specialty.getTags().size());

    CroppedImage coverPhoto = organization.getCoverPhoto();
    CropInfo cropInfoCoverPhoto = coverPhoto.getCropInfo();
    Assert.assertEquals(0, cropInfoCoverPhoto.getXAxis());
    Assert.assertEquals(0, cropInfoCoverPhoto.getYAxis());
    Assert.assertEquals(646, cropInfoCoverPhoto.getWidth());
    Assert.assertEquals(220, cropInfoCoverPhoto.getHeight());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAAfmAAAAJDQwLTk4YTItNGNlLWRj.png", 
        coverPhoto.getOriginal().toString());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAAkOAAAAJDA3NmYwMzRkLTA2MTAt.png", 
        coverPhoto.getCropped().toString());
  
    CroppedImage overviewPhotoV2 = organization.getOverviewPhotoV2();
    CropInfo cropOverviewPhotoV2 = overviewPhotoV2.getCropInfo();
    Assert.assertEquals(0, cropOverviewPhotoV2.getXAxis());
    Assert.assertEquals(0, cropOverviewPhotoV2.getYAxis());
    Assert.assertEquals(646, cropOverviewPhotoV2.getWidth());
    Assert.assertEquals(220, cropOverviewPhotoV2.getHeight());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAAfmAAAAJDQwLTk4YTItNGNlLWRj.png",
        overviewPhotoV2.getOriginal().toString());
    Assert.assertEquals("urn:li:media:/AAEAAQAAAAAAAAkOAAAAJDA3NmYwMzRkLTA2MTAt.png",
        overviewPhotoV2.getCropped().toString());
  }

}
