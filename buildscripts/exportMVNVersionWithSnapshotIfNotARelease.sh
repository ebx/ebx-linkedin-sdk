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

export MVN_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout|grep -v '\[')
## If this is not master we dynamically set the version to a snapshot
if [ "$SOURCE_BRANCH_NAME" != "$RELEASE_BRANCH" ]; then 
  mvn versions:set -q -DnewVersion=$MVN_VERSION-SNAPSHOT
  export MVN_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout|grep -v '\[')
  printf "${GREEN_COLOUR}Build version modified to $MVN_VERSION as this is not the $RELEASE_BRANCH branch.${NO_COLOUR}\n"
else
  printf "${GREEN_COLOUR}Build version not modified as this is the $RELEASE_BRANCH branch.${NO_COLOUR}\n"
fi

#Ensure the project verison is valid
if ! [[ $MVN_VERSION =~ ^[0-9]+\.[0-9]+\.[0-9]+.*$ ]]; then
  printf "${RED_COLOUR}The extracted project version '$MVN_VERSION' was not valid.${NO_COLOUR}\n"
  travis_terminate 1;
fi