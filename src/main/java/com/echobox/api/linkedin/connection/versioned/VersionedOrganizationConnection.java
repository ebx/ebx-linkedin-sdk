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

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.organization.Organization;
import com.echobox.api.linkedin.types.organization.OrganizationBase;
import com.echobox.api.linkedin.types.organization.OrganizationBrand;
import com.echobox.api.linkedin.types.organization.OrganizationResult;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.URNEntityType;
import com.echobox.api.linkedin.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VersionedOrganizationConnection extends VersionedConnection {
  
  
  private static final String ORGANIZATIONS = "/organizations";
  private static final String ORGANIZATIONS_BRANDS = "/organizationBrands";
  
  private static final String VANITY_NAME_VALUE = "vanityName";
  private static final String VANITY_NAME_KEY = "vanityName";
  
  private static final String EMAIL_DOMAIN_KEY = "emailDomain";
  private static final String EMAIL_DOMAIN_VALUE = "emailDomain";
  
  private static final String PARENT_KEY = "parent";
  private static final String PARENT_ORGANIZATION_VALUE = "parentOrganization";
  
  public VersionedOrganizationConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  public VersionedOrganizationConnection(LinkedInClient linkedInClient, String version) {
    super(linkedInClient, version);
  }
  
  // CPD-OFF
  public List<OrganizationBase> findOrganizationByVanityName(String vanityName,
      Parameter fields, Integer count) {
    ValidationUtils.verifyParameterPresence(VANITY_NAME_KEY, vanityName);
    
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
  
  public Organization retrieveOrganization(URN organizationURN, Parameter fields) {
    validateOrganizationURN("organizationURN", organizationURN);
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    return linkedinClient.fetchObject(ORGANIZATIONS + "/" + organizationURN.getId(),
        Organization.class, parameters.toArray(new Parameter[0]));
  }
  
  public OrganizationBrand retrieveOrganizationBrand(URN organizationBrandURN, Parameter fields) {
    validateOrganizationBrandURN("organizationBrandURN", organizationBrandURN);
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    String id = organizationBrandURN.getId();
    return linkedinClient.fetchObject(ORGANIZATIONS_BRANDS + "/" + id,
        OrganizationBrand.class, parameters.toArray(new Parameter[0]));
  }
  
  private void validateOrganizationURN(String paramName, URN organizationURN) {
    validateURN(paramName, organizationURN, URNEntityType.ORGANIZATION);
    ValidationUtils.verifyParameterPresence(paramName, organizationURN);
    validateURN(URNEntityType.ORGANIZATION, organizationURN);
  }
  
  private void validateOrganizationBrandURN(String paramName, URN organizationBrandURN) {
    validateURN(paramName, organizationBrandURN, URNEntityType.ORGANIZATIONBRAND);
  }
  
  private void validateURN(String paramName, URN urn, URNEntityType type) {
    ValidationUtils.verifyParameterPresence(paramName, urn);
    validateURN(type, urn);
  }
  // CPD-ON
}
