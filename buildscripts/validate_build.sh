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

if [ "${CIRCLE_BRANCH}" == "${RELEASE_BRANCH}" ] || [ "${CIRCLE_BRANCH}" == "${DEV_BRANCH}" ]; then
  printf "${GREEN_COLOUR}Building base branch $CIRCLE_BRANCH.${NO_COLOUR}\n"
else
  printf "${GREEN_COLOUR}Building PR #${CIRCLE_PULL_REQUEST##*/} '${CIRCLE_PULL_REQUEST}' from branch ${CIRCLE_BRANCH} (into ${DEV_BRANCH})${NO_COLOUR}\n"
fi
