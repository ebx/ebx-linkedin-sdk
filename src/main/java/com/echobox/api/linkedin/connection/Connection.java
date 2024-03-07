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

import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.connection.ConnectionBase;
import com.echobox.api.linkedin.types.TimeInterval;
import com.echobox.api.linkedin.version.Version;

import java.util.List;

/**
 * Base class for all connections using API
 * @author Kenneth Wong
 */
public abstract class Connection extends ConnectionBase {
  
  /**
   * The query key.
   */
  protected static final String QUERY_KEY = "q";
  
  private static final String TIME_INTERVALS_GRANULARITY = "timeIntervals.timeGranularityType";
  private static final String TIME_INTERVALS_START = "timeIntervals.timeRange.start";
  private static final String TIME_INTERVALS_END = "timeIntervals.timeRange.end";
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  protected Connection(LinkedInClient linkedinClient) {
    super(linkedinClient);
    if (Version.VERSIONED != linkedinClient.getVersion()) {
      throw new IllegalArgumentException("The version of linkedInClient is not VERSIONED");
    }
  }
  
  /**
   * Add time interval to parameters.
   *
   * @param params       the list of parameters
   * @param timeInterval the time interval to add
   */
  protected void addTimeIntervalToParams(List<Parameter> params, TimeInterval timeInterval) {
    if (timeInterval == null) {
      return;
    }
    
    if (timeInterval.getTimeGranularityType() != null) {
      // Time restriction on retrieving share statistics
      params.add(Parameter.with(TIME_INTERVALS_GRANULARITY, timeInterval.getTimeGranularityType()));
      if (timeInterval.getTimeRange() != null) {
        if (timeInterval.getTimeRange().getStart() != null
            && timeInterval.getTimeRange().getEnd() != null) {
          params.add(Parameter.with(TIME_INTERVALS_START, timeInterval.getTimeRange().getStart()));
          params.add(Parameter.with(TIME_INTERVALS_END, timeInterval.getTimeRange().getEnd()));
        }
      } else {
        throw new IllegalStateException(
            "timeIntervals.timeRange cannot be null when " + "timeInterval is provided");
      }
    } else {
      throw new IllegalStateException(
          "timeIntervals.timeGranularityType cannot be null when " + "timeInterval is provided");
    }
  }
  
}
