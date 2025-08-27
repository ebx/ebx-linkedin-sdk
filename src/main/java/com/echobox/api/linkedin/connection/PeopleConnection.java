/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echobox.api.linkedin.connection;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.people.FollowerResult;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * People Connection class for LinkedIn People (via Typeahead API).
 *
 * @author Myrto Papakonstantinou
 */
public class PeopleConnection extends Connection {
  
  private static final String PEOPLE_TYPEAHEAD = "/peopleTypeahead";
  private static final String QUERY_KEY = "q";
  private static final String KEYWORDS_KEY = "keywords";
  private static final String ORG_KEY = "organization";
  private static final String ORG_FOLLOWERS_VALUE = "organizationFollowers";
  
  public PeopleConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Search followers of an organization by keyword.
   *
   * @param organizationURN URN of the organization (urn:li:organization:{id})
   * @param keywords The search string (partial name, etc.)
   * @param count Max number of results to return
   * @return list of follower results with URNs, names, headline, photo, etc.
   */
  public List<FollowerResult> searchOrganizationFollowers(URN organizationURN, String keywords,
      Integer count) {
    ValidationUtils.verifyParameterPresence("organizationURN", organizationURN);
    ValidationUtils.verifyParameterPresence("keywords", keywords);
    
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ORG_FOLLOWERS_VALUE));
    params.add(Parameter.with(KEYWORDS_KEY, keywords));
    params.add(Parameter.with(ORG_KEY, organizationURN.toString()));
    this.addStartAndCountParams(params, null, count);
    
    return this.getListFromQuery(PEOPLE_TYPEAHEAD, FollowerResult.class,
        params.toArray(new Parameter[0]));
  }
}
