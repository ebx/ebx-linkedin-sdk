# ebx-linkedin-sdk Changelog

## 0.0.1 (November 6, 2018)

* Initial release

## 1.0.0 (March 29, 2019)

## 1.0.1 (April 2, 2019)

## 1.0.2 (July 22, 2019)

* Update incorrect spelling from UCG to UGC.
* Update `DebugHeaderInfo.toString()` so it is more verbose.
* Update `Organization.overviewPhotoV2` so it matches the LinkedIn
  organization documentation.
* Upload images bytes to LinkedIn for rich media uploads in addition to
  files.
* Update `TargetAudience.equals()`, `TargetAudience.hashcode()`,
  `Locale.equals()` and `Locale.hashcode()` methods using Lombok
  implementations.
* `Locale` and `TargetAudience` have relevant setter methods.
* Update method name `URN.getURNEntityType()` to
  `URN.resolveURNEntityType()`
* Add `Address.toString()` and `Locale.toString()` method.
* Add constructor to URN to allow entity type to construct a URN object.
* `OrganizationalTarget` class is public.
* `TotalShareStatistics` class is public with relevant getters and
  setters.
* `ConnectionBaseV2.addTimeIntervalToParams()` checks for null time
  interval.
* Add `ImageV2Elements` to extend V2 images to support cropped images
  (see:
  https://developer.linkedin.com/docs/ref/v2/media-migration#migration)
* Add `URNEntityType.UGCPost` and ensure all enums have the string
  representation of the urn. Instead of using `URNEntityType.name()`
  `URNEntityType.getEntityValue()` should be used to support UGCPosts.

## 1.0.3 (May 18, 2020)
*  `DefaultLinkedInClient.makeRequestAndProcessResponse` handles 401
   errors as `LinkedInOAuthException` rather than
   `LinkedInNetworkException`
*  `DefaultLinkedInClient.throwLinkedInResponseStatusExceptionIfNecessary`
   should handle errors if they do not have an `error` attribute in the
   JSON.
*  Add `Content-Lenght` for post requests in `DefaultWebRequester` (see: 
   [Error handling](https://docs.microsoft.com/en-us/linkedin/shared/api-guide/concepts/error-handling))
* Ensure HTTP status 204 (No Content) does not throw a
  `LinkedInException` as it's returned by `DELETE
  https://api.linkedin.com/v2/ugcPosts/{encoded ugcPostUrn|shareUrn}`
  [Delete UGC Posts](https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#delete-ugc-posts).
* Deprecate `RichMediaConnection` as LinkedIn has launched Media Assets 
  API (previously Vector Platform) to host media types such as images and 
  videos. LinkedIn is planning to deprecate the existing Rich Media 
  Platform by `Janurary 30, 2020`.
  See [migration guide](https://docs.microsoft.com/en-us/linkedin/shared/references/migrations/rich-media-platform-deprecation?context=linkedin/marketing/context&trk=eml-mktg-20191028-developer-email-api-updates-october&mcid=6592215409070530560&src=e-eml).
* Add `NATIVE_DOCUMENT`, `URN_REFERENCE`, `LIVE_VIDEO` to UGC ShareMediaCategory enum.
* Checkstyle plugin version bumped. Some code reformatted in order to satisfy new checkstyle rules.

## 1.0.4 (June 15, 2020)
* Put in a place an interim fix for Linkedin V2 pagination. It seems like LinkedIn's pagination 
is incorrectly returning the number of elements where the number of elements is less than the 
request amount even though there are in fact more results. This fix should continue to 
the next page until there are no more results.

## 1.0.5 (June 18, 2020)
* Accidentally skipped this version - no changes

## 1.0.6 (June 18, 2020)
* After receiving a response from LinkedIn on the issues surrounding V2 pagination where the 
`count` value in the response object does not match the number of elements returned or in fact in
some cases where not all elements are returned, it's best to not provide pagination parameters in
the request to the LinkedIn API until they have fixed it in the API. Pagination parameters will not
longer be provided by default in `DefaultLinkedInClient.fetchConnection()`. Instead, if no `count`
parameter is provided in the initial request, `V2PaginationImpl` will continue to iterate through
all pages until the number of elements in the response no longer equals the expected count. 
This will avoid infinitely looping over pages until an empty page is discovered.

## 1.0.7 (September 17, 2020)
* UGCShareConnection.retrieveUGCPostsByAuthors() now includes sortBy param to get posts
sorted by creation date.

## 1.0.8 (October 15, 2020)
* OrganizationConnection.retrieveOrganizationBrand will return an organization brand
 based on the organization URN.
* OrganizationConnection.retrieveOrganizationBrandByParentOrganization will return 
 all the organization brands linked to an orginzation  
 
## 2.0.0 (November 24, 2020)
* Updated all dependencies. 
* RemovedLinkedinLogger as EventLogger had been removed from slf4j-ext.

## 2.0.1 (Jan 13, 2020)
* Change `RegisterUploadRequestBody.supportedUploadMechanism` from `SupportedUploadMechanism ` to `List< SupportedUploadMechanism >`

## 3.0.0 (October 19, 2021)
* Change `OrganizationConnection.fetchMemberOrganizationAccessControl` and `OrganizationConnection.findOrganizationAccessControl`
to use `/OrganizationAcls` endpoint instead of `/OrganizationalEntityAcls` endpoint as the LinkedIn API will remove support for organisation entity ACLs  and replace it with organisation ACLs on October 30, 2021.

## 3.0.1 (Dec 2, 2021)
* Ensure `OrganizationConnection.retrieveOrganizationFollowerCount` and `OrgnizationConnection. retrieveShareStatistics`
can get data for both organization and organization brand pages.

## 3.0.2 (Dec 20, 2021)
* Update logback dependencies to move away from logback-classic and logback-core due to a 
  security vulnerability. See [CVE-2017-5929](https://nvd.nist.gov/vuln/detail/CVE-2017-5929) 
  for more information. Using ebx-structured-logging instead.

## 3.0.3 (Mar 25, 2022)
* Add attribute `primaryOrganizationType` in OrganizationBase class
* Update `findOrganizationByVanityName` to return `OrganizationBrand` if `primaryOrganizationType` is 
  `BRAND`.

## 3.0.4 (Apr 1, 2022)
* Update `JsonUtils.getValue` method to handle JsonNumeric values correctly.

## 3.0.5 (Apr 7, 2022)
* Updated `CommentAction.Attribute.value` to use AttributedEntity, to fix issue where share 
  requests with mentions aren't being persisted correctly.

## 3.0.6 (Sep 20, 2022)
* Accidentally released. This is the same as 3.0.5

## 3.1.0 (Sep 7, 2022)
* Add CommentConnection for posting comment to a post (by URN)

## 3.1.1 (Sep 29, 2022)
* Add explicit dependency for google-api-client

## 3.1.2 (Oct 13, 2022)
* Updated error handling so that the response body is included in the exception when a 422 error 
  is received after a request to the LinkedIn API
* Updated dependencies

## 3.1.3 (Oct 17, 2022)
* Add `application/x-wwww-form-urlenconded` header to obtain access token request and update additional header method to add headers as a list.

## 3.1.4 (Dec 9, 2022)
* Bump sl4j dependencies from 1.7.x to 2.0.x
* Removed google-api-services-oauth2 dependency
* Bump sevntu-checks dependencies from 1.42.0 to 1.44.0
* Fix getShares method in ShareConnection

## 4.0.0 (Jan 23, 2023)
* Bump google-api-client from 2.0.0 to 2.1.1
* Support LinkedIn Versioning API
* Deprecated V2 connection classes

## 4.1.0 (Jan 30, 2023)
* Better support for non-default versioned month
* Add boolean support to JsonUtils
* Add support for video
