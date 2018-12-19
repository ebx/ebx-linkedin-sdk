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
  
  protected LinkedInClient linkedinClient;
  
  protected ConnectionBase(LinkedInClient linkedinClient) {
    ValidationUtils.verifyParameterPresence("linkedinClient", linkedinClient);
    this.linkedinClient = linkedinClient;
  }
  
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
  
  protected void validateURN(URNEntityType urnEntityType, URN urn) {
    if (!urnEntityType.equals(urn.getURNEntityType())) {
      throw new IllegalArgumentException("The URN should be type " + urnEntityType);
    }
  }

}
