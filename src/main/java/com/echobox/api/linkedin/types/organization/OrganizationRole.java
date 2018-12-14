package com.echobox.api.linkedin.types.organization;

/**
 * A role defines the privileges that a member has within the organization
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
 * management/organizations/organization-access-control#organization-roles">
 * Organization Access Control Schema</a>
 * @author joanna
 *
 */
public enum OrganizationRole {
  
  /**
   * Access to administer an organizational entity. An administrator can post updates, edit the
   * organization's page, add other admins, view analytics, and view notifications.
   */
  ADMINISTRATOR,
  
  /**
   * Access to create direct sponsored content for an organizational entity.
   */
  DIRECT_SPONSORED_CONTENT_POSTER,
  
  /**
   * Access to post to an organizational entity.
   */
  RECRUITING_POSTER,
  
  /**
   * Access to view and manage landing pages for the company, as well as create new landing
   * pages or edit existing ones.
   */
  LEAD_CAPTURE_ADMINISTRATOR,
  
  /**
   * Access to retrieve leads that belong to a specific account which is associated with a company
   * page.
   */
  LEAD_GEN_FORMS_MANAGER;

}
