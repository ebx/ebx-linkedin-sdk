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

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.objectype.AuditStamp;
import com.echobox.api.linkedin.types.urn.ContainsURN;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Comment Action
 * @author Alexandros
 */
public class CommentAction extends ContainsURN {
  
  @Getter
  @Setter
  @LinkedIn
  private Long id;
  
  @Getter
  @Setter
  @LinkedIn
  private URN actor;

  @Getter
  @Setter
  @LinkedIn
  private URN commentUrn;
  
  @Getter
  @Setter
  @LinkedIn
  private List<CommentContent> content;
  
  @Getter
  @Setter
  @LinkedIn
  private CommentMessage message;
  
  @Getter
  @Setter
  @LinkedIn
  private URN parentComment;
  
  @Getter
  @Setter
  @LinkedIn
  private LikesSummary likesSummary;
  
  @Getter
  @Setter
  @LinkedIn
  private URN object;

  @Getter
  @Setter
  @LinkedIn
  private AuditStamp created;

  @Getter
  @Setter
  @LinkedIn
  private AuditStamp lastModified;
  
  /**
   * A Model to describe the content of a comment
   */
  public static class CommentContent {
    
    @Getter
    @Setter
    @LinkedIn
    private String type;
    
    @Getter
    @Setter
    @LinkedIn
    private String url;

    @Getter
    @Setter
    @LinkedIn
    private CommentEntity entity;
  }

  public static class CommentEntity {

    @Getter
    @Setter
    @LinkedIn
    private URN digitalMediaAsset;
  }
  
  /**
   * A Model to describe the message of a comment
   */
  public static class CommentMessage {
    
    @Getter
    @Setter
    @LinkedIn
    private List<Attribute> attributes;
    
    @Getter
    @Setter
    @LinkedIn
    private String text;
  }
  
  /**
   * Attribute model
   */
  public static class Attribute {
    
    @Getter
    @Setter
    @LinkedIn
    private Integer length;
    
    @Getter
    @Setter
    @LinkedIn
    private Integer start;
    
    @LinkedIn("value")
    private CommentAction.AttributedEntity value;
  
    public CommentAction.CompanyAttributedEntity getCompanyValue() {
      CommentAction.CompanyAttributedEntity companyValue =
          new CommentAction.CompanyAttributedEntity();
      companyValue.setCompany(value.getCompany());
      return companyValue;
    }
  
    public void setCompanyValue(CommentAction.CompanyAttributedEntity companyValue) {
      if (value == null) {
        value = new AttributedEntity();
      }
      value.setCompany(companyValue.getCompany());
    }
  
    public CommentAction.MemberAttributedEntity getMemberVaue() {
      CommentAction.MemberAttributedEntity memberValue =
          new CommentAction.MemberAttributedEntity();
      memberValue.setMember(value.getMember());
      return memberValue;
    }
  
    public void setMemberVaue(CommentAction.MemberAttributedEntity memberValue) {
      if (value == null) {
        value = new AttributedEntity();
      }
      value.setMember(memberValue.getMember());
    }
  
    public CommentAction.CompanyAttributedEntity getSchoolValue() {
      CommentAction.CompanyAttributedEntity schoolValue =
          new CommentAction.CompanyAttributedEntity();
      schoolValue.setCompany(value.getSchool());
      return schoolValue;
    }
  
    public void setSchoolValue(CommentAction.SchoolAttributedEntity schoolValue) {
      if (value == null) {
        value = new AttributedEntity();
      }
      value.setSchool(schoolValue.getSchool());
    }
  }
  
  /**
   * Attributed Entity
   */
  public static class AttributedEntity {
  
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.CompanyAttributedEntity")
    private CompanyURN company;
  
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.MemberAttributedEntity")
    private MemberURN member;
  
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.SchoolAttributedEntity")
    private CompanyURN school;
    
  }
  
  /**
   * Company Attributed Entity
   */
  public static class CompanyAttributedEntity  {
    
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.CompanyAttributedEntity")
    private CompanyURN company;
  }
  
  /**
   * Company URN
   */
  public static class CompanyURN {
    
    @Setter
    @Getter
    @LinkedIn
    private URN company;
  }
  
  /**
   * Member Attributed Entity
   */
  public static class MemberAttributedEntity  {
    
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.MemberAttributedEntity")
    private MemberURN member;
  }
  
  /**
   * Member URN
   */
  public static class MemberURN {
    
    @Setter
    @Getter
    @LinkedIn
    private URN member;
  }
  
  /**
   * Company Attributed Entity
   */
  public static class SchoolAttributedEntity  {
    
    @Getter
    @Setter
    @LinkedIn("com.linkedin.common.SchoolAttributedEntity")
    private CompanyURN school;
    // TODO: organizationUrn??
  }
}
