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

package com.echobox.api.linkedin.connection.versioned;

import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.VersionedLinkedInClient;
import com.echobox.api.linkedin.types.organization.OrganizationBase;
import com.echobox.api.linkedin.types.organization.OrganizationResult;
import com.echobox.api.linkedin.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Organization connection class to handle organization operations
 * @see
 * <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http">
 * Organization Lookup</a>
 *
 * @author Kenneth Wong
 *
 */
public class VersionedOrganizationConnection extends VersionedConnection {
  
  /**
   * endpoint path
   */
  private static final String ORGANIZATIONS = "/organizations";
  
  /**
   * Keys
   */
  private static final String VANITY_NAME_KEY = "vanityName";
  
  /**
   * Param value
   */
  private static final String VANITY_NAME_VALUE = "vanityName";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public VersionedOrganizationConnection(VersionedLinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Lookup an organization by vanity name
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#find-organization-by-vanity-name">
   * Retrieve organization</a>
   * @param vanityName the vanity name for the organization
   * @param fields the fields to request
   * @param count the number of entries to be returned per paged request
   * @return The organization with the vanity name
   */
  public List<OrganizationBase> findOrganizationByVanityName(String vanityName,
      Parameter fields, Integer count) {
    ValidationUtils.verifyParameterPresence(VANITY_NAME_VALUE, vanityName);
    
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    parameters.add(Parameter.with(QUERY_KEY, VANITY_NAME_VALUE));
    parameters.add(Parameter.with(VANITY_NAME_KEY, vanityName));
    addStartAndCountParams(parameters, null, count);
    List<OrganizationResult> organizationList = getListFromQuery(ORGANIZATIONS,
        OrganizationResult.class, parameters.toArray(new Parameter[0]));
    
    return organizationList.stream().map(OrganizationResult::getOrganization)
        .collect(Collectors.toList());
  }
  
}
