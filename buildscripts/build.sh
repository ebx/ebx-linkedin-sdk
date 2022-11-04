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

## For DEV and MASTER deploy to maven central (DEV will always be a snapshot)
## All other builds are simply verified
if [ "$CIRCLE_BRANCH" == "$RELEASE_BRANCH" ] || [ "$CIRCLE_BRANCH" == "$DEV_BRANCH" ]; then
  printf "${GREEN_COLOUR}Performing deploy build to maven central.${NO_COLOUR}\n"
  echo "${GPG_SECRET_KEYS}" | base64 --decode | $GPG_EXECUTABLE --import --batch --passphrase "${GPG_PASSPHRASE}" || echo "Failed to import GPG_SECRET_KEYS."
  echo "${GPG_OWNERTRUST}" | base64 --decode | $GPG_EXECUTABLE --import-ownertrust --batch --passphrase "${GPG_PASSPHRASE}" || echo "Failed to import GPG_OWNERTRUST."
  
  mvn clean deploy --settings .maven.xml -B -U -Prelease
else
  printf "${GREEN_COLOUR}Performing a PR verify build. Releases are only created from $DEV_BRANCH and $RELEASE_BRANCH branches.${NO_COLOUR}\n"
  mvn clean verify
fi
