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

## 1.0.3
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


## 1.0.4 (Work in progress)