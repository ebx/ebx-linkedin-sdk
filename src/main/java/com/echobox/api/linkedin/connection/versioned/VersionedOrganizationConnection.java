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
import com.echobox.api.linkedin.types.TimeInterval;
import com.echobox.api.linkedin.types.organization.AccessControl;
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
  private static final String ORGANIZATIONS_BRANDS = "/organizationBrands";
  private static final String ORGANIZATION_ACLS = "/organizationAcls";
  private static final String ORGANIZATIONAL_ENTITY_FOLLOWER_STATS =
      "/organizationalEntityFollowerStatistics";
  private static final String ORGANIZATIONAL_PAGE_STATS = "/organizationPageStatistics";
  
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
  
  /**
   * Param value
   */
  private static final String VANITY_NAME_VALUE = "vanityName";
  private static final String EMAIL_DOMAIN_VALUE = "emailDomain";
  private static final String PARENT_ORGANIZATION_VALUE = "parentOrganization";
  private static final String ROLE_ASSIGNEE_VALUE = "roleAssignee";
  private static final String ORGANIZATION_VALUE = "organization";
  private static final String ORGANIZATIONAL_ENTITY_VALUE = "organizationalEntity";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public VersionedOrganizationConnection(VersionedLinkedInClient linkedinClient) {
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
   * @param projection Field projection
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for a given role and state for the member
   */
  public List<AccessControl> retrieveMemberOrganizationAccessControl(String role, String state,
      Parameter projection, Integer count) {
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ROLE_ASSIGNEE_VALUE));
    addRoleStateParams(role, state, projection, params);
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
   * @param projection Field projection
   * @param count the number of entries to be returned per paged request
   * @return List of access controls for an organization
   */
  public List<AccessControl> findOrganizationAccessControl(URN organizationURN,
      String role, String state, Parameter projection, Integer count) {
    validateOrganizationURN("organization", organizationURN);
    
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, ORGANIZATION_VALUE));
    parameters.add(Parameter.with(ORGANIZATION_KEY, organizationURN.toString()));
    addRoleStateParams(role, state, projection, parameters);
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
  
  // Validation
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
