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

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.TimeInterval;
import com.echobox.api.linkedin.types.organization.AccessControl;
import com.echobox.api.linkedin.types.organization.Organization;
import com.echobox.api.linkedin.types.statistics.page.Statistics;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.URNEntityType;
import com.echobox.api.linkedin.version.Version;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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
   * @param projection Field projection
   * @return List of access controls for a given role and state for the member
   */
  public List<AccessControl> fetchMemeberOrganizationAccessControl(String role, String state,
      Parameter projection) {
    Parameter queryParam = Parameter.with("q", "roleAssignee");
    Parameter roleParam = Parameter.with("role", role);
    Parameter stateParam = Parameter.with("state", state);

    return getListFromQuery("/organizationalEntityAcls", AccessControl.class,
        projection, queryParam, roleParam, stateParam);
  }

  /**
   * Find an Organization's Access Control Information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-access-control">Access Control</a>
   * E.g. https://api.linkedin.com/v2/organizationalEntityAcls?q=organizationalTarget
   * &organizationalTarget={organization URN}&role=ADMINISTRATOR&state=APPROVED
   * @param organizationalTarget The organizational entity for which access control information
   * is retrieved.
   * @param role Limit results to specific roles
   * @param state Limit results to specific role states
   * @param projection Field projection
   * @return List of access controls for an organization
   */
  public List<AccessControl> findOrganizationAccessControl(URN organizationalTarget, String role,
      String state, Parameter projection) {
    Parameter queryParam = Parameter.with("q", "organizationalTarget");
    Parameter organizationalTargetParam =
        Parameter.with("organizationalTarget", organizationalTarget.toString());
    Parameter roleParam = Parameter.with("role", role);
    Parameter stateParam = Parameter.with("state", state);

    return getListFromQuery("/organizationalEntityAcls", AccessControl.class, projection,
        queryParam, organizationalTargetParam, roleParam, stateParam);
  }

  /**
   * Find an organization using an organization ID, parent organization ID from the URN
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param organizationURN The organization URN
   * @param fields the fields to project
   * @return the requested organization by the given URN
   */
  public Organization retrieveOrganizationByURN(URN organizationURN, Parameter fields) {
    if (!URNEntityType.ORGANIZATION.equals(organizationURN.getURNEntityType())) {
      throw new IllegalArgumentException("The URN should be type organization");
    }
    return retrieveOrganization(Long.parseLong(organizationURN.getId()), fields);
  }

  /**
   * Find an organization using an organization ID, parent organization ID
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param organizationId The organization id
   * @param fields the fields to project
   * @return the requested organization
   */
  public Organization retrieveOrganization(long organizationId, Parameter fields) {
    return linkedinClient.fetchObject("organizations/" + organizationId, Organization.class,
        fields);
  }

  /**
   * Lookup an organization by vanity name
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param vanityName the vanity name for the organization
   * @param fields the fields to request
   * @return The organization with the vanity name
   */
  public Organization findOrganizationByVanityName(String vanityName, Parameter fields) {
    Parameter queryParam = Parameter.with("q", "vanityName");
    Parameter vanityNameParam = Parameter.with("vanityName", vanityName);
    return linkedinClient.fetchObject("organizations", Organization.class,
        fields, queryParam, vanityNameParam);
  }

  /**
   * Lookup an organization by email domain
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param emailDomain the email domain for the organization
   * @param fields the fields to request
   * @return A list of organizations with the email domain
   */
  public List<Organization> findOrganizationByEmailDomain(String emailDomain, Parameter fields) {
    Parameter queryParam = Parameter.with("q", "emailDomain");
    Parameter emailDomainParam = Parameter.with("emailDomain", emailDomain);
    return getListFromQuery("organizations", Organization.class, fields, queryParam,
        emailDomainParam);
  }

  /**
   * Look up a member's positions and related organization IDs.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api">
   * Find Organizations Associated with a Member's Positions</a>
   */
  public void findOrganizationsAssociatedToMemberPosition() {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization brand id to find all all of its information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve an organization brand</a>
   * @param organizationBrandId organizationBrandId
   */
  public void retrieveOrganizationBrand(long organizationBrandId) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization vanity name to find all all of its information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve Organization Brand by Vanity Name</a>
   * @param vanityName vanity name
   */
  public void retrieveOrganizationBrandByVanityName(String vanityName) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve Organization Brand by Parent Organization</a>
   * @param organizationURN parent organization URN
   */
  public void retrieveOrganizationBrandByParentOrganization(URN organizationURN) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/organization-lookup-api#retrieve-media-content-using-organizations-
   * and-organization-brands">
   * Retrieve Organization's Media Content</a>
   * @param organizationId organization id
   */
  public void retrieveOrganizationMediaContent(long organizationId) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  /**
   * Search for organizations
   * @param searchTerm the search term
   * @return a list of organizations that match the organization
   */
  public List<Organization> searchForOrganizations(String searchTerm) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  /**
   * Retreive the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
   * community-management/organizations/follower-statistics">
   * Organization Follower Statistics</a>
   * @param organizationURN the organization URN to retrieve the follower statistics
   * @param timeInterval the time interval  for time-bound follower statistics
   * @return a list of organization's follower statistics
   */
  public List<Statistics> retrieveOrganizationFollowerStatistics(URN organizationURN,
      TimeInterval timeInterval) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with("q", "organizationalEntity"));
    parameters.add(Parameter.with("organizationalEntity", organizationURN.toString()));
    
    addTimeIntervalToQueryParameters(timeInterval, parameters);
    
    return getListFromQuery("/organizationalEntityFollowerStatistics", Statistics.class,
        parameters.toArray(new Parameter[parameters.size()]));
  }
  
  /**
   * Retreive the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
   * community-management/organizations/page-statistics#retrieve-lifetime-organization-page-
   * statistics">
   * Organization Page Statistics - Lifetime</a>
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
   * community-management/organizations/page-statistics#retrieve-time-bound-organization-page-
   * statistics">
   * Organization Page Statistics - time-bound</a>
   * @param organizationURN the organization URN to retrieve the page statistics
   * @param timeInterval the time interval  for time-bound follower statistics
   * @return a list of organization's follower statistics
   */
  public List<Statistics> retrieveOrganizationPageStatistics(URN organizationURN,
      TimeInterval timeInterval) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with("q", "organization"));
    parameters.add(Parameter.with("organization", organizationURN.toString()));
    
    addTimeIntervalToQueryParameters(timeInterval, parameters);
    
    return getListFromQuery("/organizationalEntityFollowerStatistics", Statistics.class,
        parameters.toArray(new Parameter[parameters.size()]));
  }
  
  /**
   * 
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
   * community-management/organizations/page-statistics#retrieve-organization-brand-page-
   * statistics">
   * Organization Brand Page Statistics</a>
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/
   * community-management/organizations/page-statistics#retrieve-organization-brand-time-bound-
   * page-statistics">
   * Organization Brand Page Statistics - time-bound</a>
   * @param organizationBrand the organization brand URN
   * @param timeInterval the time interval for time bounded statistics
   * @return list of page statistics for the organization brand
   */
  public List<Statistics> retrieveOrganizationBrandPageStatistics(URN organizationBrand,
      TimeInterval timeInterval) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  private void addTimeIntervalToQueryParameters(TimeInterval timeInterval,
      List<Parameter> parameters) {
    if (timeInterval != null) {
      if (!StringUtils.isBlank(timeInterval.getTimeGranularityType())) {
        parameters.add(Parameter.with("timeIntervals.timeRange",
            timeInterval.getTimeGranularityType()));
      }
      if (timeInterval.getTimeRange() != null && timeInterval.getTimeRange().getStart() != null
          && timeInterval.getTimeRange().getEnd() != null) {
        parameters.add(Parameter.with("timeIntervals.timeRange.start",
            timeInterval.getTimeRange().getStart()));
        parameters.add(Parameter.with("timeIntervals.timeRange.end",
            timeInterval.getTimeRange().getEnd()));
      }
    }
  }

}
