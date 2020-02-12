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

package com.echobox.api.linkedin.connection.v2;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.TimeInterval;
import com.echobox.api.linkedin.types.engagement.ShareStatistic;
import com.echobox.api.linkedin.types.organization.AccessControl;
import com.echobox.api.linkedin.types.organization.NetworkSize;
import com.echobox.api.linkedin.types.organization.Organization;
import com.echobox.api.linkedin.types.statistics.OrganizationFollowerStatistics;
import com.echobox.api.linkedin.types.statistics.page.FollowerStatistic;
import com.echobox.api.linkedin.types.statistics.page.Statistics;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.URNEntityType;
import com.echobox.api.linkedin.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Organization connection class that should contain all organization operations
 * @see
 * <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control">
 * Organization Access Contol</a>
 * 
 * @author joanna
 *
 */
public class OrganizationConnection extends ConnectionBaseV2 {

  private static final String ORGANIZATIONAL_ENTITY_ACLS = "/organizationalEntityAcls";
  private static final String ORGANIZATIONS = "/organizations";
  private static final String ORGANIZATIONAL_ENTITY_FOLOWER_STATS =
      "/organizationalEntityFollowerStatistics";
  private static final String ORGANIZATIONAL_PAGE_STATS = "/organizationPageStatistics";
  private static final String NETWORK_SIZES = "/networkSizes";
  private static final String SHARE_STATISTICS = "/organizationalEntityShareStatistics";
  
  private static final String ROLE_KEY = "role";
  private static final String STATE_KEY = "state";
  private static final String ORGANIZATIONAL_TARGET_KEY = "organizationalTarget";
  private static final String VANITY_NAME_KEY = "vanityName";
  private static final String EMAIL_DOMAIN_KEY = "emailDomain";
  private static final String ORGANIZATIONAL_ENTITY_KEY = "organizationalEntity";
  private static final String ORGANIZATION_KEY = "organization";
  
  private static final String ROLE_ASSIGNEE_VALUE = "roleAssignee";
  private static final String ORGANIZATION_TARGET_VALUE = "organizationalTarget";
  private static final String VANITY_NAME_VALUE = "vanityName";
  private static final String EMAIL_DOMAIN_VALUE = "emailDomain";
  private static final String ORGANIZATIONAL_ENTITY_VALUE = "organizationalEntity";
  private static final String ORGANIZATION_VALUE = "organization";
  private static final String COMPANY_FOLLOWED_BY_MEMEBER = "CompanyFollowedByMember";

  /**
   * Initialise an organization connection
   * @param linkedinClient the linkedIn API client to create a LinkedIn organization connection
   */
  public OrganizationConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }

  /**
   * Find a Member's Organization Access Control Information
   * E.g. https://api.linkedin.com/v2/organizationalEntityAcls?q=roleAssignee
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control">
   * Access Control</a>
   * @param role Limit results to specific roles, such as ADMINISTRATOR.
   * @param state Limit results to specific role states, such as APPROVED.
   * @param projection Field projection
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for a given role and state for the member
   */
  public List<AccessControl> fetchMemberOrganizationAccessControl(String role, String state,
      Parameter projection, Integer count) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, ROLE_ASSIGNEE_VALUE));
    addRoleStateParams(role, state, projection, parameters);
    addStartAndCountParams(parameters, null, count);

    return getListFromQuery(ORGANIZATIONAL_ENTITY_ACLS, AccessControl.class,
        parameters.toArray(new Parameter[0]));
  }

  /**
   * Find an Organization's Access Control Information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control">
   * Access Control</a>
   * @param organizationalTarget The organizational entity for which access control information
   * is retrieved.
   * @param role Limit results to specific roles
   * @param state Limit results to specific role states
   * @param projection Field projection
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for an organization
   */
  public List<AccessControl> findOrganizationAccessControl(URN organizationalTarget, String role,
      String state, Parameter projection, Integer count) {
    validateOrganizationURN("organizationalTarget", organizationalTarget);

    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, ORGANIZATION_TARGET_VALUE));
    parameters.add(Parameter.with(ORGANIZATIONAL_TARGET_KEY, organizationalTarget.toString()));
    addRoleStateParams(role, state, projection, parameters);
    addStartAndCountParams(parameters, null, count);

    return getListFromQuery(ORGANIZATIONAL_ENTITY_ACLS, AccessControl.class,
        parameters.toArray(new Parameter[0]));
  }

  /**
   * Find an organization using an organization ID, parent organization ID
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations">Retrieve organization</a>
   * @param organizationURN The organization URN
   * @param fields the fields to project
   * @return the requested organization
   */
  public Organization retrieveOrganization(URN organizationURN, Parameter fields) {
    validateOrganizationURN("organizationURN", organizationURN);
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    return linkedinClient.fetchObject(ORGANIZATIONS + "/" + organizationURN.getId(),
        Organization.class, parameters.toArray(new Parameter[0]));
  }

  /**
   * Lookup an organization by vanity name
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param vanityName the vanity name for the organization
   * @param fields the fields to request
   * @param count the number of entries to be returned per paged request
   * @return The organization with the vanity name
   */
  public List<Organization> findOrganizationByVanityName(String vanityName, Parameter fields,
      Integer count) {
    ValidationUtils.verifyParameterPresence("vanityName", vanityName);
  
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    parameters.add(Parameter.with(QUERY_KEY, VANITY_NAME_VALUE));
    parameters.add(Parameter.with(VANITY_NAME_KEY, vanityName));
    addStartAndCountParams(parameters, null, count);
    return getListFromQuery(ORGANIZATIONS, Organization.class,
        parameters.toArray(new Parameter[0]));
  }

  /**
   * Lookup an organization by email domain
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations">
   * Retrieve organization</a>
   * @param emailDomain the email domain for the organization
   * @param fields the fields to request
   * @param count the number of entries to be returned per paged request
   * @return A list of organizations with the email domain
   */
  public List<Organization> findOrganizationByEmailDomain(String emailDomain, Parameter fields,
      Integer count) {
    ValidationUtils.verifyParameterPresence("emailDomain", emailDomain);
  
    List<Parameter> parameters = new ArrayList<>();
    if (fields != null) {
      parameters.add(fields);
    }
    parameters.add(Parameter.with(QUERY_KEY, EMAIL_DOMAIN_VALUE));
    parameters.add(Parameter.with(EMAIL_DOMAIN_KEY, emailDomain));
    addStartAndCountParams(parameters, null, count);
    return getListFromQuery(ORGANIZATIONS, Organization.class,
        parameters.toArray(new Parameter[0]));
  }
  
  /**
   * Retrieve the number of first-degree connections (followers) for any organization.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organization-follower-count">
   * Retrieve Organization Follower Count</a>
   * @param organizationURN the organization URN
   * @return the number of followers for the organization
   */
  public Long retrieveOrganizationFollowerCount(URN organizationURN) {
    validateOrganizationURN("organizationURN", organizationURN);
    NetworkSize networkSize = linkedinClient.fetchObject(NETWORK_SIZES + "/" + organizationURN,
        NetworkSize.class, Parameter.with(EDGE_TYPE, COMPANY_FOLLOWED_BY_MEMEBER));
    return networkSize.getFirstDegreeSize();
  }

  /**
   * Look up a member's positions and related organization IDs.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api">
   * Find Organizations Associated with a Member's Positions</a>
   */
  public void findOrganizationsAssociatedToMemberPosition() {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization brand id to find all all of its information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve an organization brand</a>
   * @param organizationBrandId organizationBrandId
   */
  public void retrieveOrganizationBrand(long organizationBrandId) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization vanity name to find all all of its information
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve Organization Brand by Vanity Name</a>
   * @param vanityName vanity name
   */
  public void retrieveOrganizationBrandByVanityName(String vanityName) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organization-brands">
   * Retrieve Organization Brand by Parent Organization</a>
   * @param organizationURN parent organization URN
   */
  public void retrieveOrganizationBrandByParentOrganization(URN organizationURN) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }

  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-media-content-using-organizations-and-organization-brands">
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
   * Retrieve the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/follower-statistics">
   * Organization Follower Statistics</a>
   * @param organizationURN the organization URN to retrieve the follower statistics
   * @param count the number of entries to be returned per paged request
   * @return a list of organization's follower statistics
   */
  public List<OrganizationFollowerStatistics> retrieveOrganizationFollowerStatistics(
      URN organizationURN, Integer count) {
    validateOrganizationURN("organizationURN", organizationURN);
    List<Parameter> parameters = new ArrayList<>();

    addParametersForStatistics(organizationURN, null, parameters);
    addStartAndCountParams(parameters, null, count);

    return getListFromQuery(ORGANIZATIONAL_ENTITY_FOLOWER_STATS,
        OrganizationFollowerStatistics.class,
        parameters.toArray(new Parameter[0]));
  }
  
  /**
   * Retrieve both lifetime and time-bound statistics on followers for an organization.
   * Lifetime follower statistics: To retrieve lifetime follower statistics, omit the
   * timeIntervals query parameter. The API returns follower counts segmented by various facets
   * such as region and industry.
   *
   * Time-bound follower statistics: To retrieve time-bound follower statistics, include the
   * timeIntervals query parameter. The API returns the aggregate follower count for both paid
   * and organic followers during the days or months of the selected date range, based on the
   * specified timeIntervals.timeGranularityType.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/follower-statistics">
   * Organization Follower Statistics</a>
   * @param organizationURN the organization RUN
   * @param timeInterval the time interval for time bound follower statistics
   * @param count the number of entries to be returned per paged request
   * @return a list of organization follower statistics
   */
  public List<FollowerStatistic> retrieveOrganizationFollowerStatistics(URN organizationURN,
      TimeInterval timeInterval, Integer count) {
    validateOrganizationURN("organizationURN", organizationURN);
    
    List<Parameter> parameters = new ArrayList<>();
    
    addParametersForStatistics(organizationURN, timeInterval, parameters);
    addStartAndCountParams(parameters, null, count);
    
    return getListFromQuery(ORGANIZATIONAL_ENTITY_FOLOWER_STATS, FollowerStatistic.class,
        parameters.toArray(new Parameter[0]));
  }

  /**
   * Retrieve the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics#retrieve-lifetime-organization-page-statistics">
   * Organization Page Statistics - Lifetime</a>
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics#retrieve-time-bound-organization-page-statistics">
   * Organization Page Statistics - time-bound</a>
   * @param organizationURN the organization URN to retrieve the page statistics
   * @param timeInterval the time interval  for time-bound follower statistics
   * @param count the number of entries to be returned per paged request
   * @return a list of organization's follower statistics
   */
  public List<Statistics.OrganizationStatistics> retrieveOrganizationPageStatistics(
      URN organizationURN, TimeInterval timeInterval, Integer count) {
    validateOrganizationURN("organizationURN", organizationURN);

    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, ORGANIZATION_VALUE));
    parameters.add(Parameter.with(ORGANIZATION_KEY, organizationURN.toString()));

    addTimeIntervalToParams(parameters, timeInterval);
    addStartAndCountParams(parameters, null, count);

    return getListFromQuery(ORGANIZATIONAL_PAGE_STATS, Statistics.OrganizationStatistics.class,
        parameters.toArray(new Parameter[0]));
  }

  /**
   * Retrieve organization brand page statistics
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics#retrieve-organization-brand-page-statistics">
   * Organization Brand Page Statistics</a>
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics#retrieve-organization-brand-time-bound-page-statistics">
   * Organization Brand Page Statistics - time-bound</a>
   * @param organizationBrand the organization brand URN
   * @param timeInterval the time interval for time bounded statistics
   * @return list of page statistics for the organization brand
   */
  public List<Statistics.BrandStatistics> retrieveOrganizationBrandPageStatistics(
      URN organizationBrand, TimeInterval timeInterval) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  /**
   * retrieve both lifetime and time-bound organic statistics on shares for an organization,
   * including specific organization share URNs. This endpoint returns organic statistics only.
   * Sponsored activity is not counted in this endpoint.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/share-statistics">
   * Organization Share Statistics</a>
   * @param organizationURN the organizational entity URN for which the statistics represents
   * @param timeInterval Time restriction for the query. When omitted, lifetime stats are returned
   * @param shareURNs References to one or more shares for which statistics are returned
   * @param count the number of entries to be returned per paged request
   * @return aggregated stats for an organization's shares
   */
  public List<ShareStatistic> retrieveShareStatistics(URN organizationURN,
      TimeInterval timeInterval, List<URN> shareURNs, Integer count) {
    validateOrganizationURN("organizationURN", organizationURN);
    
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ORGANIZATIONAL_ENTITY_KEY));
    params.add(Parameter.with(ORGANIZATIONAL_ENTITY_KEY, organizationURN));
    
    if (shareURNs != null && !shareURNs.isEmpty()) {
      shareURNs.forEach(this::validateShareURN);
      addParametersFromURNs(params, SHARES_PARAM, shareURNs);
    }
  
    addTimeIntervalToParams(params, timeInterval);
    addStartAndCountParams(params, null, count);
    
    return getListFromQuery(SHARE_STATISTICS, ShareStatistic.class,
        params.toArray(new Parameter[0]));
  }

  private void validateOrganizationURN(String paramName, URN organizationURN) {
    ValidationUtils.verifyParameterPresence(paramName, organizationURN);
    validateURN(URNEntityType.ORGANIZATION, organizationURN);
  }
  
  private void addRoleStateParams(String role, String state, Parameter projection,
      List<Parameter> parameters) {
    if (StringUtils.isNotBlank(role)) {
      parameters.add(Parameter.with(ROLE_KEY, role));
    }
    if (StringUtils.isNotBlank(state)) {
      parameters.add(Parameter.with(STATE_KEY, state));      
    }
    if (projection != null) {
      parameters.add(projection);
    }
  }
  
  private void addParametersForStatistics(URN organizationURN, TimeInterval timeInterval,
      List<Parameter> parameters) {
    validateOrganizationURN("organizationURN", organizationURN);

    parameters.add(Parameter.with(QUERY_KEY, ORGANIZATIONAL_ENTITY_VALUE));
    parameters.add(Parameter.with(ORGANIZATIONAL_ENTITY_KEY, organizationURN.toString()));

    addTimeIntervalToParams(parameters, timeInterval);
  }

}
