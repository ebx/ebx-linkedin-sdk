# Contributing to ebx-linkedin-sdk

Contributing code is an essential part of open source and we try to make this as easy as possible. There are several ways you can contribute to ebx-linkedin-sdk.  Here are some guidelines for creating new issues and sending us pull requests. Please read them carefully before contributing.

## How to create a new issue

We like new issues and are very keen to hear your problems, ideas and proposals. After analyzing them, they are categorized and assigned to a milestone. To make this part easier you should give us some information. Please read the checklist and try to mark as many entries as possible.

**Issue checklist:**
* Check the closed issues and don't open duplicates
* Explain your idea or problem in plain English
* Provide a JSON snippet if possible (values may be obfuscated but similar to the original)
* Tell us why this is beneficial and what the advantage is for the users
* Explain your use case, it's easier to understand a proposal if we have some background knowledge

## How to create a pull request

Pull requests (PR) are a very important way to contribute code to the library. We have guidelines for sending us a PR, because changes to the source code are even more fundamental than sending a new issue. This is an overview of our prerequisites.

**PR checklist:**
* Please ensure you have an associated [github issue](https://github.com/ebx/ebx-linkedin-sdk/issues) to hand, this needs to be included in the PR. If a suitable issue doesn't already exist feel free to create one, as described above.
* The code must be formatted with our code formatter (have a look at the [CodeStyle folder](https://github.com/ebx/ebx-linkedin-sdk/tree/master/CodeStyle)). If you perform a local *mvn verify* before creating the PR your changes will already be getting validated for style.
* The code layout should conform to our general design standards (if you feel it's necessary to go against the grain, please ask us first!).
* You should complete the PR template and please format your PR title as follows:

        GH-[Issue#] [Summary of change or issue title]

  for example:
  
        GH-123 Fixed NPE exception when resvoling an organisation

* The pull request should be mergeable, i.e. no conflicts.
* Junit tests required for any functional change must be included.
* The pull request should be targetted at the `dev` branch. If you raise it against `master` the PR will fail to build.
* Please try to keep PR commits in a logical order incase we need to review each commit seperately, but generally speaking the fewer commits the better.
* Your PR will have to build succesfully against our CI (we use [Travis CI](https://travis-ci.org/ebx/ebx-linkedin-sdk)).
* **Important Note**: If your PR contains breaking changes you must include a MAJOR version bump in the PR.
