#!/bin/bash
##
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##

if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  printf "${GREEN_COLOUR}Building base branch $SOURCE_BRANCH_NAME which is at version $MVN_VERSION.${NO_COLOUR}\n"
else
  #Get the PR title
  export PR_TITLE=$(curl -s https://api.github.com/repos/$REPO/pulls/$TRAVIS_PULL_REQUEST | grep -Po '(?<="title":[[:space:]]")[^"]*(?=",)')

  printf "${GREEN_COLOUR}Building PR #$TRAVIS_PULL_REQUEST '$PR_TITLE' from branch $SOURCE_BRANCH_NAME (into $TRAVIS_BRANCH)${NO_COLOUR}\n"
  
  ##Disabled as travis keeps getting rate limited by github
  ##Ensure the PR name matches our expected format
  #if ! [[ $PR_TITLE =~ ^[A-Z]{2,4}-[0-9]+[[:space:]].+$ ]]; then
  #  printf "${RED_COLOUR}PR title '$PR_TITLE' does not match the expected format 'GH-[xxxx] [description]'.${NO_COLOUR}\n"
  #  travis_terminate 1;
  #fi
  
  #Ensure PR cannot be into master, unless it's coming from dev
  if [ "$TRAVIS_BRANCH" == "$RELEASE_BRANCH" ] && [ "$SOURCE_BRANCH_NAME" != "$DEV_BRANCH" ]; then
    printf "${RED_COLOUR}Build failed as PR target is master. Please ensure you use $DEV_BRANCH as the target.${NO_COLOUR}\n"
    travis_terminate 1;
  fi
fi