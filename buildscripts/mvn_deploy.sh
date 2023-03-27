#!/bin/bash
# shellcheck disable=SC2059
set -euo pipefail
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

## For DEV and MASTER deploy to maven central (DEV will deploy a snapshot)
## All other builds (eg PR builds) are verified by separate script mvn_verify.sh
export JAVA_HOME="/usr"

get_mvn_version() {
  mvn help:evaluate -Dexpression=project.version -q -DforceStdout|grep -v '\['
}

if [ "${CIRCLE_BRANCH}" != "${DEV_BRANCH}" ] && [ "${CIRCLE_BRANCH}" != "${RELEASE_BRANCH}" ]; then
  # Exit with error if this is not dev or master branch
  printf "${RED_COLOUR}Deployments to maven central should only be triggered from ${DEV_BRANCH} and ${RELEASE_BRANCH} branches.${NO_COLOUR}\n"
  exit 1
else
  printf "${GREEN_COLOUR}Performing deploy build to maven central.${NO_COLOUR}\n"
  mvn_version=$(get_mvn_version)

  # If this is not master we dynamically set the version to a snapshot
  if [ "${CIRCLE_BRANCH}" != "${RELEASE_BRANCH}" ]; then
    printf "${GREEN_COLOUR}Appending '-SNAPSHOT' to version as this is not the ${RELEASE_BRANCH} branch.${NO_COLOUR}\n"
    mvn versions:set -q -DnewVersion="${mvn_version}-SNAPSHOT"

    mvn_version=$(get_mvn_version)
    printf "${GREEN_COLOUR}Build version modified to ${mvn_version}.${NO_COLOUR}\n"
  fi

  # Ensure the project verison is valid
  if ! [[ ${mvn_version} =~ ^[0-9]+\.[0-9]+\.[0-9]+.*$ ]]; then
    printf "${RED_COLOUR}The extracted project version '${mvn_version}' was not valid.${NO_COLOUR}\n"
    exit 1;
  fi

  echo "${GPG_SECRET_KEYS}" | base64 --decode | $GPG_EXECUTABLE --import --batch --passphrase "${GPG_PASSPHRASE}" || echo "Failed to import GPG_SECRET_KEYS."
  echo "${GPG_OWNERTRUST}" | base64 --decode | $GPG_EXECUTABLE --import-ownertrust --batch --passphrase "${GPG_PASSPHRASE}" || echo "Failed to import GPG_OWNERTRUST."
  
  # Deploy to maven central
  mvn clean deploy --settings .maven.xml -B -U -Prelease
fi
