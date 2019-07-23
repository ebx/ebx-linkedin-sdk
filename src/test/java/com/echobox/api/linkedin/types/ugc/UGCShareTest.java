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
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.objectype.AuditStamp;
import com.echobox.api.linkedin.types.social.actions.CommentAction;
import com.echobox.api.linkedin.types.urn.URN;

import org.junit.Test;

import java.util.List;

/**
 * UGCShareTest
 * @author joanna
 */
public class UGCShareTest extends DefaultJsonMapperTestBase {
  
  @Test
  public void testUGCShareBody() {
    String json =
        readFileToString("com.echobox.api.linkedin.jsonmapper/ugcShareBody.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    UGCShare ugcShare = defaultJsonMapper.toJavaObject(json, UGCShare.class);
    
    assertEquals(new URN("urn:li:organization:5590506"), ugcShare.getAuthor());
    assertEquals("PUBLISHED", ugcShare.getLifecycleState());
  
    ShareContent specificContent = ugcShare.getSpecificContent();
    ShareContent.ShareContentBody shareContent = specificContent.getShareContent();
    
    List<ShareMedia> media = shareContent.getMedia();
    assertEquals(1, media.size());
    ShareMedia shareMedia = media.get(0);
    assertTrue(shareMedia.getDescription().getAttributes().isEmpty());
    assertEquals("Sample Description", shareMedia.getDescription().getText());
    assertEquals(new URN("urn:li:digitalmediaAsset:C5500AQG7r2u00ByWjw"),
        shareMedia.getMedia());
    assertEquals("READY", shareMedia.getStatus());
    assertTrue(shareMedia.getThumbnails().isEmpty());
    assertTrue(shareMedia.getTitle().getAttributes().isEmpty());
    assertEquals("Sample Video Create", shareMedia.getTitle().getText());
  
    Commentary shareCommentary = shareContent.getShareCommentary();
    assertTrue(shareCommentary.getAttributes().isEmpty());
    assertEquals("Some share text", shareCommentary.getText());
    
    assertEquals("VIDEO", shareContent.getShareMediaCategory());
  
    TargetAudience targetAudience = ugcShare.getTargetAudience();
    List<TargetAudience.TargetAudienceEntity> targetedEntities =
        targetAudience.getTargetedEntities();
    assertEquals(1, targetedEntities.size());
    List<URN> locations = targetedEntities.get(0).getLocations();
    assertEquals(1, locations.size());
    assertEquals(new URN("urn:li:country:us"), locations.get(0));
  
    UGCShare.Visibility visibility = ugcShare.getVisibility();
    assertEquals("PUBLIC", visibility.getVisibility());
  }
  
  @Test
  public void testUGCShare() {
    String json =
        readFileToString("com.echobox.api.linkedin.jsonmapper/ugcShare.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    UGCShare ugcShare = defaultJsonMapper.toJavaObject(json, UGCShare.class);
    
    assertEquals(new URN("urn:li:person:123ABC"), ugcShare.getAuthor());
    assertEquals("{\"originCountryCode\":\"us\",\"modifiedAt\":1500590592795,"
        + "\"spamRestriction\":{\"classifications\":[],\"contentQualityClassifications"
        + "\":[],\"systemName\":\"MACHINE_SYNC\",\"lowQuality\":false,"
        + "\"contentClassificationTrackingId\":\"B6A8B437D1D5E59D123455F6DCE5B\""
        + ",\"contentRelevanceClassifications\":[],\"spam\":false}}",
        ugcShare.getContentCertificationRecord());
    assertEquals(new Long(1500590592702L), ugcShare.getFirstPublishedAt());
    assertEquals(new URN("urn:li:ugcPost:123456"), ugcShare.getId());
  
    AuditStamp lastModified = ugcShare.getLastModified();
    assertEquals("urn:li:person:123ABC", lastModified.getActor());
    assertEquals(new Long(1500590592806L), lastModified.getTime());
  
    assertEquals("PUBLISHED", ugcShare.getLifecycleState());
    
    ShareContent specificContent = ugcShare.getSpecificContent();
    ShareContent.ShareContentBody shareContent = specificContent.getShareContent();
    
    List<ShareMedia> media = shareContent.getMedia();
    assertEquals(1, media.size());
    ShareMedia shareMedia = media.get(0);
    assertEquals(new URN("urn:li:digitalmediaAsset:123ABDEFHAG"), shareMedia.getMedia());
    assertEquals("READY", shareMedia.getStatus());
    assertTrue(shareMedia.getThumbnails().isEmpty());
    
    Commentary shareCommentary = shareContent.getShareCommentary();
    assertEquals(1, shareCommentary.getAttributes().size());
    CommentAction.Attribute attribute = shareCommentary.getAttributes().get(0);
    assertEquals(new Integer(35), attribute.getLength());
    assertEquals(new Integer(66), attribute.getStart());
    CommentAction.CompanyAttributedEntity companyValue = attribute.getCompanyValue();
    CommentAction.CompanyURN company = companyValue.getCompany();
    assertEquals(new URN("urn:li:organization:12345"), company.getCompany());
    assertEquals("Testing a UGC Post!", shareCommentary.getText());
    
    assertEquals("VIDEO", shareContent.getShareMediaCategory());
    
    assertEquals("2", ugcShare.getVersionTag());
    
    UGCShare.Visibility visibility = ugcShare.getVisibility();
    assertEquals("PUBLIC", visibility.getVisibility());
  }
  
}
