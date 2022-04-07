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

package com.echobox.api.linkedin.types.social.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.ugc.UGCShare;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Test;

/**
 * CommentAction test
 * @author lukemartin
 */
public class CommentActionTest extends DefaultJsonMapperTestBase {
  
  @Test
  public void testAttributeGetCompanyValue() {
    String json =
        readFileToString("com.echobox.api.linkedin.jsonmapper/mentionAttribute.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    UGCShare ugcShare = defaultJsonMapper.toJavaObject(json, UGCShare.class);
  
    URN urn = new URN("urn:li:organization:1337");
  
    CommentAction.Attribute attribute =
        ugcShare.getSpecificContent().getShareContent().getShareCommentary().getAttributes().get(0);
  
    assertEquals(attribute.getCompanyValue().getCompany().getCompany().toString(),
        urn.toString());
  }
  
  @Test
  public void testAttributeSetCompanyValue() {
    URN urn = new URN("urn:li:organization:1337");
  
    CommentAction.CompanyURN companyURN = new CommentAction.CompanyURN();
    companyURN.setCompany(new URN("urn:li:organization:1337"));
  
    CommentAction.CompanyAttributedEntity companyAttributedEntity =
        new CommentAction.CompanyAttributedEntity();
    companyAttributedEntity.setCompany(companyURN);
  
    CommentAction.Attribute attribute = new CommentAction.Attribute();
    attribute.setCompanyValue(companyAttributedEntity);
    
    assertEquals(attribute.getCompanyValue().getCompany().getCompany().toString(),
        urn.toString());
    
  }
  
  @Test
  public void testAttributeGetMemberValue() {
    String json =
        readFileToString("com.echobox.api.linkedin.jsonmapper/mentionAttribute.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    UGCShare ugcShare = defaultJsonMapper.toJavaObject(json, UGCShare.class);
    
    URN urn = new URN("urn:li:person:1337");
    
    CommentAction.Attribute attribute =
        ugcShare.getSpecificContent().getShareContent().getShareCommentary().getAttributes().get(1);
    
    assertEquals(attribute.getMemberVaue().getMember().getMember().toString(),
        urn.toString());
    
  }
  
  @Test
  public void testAttributeSetMemberValue() {
    URN urn = new URN("urn:li:person:1337");
    
    CommentAction.MemberURN memberURN = new CommentAction.MemberURN();
    memberURN.setMember(new URN("urn:li:person:1337"));
    
    CommentAction.MemberAttributedEntity memberAttributedEntity =
        new CommentAction.MemberAttributedEntity();
    memberAttributedEntity.setMember(memberURN);
    
    CommentAction.Attribute attribute = new CommentAction.Attribute();
    attribute.setMemberVaue(memberAttributedEntity);
    
    assertEquals(attribute.getMemberVaue().getMember().getMember().toString(),
        urn.toString());
    
  }
  
  @Test
  public void testAttributeGetSetSchoolValue() {
    URN urn = new URN("urn:li:organization:1337");
    
    CommentAction.CompanyURN companyURN = new CommentAction.CompanyURN();
    companyURN.setCompany(new URN("urn:li:organization:1337"));
  
    CommentAction.SchoolAttributedEntity schoolAttributedEntity =
        new CommentAction.SchoolAttributedEntity();
    schoolAttributedEntity.setSchool(companyURN);
    
    CommentAction.Attribute attribute = new CommentAction.Attribute();
    attribute.setSchoolValue(schoolAttributedEntity);
  
    // SchoolAttributedEntity is now deprecated, must use CompanyAttributedEntity
    assertTrue(schoolAttributedEntity instanceof CommentAction.SchoolAttributedEntity);
    assertTrue(attribute.getSchoolValue() instanceof CommentAction.CompanyAttributedEntity);
    assertEquals(attribute.getSchoolValue().getCompany().getCompany().toString(),
        urn.toString());
    
  }
  
  
  
}
