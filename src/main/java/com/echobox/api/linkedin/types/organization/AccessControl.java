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

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;

/**
 * Access Control POJO
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control#find-access-control-information">
 * Access Control Schema</a>
 * @author joanna
 *
 */
public class AccessControl {
  
  @Getter
  @LinkedIn
  private String state;
  
  @Getter
  @LinkedIn
  private String role;
  
  @Getter
  @LinkedIn("roleAssignee")
  private URN roleAssigneeURN;
  
  @Getter
  @LinkedIn("roleAssignee~")
  private RoleAssignee roleAssignee;
  
  @Getter
  @LinkedIn("organizationalTarget")
  private URN organizationalTargetURN;
  
  @Getter
  @LinkedIn("organizationalTarget~")
  private OrganizationalTarget organizationalTarget;
  
  /**
   * Role Assignee
   * @author Joanna
   */
  public static class RoleAssignee {
    @Getter
    @LinkedIn
    private String localizedLastName;
    
    @Getter
    @LinkedIn
    private String localizedFirstName;
  }
  
  /**
   * Organization target
   * @author Joanna
   */
  public static class OrganizationalTarget {
    @Getter
    @LinkedIn
    private String localizedName;
  }
  
}
