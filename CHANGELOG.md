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

## 1.0.8 (Work in progress)
* OrganizationConnection.retrieveOrganizationBrand will return an organization brand
 based on the organization URN.
 OrganizationConnection.retrieveOrganizationBrandByParentOrganization will return 
 all the organization brands linked to an orginzation  