[![Build Status](https://travis-ci.org/ebx/ebx-linkedin-sdk.svg?branch=master)](https://travis-ci.org/ebx/ebx-linkedin-sdk)

# ebx-linkedin-sdk - Initial release under development!

## What it is

ebx-linkedin-sdk is a pure Java LinkedIn API client.

It was created and maintained by [Echobox](http://echobox.com).

## Licensing

ebx-linkedin-sdk itself is open source software released under the terms of the Apache 2.0 License.

## Installation

ebx-linkedin-sdk is a single JAR - just drop it into your app and you're ready to go.

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
