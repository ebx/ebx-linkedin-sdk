[![Maven Central](https://img.shields.io/maven-central/v/com.echobox/ebx-linkedin-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.echobox%22%20AND%20a:%22ebx-linkedin-sdk%22) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://raw.githubusercontent.com/ebx/ebx-linkedin-sdk/master/LICENSE)

# ebx-linkedin-sdk

## What it is

ebx-linkedin-sdk is a pure Java LinkedIn API client. It implements the v2 API as described [here](https://docs.microsoft.com/en-us/linkedin/).

It is created and maintained by [Echobox](http://echobox.com).

## Licensing

ebx-linkedin-sdk itself is open source software released under the terms of the Apache 2.0 License.

## Installation (Recommended)

Recommended installation is via maven (or gradle etc.). For our latest stable release (recommended) use:

```
<dependency>
  <groupId>com.echobox</groupId>
  <artifactId>ebx-linkedin-sdk</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Installation (Most Up To Date) [![Build Status](https://travis-ci.org/ebx/ebx-linkedin-sdk.svg?branch=dev)](https://travis-ci.org/ebx/ebx-linkedin-sdk)

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

and then include the snapshot dependency:

```
<dependency>
  <groupId>com.echobox</groupId>
  <artifactId>ebx-linkedin-sdk</artifactId>
  <version>1.0.2-SNAPSHOT</version>
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

Retrieve an organization from LinkedIn ([See documentation](https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/organizations/organization-lookup-api#retrieve-organizations)):
    
    Organization organization = connection.retrieveOrganization(organizationURN, Parameter
            .with("projection",
                "(elements*(*,roleAssignee~(localizedFirstName, localizedLastName),"
                    + "organizationalTarget~(localizedName)))"));

## Getting in touch

* **[GitHub Issues](https://github.com/ebx/ebx-linkedin-sdk/issues/new)**: If you have ideas, bugs, or problems with our library, just open a new issue.

## Contributing

If you would like to get involved please follow the instructions [here](https://github.com/ebx/ebx-linkedin-sdk/tree/master/CONTRIBUTING.md)

## Releases

We use [semantic versioning](https://semver.org/).

Each merge into the MASTER branch will automatically get released to maven central, and github releases, using the current library version. As such, following every merge to master, the version number of the dev branch should be incremented and will represent 'Work In Progress' towards the next release.

All merges into DEV will automatically get released as a maven central snapshot, which can be easily included in any downstream dependencies that always desire the latest changes.