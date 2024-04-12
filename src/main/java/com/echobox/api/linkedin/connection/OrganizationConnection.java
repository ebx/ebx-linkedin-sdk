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
import com.echobox.api.linkedin.types.engagement.ShareStatistic;
import com.echobox.api.linkedin.types.organization.AccessControl;
import com.echobox.api.linkedin.types.organization.NetworkSize;
import com.echobox.api.linkedin.types.organization.Organization;
import com.echobox.api.linkedin.types.organization.OrganizationBase;
import com.echobox.api.linkedin.types.organization.OrganizationBrand;
import com.echobox.api.linkedin.types.organization.OrganizationResult;
import com.echobox.api.linkedin.types.statistics.OrganizationFollowerStatistics;
import com.echobox.api.linkedin.types.statistics.page.FollowerStatistic;
import com.echobox.api.linkedin.types.statistics.page.Statistics;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.URNEntityType;
import com.echobox.api.linkedin.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
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
public class OrganizationConnection extends Connection {
  
  /**
   * endpoint path
   */
  private static final String ORGANIZATIONS = "/organizations";
  private static final String ORGANIZATIONS_BRANDS = "/organizationBrands";
  private static final String ORGANIZATION_ACLS = "/organizationAcls";
  private static final String ORGANIZATIONAL_ENTITY_FOLLOWER_STATS =
      "/organizationalEntityFollowerStatistics";
  private static final String ORGANIZATIONAL_PAGE_STATS = "/organizationPageStatistics";
  private static final String NETWORK_SIZES = "/networkSizes";
  private static final String SHARE_STATISTICS = "/organizationalEntityShareStatistics";
  
  /**
   * Keys
   */
  private static final String VANITY_NAME_KEY = "vanityName";
  private static final String EMAIL_DOMAIN_KEY = "emailDomain";
  private static final String PARENT_KEY = "parent";
  private static final String ROLE_KEY = "role";
  private static final String STATE_KEY = "state";
  private static final String ORGANIZATION_KEY = "organization";
  private static final String ORGANIZATIONAL_ENTITY_KEY = "organizationalEntity";
  private static final String EDGE_TYPE_KEY = "edgeType";
  private static final String SHARES_PARAM_KEY = "shares";
  private static final String UGCPOSTS_PARAM_KEY = "ugcPosts";
  
  /**
   * Param value
   */
  private static final String VANITY_NAME_VALUE = "vanityName";
  private static final String EMAIL_DOMAIN_VALUE = "emailDomain";
  private static final String PARENT_ORGANIZATION_VALUE = "parentOrganization";
  private static final String ROLE_ASSIGNEE_VALUE = "roleAssignee";
  private static final String ORGANIZATION_VALUE = "organization";
  private static final String ORGANIZATIONAL_ENTITY_VALUE = "organizationalEntity";
  private static final String COMPANY_FOLLOWED_BY_MEMBER_VALUE = "COMPANY_FOLLOWED_BY_MEMBER";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public OrganizationConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
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
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#find-organization-by-vanity-name">
   * Retrieve organization</a>
   * @param vanityName the vanity name for the organization
   * @param fields the fields to request
   * @param count the number of entries to be returned per paged request
   * @return The organization with the vanity name
   */
  public List<OrganizationBase> findOrganizationByVanityName(String vanityName, Parameter fields,
      Integer count) {
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
  
  /**
   * Lookup an organization by email domain
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#find-organization-by-email-domain">
   * Find Organization by Email Domain</a>
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
   * Use organization brand id to find all all of its information
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#retrieve-an-administered-organization-brand">
   * Retrieve an organization brand</a>
   * @param organizationBrandURN organizationBrandURN
   * @param fields the fields to request
   * @return the organization brand
   */
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
  
  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#find-administered-organization-brands-by-parent-organization">
   * Retrieve Organization Brand by Parent Organization</a>
   * @param organizationURN parent organization URN
   * @return all the organization brands
   */
  public List<OrganizationBrand> retrieveOrganizationBrandByParentOrganization(
      URN organizationURN) {
    validateOrganizationURN("organizationURN", organizationURN);
    
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, PARENT_ORGANIZATION_VALUE));
    parameters.add(Parameter.with(PARENT_KEY, organizationURN.toString()));
    
    return getListFromQuery(ORGANIZATIONS, OrganizationBrand.class,
        parameters.toArray(new Parameter[0]));
  }
  
  /**
   * Find a Member's Organization Access Control Information
   * E.g. https://api.linkedin.com/rest/organizationAcls?q=roleAssignee
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control-by-role?view=li-lms-2022-11&tabs=http">
   * Access Control</a>
   * @param role Limit results to specific roles, such as ADMINISTRATOR.
   * @param state Limit results to specific role states, such as APPROVED.
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for a given role and state for the member
   */
  public List<AccessControl> retrieveMemberOrganizationAccessControl(String role, String state,
      Integer count) {
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ROLE_ASSIGNEE_VALUE));
    addRoleStateParams(role, state, params);
    addStartAndCountParams(params, null, count);
    
    return getListFromQuery(ORGANIZATION_ACLS, AccessControl.class,
        params.toArray(new Parameter[0]));
  }
  
  /**
   * Find an Organization's Access Control Information
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-access-control-by-role?view=li-lms-2022-11&tabs=http#find-organization-administrators">
   * Access Control</a>
   * @param organizationURN The organization for which access control information
   * is retrieved.
   * @param role Limit results to specific roles
   * @param state Limit results to specific role states
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for an organization
   */
  public List<AccessControl> findOrganizationAccessControl(URN organizationURN, String role,
      String state, Integer count) {
    validateOrganizationURN("organization", organizationURN);
    
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, ORGANIZATION_VALUE));
    parameters.add(Parameter.with(ORGANIZATION_KEY, organizationURN.toString()));
    addRoleStateParams(role, state, parameters);
    addStartAndCountParams(parameters, null, count);
    
    return getListFromQuery(ORGANIZATION_ACLS, AccessControl.class,
        parameters.toArray(new Parameter[0]));
  }
  
  /**
   * Retrieve the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/follower-statistics?view=li-lms-2022-11&tabs=http#retrieve-lifetime-follower-statistics">
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
  
    return getListFromQuery(ORGANIZATIONAL_ENTITY_FOLLOWER_STATS,
        OrganizationFollowerStatistics.class, parameters.toArray(new Parameter[0]));
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
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/follower-statistics?view=li-lms-2022-11&tabs=http#retrieve-time-bound-follower-statistics">
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
    
    return getListFromQuery(ORGANIZATIONAL_ENTITY_FOLLOWER_STATS, FollowerStatistic.class,
        parameters.toArray(new Parameter[0]));
  }
  
  /**
   * Retrieve the lifetime follower statistics. Providing the time interval will retrieve
   * time-bounded follower statistics, otherwise the lifetime follower statistics will be returned
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics?view=li-lms-2022-11&tabs=http#retrieve-lifetime-organization-page-statistics">
   * Organization Page Statistics - Lifetime</a>
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/page-statistics?view=li-lms-2022-11&tabs=http#retrieve-time-bound-organization-page-statistics">
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
   * Retrieve the number of first-degree connections (followers) for any organization.
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api?view=li-lms-2022-11&tabs=http#retrieve-organization-follower-count">
   * Retrieve Organization Follower Count</a>
   * @param organizationURN the organization URN
   * @return the number of followers for the organization
   */
  public Long retrieveOrganizationFollowerCount(URN organizationURN) {
    validateOrganizationOrBrandURN("organizationURN", organizationURN);
    NetworkSize networkSize = linkedinClient.fetchObject(NETWORK_SIZES + "/" + organizationURN,
        NetworkSize.class, Parameter.with(EDGE_TYPE_KEY, COMPANY_FOLLOWED_BY_MEMBER_VALUE));
    return networkSize.getFirstDegreeSize();
  }
  
  /**
   * retrieve both lifetime and time-bound organic statistics on shares for an organization,
   * including specific organization share URNs. This endpoint returns organic statistics only.
   * Sponsored activity is not counted in this endpoint.
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/share-statistics?view=li-lms-2022-11&tabs=http#retrieve-time-bound-share-statistics">
   * Organization Share Statistics</a>
   * @param organizationURN the organizational entity URN for which the statistics represents
   * @param timeInterval Time restriction for the query. When omitted, lifetime stats are returned
   * @param shareURNs References to one or more shares for which statistics are returned
   * @param count the number of entries to be returned per paged request
   * @return aggregated stats for an organization's shares
   */
  public List<ShareStatistic> retrieveShareStatistics(URN organizationURN,
      TimeInterval timeInterval, List<URN> shareURNs, Integer count) {
    validateOrganizationOrBrandURN("organizationURN", organizationURN);
    
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ORGANIZATIONAL_ENTITY_KEY));
    params.add(Parameter.with(ORGANIZATIONAL_ENTITY_KEY, organizationURN));
    
    if (shareURNs != null && !shareURNs.isEmpty()) {
      // Validate and separate out ugcPosts and share posts
      Pair<List<URN>, List<URN>> urns = getValidShareAndUGCPostURNs(shareURNs);
      if (!urns.getLeft().isEmpty()) {
        addParametersFromURNs(params, SHARES_PARAM_KEY, urns.getLeft());
      }
      if (!urns.getRight().isEmpty()) {
        addParametersFromURNs(params, UGCPOSTS_PARAM_KEY, urns.getRight());
      }
    }
    
    addTimeIntervalToParams(params, timeInterval);
    addStartAndCountParams(params, null, count);
    
    return getListFromQuery(SHARE_STATISTICS, ShareStatistic.class,
        params.toArray(new Parameter[0]));
  }
  
  /**
   * Look up a member's positions and related organization IDs.
   */
  public void findOrganizationsAssociatedToMemberPosition() {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  /**
   * Use organization parent URN to get a list of array of brands that belong to the specified
   * parent
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
   * Retrieve organization brand page statistics
   * @param organizationBrand the organization brand URN
   * @param timeInterval the time interval for time bounded statistics
   * @return list of page statistics for the organization brand
   */
  public List<Statistics.BrandStatistics> retrieveOrganizationBrandPageStatistics(
      URN organizationBrand, TimeInterval timeInterval) {
    throw new UnsupportedOperationException("Operation is not implemented yet.");
  }
  
  // Validation
  /**
   * Validate share urn.
   *
   * @param shareURN the share URN parameter to check
   */
  private void validateShareURN(URN shareURN) {
    ValidationUtils.verifyParameterPresence("share", shareURN);
    validateURN(URNEntityType.SHARE, shareURN);
  }
  
  private Pair<List<URN>, List<URN>> getValidShareAndUGCPostURNs(List<URN> shareURNs) {
    final List<URN> shares = new ArrayList<>();
    final List<URN> ugcPosts = new ArrayList<>();
    
    for (URN urn: shareURNs) {
      if (URNEntityType.SHARE.getEntityValue().equals(urn.getEntityType())) {
        shares.add(urn);
      } else if (URNEntityType.UGCPOST.getEntityValue().equals(urn.getEntityType())) {
        ugcPosts.add(urn);
      } else {
        final List<String> acceptableEntityTypes =
            Arrays.asList(URNEntityType.SHARE.getEntityValue(),
                URNEntityType.UGCPOST.getEntityValue());
        throw new IllegalArgumentException(
            "The URN should be type " + String.join(",", acceptableEntityTypes));
      }
    }
    return Pair.of(shares, ugcPosts);
  }
  
  
  private void validateOrganizationOrBrandURN(String paramName, URN organizationOrBrandURN) {
    ValidationUtils.verifyParameterPresence(paramName, organizationOrBrandURN);
    if (!(URNEntityType.ORGANIZATION.equals(organizationOrBrandURN.resolveURNEntityType())
        || URNEntityType.ORGANIZATIONBRAND.equals(organizationOrBrandURN.resolveURNEntityType()))) {
      throw new IllegalArgumentException(String.format("The URN should be type %s or %s",
          URNEntityType.ORGANIZATION, URNEntityType.ORGANIZATIONBRAND));
    }
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
  
  private void addRoleStateParams(String role, String state, List<Parameter> parameters) {
    if (StringUtils.isNotBlank(role)) {
      parameters.add(Parameter.with(ROLE_KEY, role));
    }
    if (StringUtils.isNotBlank(state)) {
      parameters.add(Parameter.with(STATE_KEY, state));
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
