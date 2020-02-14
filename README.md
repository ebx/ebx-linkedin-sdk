[![Maven Central](https://img.shields.io/maven-central/v/com.echobox/ebx-linkedin-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.echobox%22%20AND%20a:%22ebx-linkedin-sdk%22) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://raw.githubusercontent.com/ebx/ebx-linkedin-sdk/master/LICENSE) 

# ebx-linkedin-sdk

## What it is

ebx-linkedin-sdk is a pure Java LinkedIn API client. It implements the v2 API as described 
[here](https://docs.microsoft.com/en-us/linkedin/).

It is created and maintained by [Echobox](http://echobox.com).

## Licensing

ebx-linkedin-sdk itself is open source software released under the terms of the Apache 2.0 License.

## Installation (Recommended)

Recommended installation is via maven (or gradle etc.). For our latest stable release (recommended) 
use:

```
<dependency>
  <groupId>com.echobox</groupId>
  <artifactId>ebx-linkedin-sdk</artifactId>
  <version>1.0.3</version>
</dependency>
```

## Installation (Most Up To Date)
[![Build Status](https://travis-ci.org/ebx/ebx-linkedin-sdk.svg?branch=dev)](https://travis-ci.org/ebx/ebx-linkedin-sdk)

If you'd like to use the latest SNAPSHOT build please ensure you have snapshots enabled in your pom:

```
<repositories>
    <repository>
        <id>oss.sonatype.org-snapshot</id>
        <url>http://oss.sonatype.org/content/repositories/snapshots</url>
        <releases><enabled>false</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```

and then include the snapshot dependency, replacing *x.x.x* with the latest 
[snapshot version](https://github.com/ebx/ebx-linkedin-sdk/blob/dev/pom.xml):

```
<dependency>
  <groupId>com.echobox</groupId>
  <artifactId>ebx-linkedin-sdk</artifactId>
  <version>x.x.x-SNAPSHOT</version>
</dependency>
```

alternatively just drop the JAR into your app and you're ready to go.

## Building it Yourself

Just type

    mvn package
    
and the jars will be built and can be found in the `target` folder. 

## Usage and examples

Please see the tests for examples of API calls that are supported and the expected JSON responses

To get the access token to begin to make requests ([See
documentation](https://docs.microsoft.com/en-us/linkedin/shared/authentication/authorization-code-flow?context=linkedin/marketing/context])):

    DefaultLinkedInClient client = new DefaultLinkedInClient(Version.DEFAULT_VERSION);
    LinkedInClient.AccessToken accessToken = client.obtainUserAccessToken(clientId, clientSecret, redirectURI, code);

To create a LinkedIn Share
([See documentation](https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/share-api#post-shares)):

    ShareConnection shareConnection = new ShareConnection(new DefaultLinkedInClient(authToken));
    
    ShareRequestBody shareRequestBody = new ShareRequestBody(URN);
    
    ShareContent shareContent = new ShareContent();
    
    ContentEntity contentEntity = new ContentEntity();
    contentEntity.setEntityLocation("https://www.example.com/content.html");
    shareContent.setContentEntities(Arrays.asList(contentEntity));
    shareContent.setTitle("Test Share with Content");
    shareRequestBody.setContent(shareContent);
    shareRequestBody.setSubject("Test share subject");
    ShareText shareText = new ShareText();
    shareText.setText("test share");
    shareRequestBody.setText(shareText);
    Share share = shareConnection.postShare(shareRequestBody);

Retrieve an organization from LinkedIn 
([See documentation](https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations)):
    
    Organization organization = connection.retrieveOrganization(organizationURN, Parameter
            .with("projection",
                "(elements*(*,roleAssignee~(localizedFirstName, localizedLastName),"
                    + "organizationalTarget~(localizedName)))"));

## Getting in touch

* **[GitHub Issues](https://github.com/ebx/ebx-linkedin-sdk/issues/new)**: If you have ideas, bugs, 
or problems with our library, just open a new issue.

## Contributing

If you would like to get involved please follow the instructions 
[here](https://github.com/ebx/ebx-linkedin-sdk/tree/master/CONTRIBUTING.md)

## Releases

We use [semantic versioning](https://semver.org/).

All merges into DEV will automatically get released as a maven central snapshot, which can be easily
included in any downstream dependencies that always desire the latest changes (see above for 
'Most Up To Date' installation).

Each merge into the MASTER branch will automatically get released to Maven central and github 
releases, using the current library version. As such, following every merge to master, the version 
number of the dev branch should be incremented and will represent 'Work In Progress' towards the 
next release. 

Please use a merge (not rebase) commit when merging dev into master to perform the release.

To create a full release to Maven central please follow these steps:
1. Ensure the `CHANGELOG.md` is up to date with all the changes in the release, if not please raise 
a suitable PR into `DEV`. Typically the change log should be updated as we go.
3. Create a PR from `DEV` into `MASTER`. Ensure the version in the `pom.xml` is the 
correct version to be released. Merging this PR into `MASTER` will automatically create the maven 
and github releases. Please note that a release is final, it can not be undone/deleted/overwritten.
5. Once the public release has been successful create a final PR into `DEV` that contains an 
incremented `pom.xml` version to ensure the correct snapshot gets updated on subsequent merges
into `DEV`. This PR should also include:
    * An update to the `README.md` latest stable release version number.
    * A 'Work In Progress' entry for the next anticipated release in `CHANGELOG.md`.
