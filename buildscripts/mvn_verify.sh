#!/bin/bash
set -euo pipefail
# shellcheck disable=SC2059
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

## For PR builds, perform maven verify. Exit with error if dev or master 
## as these are handled separately in mvn_deploy.sh
export JAVA_HOME="/usr"

if [ "$CIRCLE_BRANCH" == "${DEV_BRANCH}" ] || [ "$CIRCLE_BRANCH" == "${RELEASE_BRANCH}" ]; then
  printf "${RED_COLOUR}ERROR: PR builds should not be triggered by ${DEV_BRANCH} or ${RELEASE_BRANCH} branches.${NO_COLOUR}\n"
  exit 1
else
  printf "${GREEN_COLOUR}Performing a PR verify build on PR #${CIRCLE_PULL_REQUEST##*/}.${NO_COLOUR}\n"
  java --version
  mvn clean verify
fi
