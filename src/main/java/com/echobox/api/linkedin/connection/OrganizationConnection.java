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

package com.echobox.api.linkedin.connection;

import com.echobox.api.linkedin.client.Connection;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.organization.AccessControl;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.version.Version;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Organization connection class that should contain all organization operations
 * 
 * @author joanna
 *
 */
public class OrganizationConnection extends ConnectionBase {

  protected OrganizationConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
    if (Version.V2.equals(linkedinClient.getVersion())) {
      throw new IllegalStateException(
          "The LinkedIn clinet should be set to V2 to access the endpoints");
    }
  }

  /**
   * Find a Member's Organization Access Control Information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-access-control">Access Control</a>
   * E.g. https://api.linkedin.com/v2/organizationalEntityAcls?q=roleAssignee
   * @param role Limit results to specific roles, such as ADMINISTRATOR.
   * @param state Limit results to specific role states, such as APPROVED.
   * @return List of access controls for a given role and state for the member
   */
  public List<AccessControl> fetchOrganizationAccessControl(String role, String state) {
    List<AccessControl> accessControl = new ArrayList<>();

    Parameter queryParam = Parameter.with("q", "roleAssignee");
    Parameter roleParam = Parameter.with("role", "roleAssignee");
    Parameter stateParam = Parameter.with("state", "roleAssignee");

    Connection<AccessControl> connection =
        linkedinClient.fetchConnection("/organizationalEntityAcls", AccessControl.class,
            queryParam, roleParam, stateParam);

    Iterator<List<AccessControl>> iterator = connection.iterator();
    while (iterator.hasNext()) {
      accessControl.addAll(iterator.next());
    }

    return accessControl;
  }

  /**
   * https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations
   */
  public static void retrieveOrganizations(long organizationId) {

  }

  /**
   * https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations
   * @param vanityName
   */
  public static void findOrganizationByVanityName(String vanityName) {

  }

  public static void findOrganizationByEmailDomain(String emailDomain) {

  }

  public static void findOrganizationsAssociatedToMemberPosition() {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organization-brands
   * @param organizationBrandId
   */
  public void retrieveOrganizationBrand(long organizationBrandId) {

  }

  public void retrieveOrganizationBrandByVanityName(String vanityName) {

  }

  public void retrieveOrganizationBrandByParentOrganization(URN organizationURN) {

  }

  public void retrieveOrganizationMediaContent(long organizationId) {

  }

}
