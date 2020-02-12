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

import com.echobox.api.linkedin.client.Connection;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.URNEntityType;
import com.echobox.api.linkedin.util.ValidationUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The connection base class
 * @author joanna
 *
 */
public abstract class ConnectionBase {

  /**
   * The LinkedIn client.
   */
  protected LinkedInClient linkedinClient;

  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  protected ConnectionBase(LinkedInClient linkedinClient) {
    ValidationUtils.verifyParameterPresence("linkedinClient", linkedinClient);
    this.linkedinClient = linkedinClient;
  }

  /**
   * Gets list from query.
   *
   * @param <T>            the type parameter
   * @param object         The name of the connection
   * @param connectionType the connection type
   * @param parameters     URL parameters to include in the API call
   * @return the list from query
   */
  protected <T> List<T> getListFromQuery(String object, Class<T> connectionType,
      Parameter... parameters) {
    List<T> resultList = new ArrayList<>();

    Connection<T> connection =
        linkedinClient.fetchConnection(object, connectionType, parameters);

    Iterator<List<T>> iterator = connection.iterator();
    while (iterator.hasNext()) {
      resultList.addAll(iterator.next());
    }

    return resultList;
  }

  /**
   * Validate URN.
   *
   * @param urnEntityType the urn entity type
   * @param urn           the uniform resource name
   */
  protected void validateURN(URNEntityType urnEntityType, URN urn) {
    if (!urnEntityType.equals(urn.resolveURNEntityType())) {
      throw new IllegalArgumentException("The URN should be type " + urnEntityType);
    }
  }

  /**
   * Add parameters from URNs.
   *
   * @param params the list of parameters
   * @param key    the key to be added
   * @param urns   the list of URNs
   */
  protected void addParametersFromURNs(List<Parameter> params, String key, List<URN> urns) {
    if (urns != null && !urns.isEmpty()) {
      for (int i = 0; i < urns.size(); i++) {
        params.add(Parameter.with(key + "[" + i + "]", urns.get(i).toString()));
      }
    }
  }

  /**
   * Add start and count parameters.
   *
   * @param params the list of parameters
   * @param start  the index of the first item you want results for.
   * @param count  the number of items needed to be included on each page of results
   */
  protected void addStartAndCountParams(List<Parameter> params, Integer start, Integer count) {
    if (start != null && start < 0) {
      throw new IllegalArgumentException("start parameter must be a positive integer");
    }
    if (count != null && count < 0) {
      throw new IllegalArgumentException("count parameter must be a positive integer");
    }
    if (start != null) {
      params.add(Parameter.with("start", start));
    }
    if (count != null) {
      params.add(Parameter.with("count", count));
    }
  }

}
