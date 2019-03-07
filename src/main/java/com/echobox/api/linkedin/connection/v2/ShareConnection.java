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

package com.echobox.api.linkedin.connection.v2;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.Share;
import com.echobox.api.linkedin.types.ShareText;
import com.echobox.api.linkedin.types.TimeInterval;
import com.echobox.api.linkedin.types.engagement.ShareStatistic;
import com.echobox.api.linkedin.types.request.ShareRequestBody;
import com.echobox.api.linkedin.types.request.UpdateShareRequestBody;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.ArrayList;
import java.util.List;

/**
 * SHare connection class that should contain all share operations
 *
 * @author joanna
 *
 */
public class ShareConnection extends ConnectionBaseV2 {
  
  private static final String SHARES = "/shares";
  private static final String SHARE_STATISTICS = "/organizationalEntityShareStatistics";
  private static final String OWNERS = "owners";
  private static final String SHARES_PARAM = "shares";
  private static final String SHARES_PER_OWNER = "sharesPerOwner";
  private static final String COUNT = "count";
  private static final String ORGANIZATIONAL_ENTITY = "organizationalEntity";
  private static final String TIME_INTERVALS_GRANULARITY = "timeIntervals.timeGranularityType";
  private static final String TIME_INTERVALS_START = "timeIntervals.timeRange.start";
  private static final String TIME_INTERVALS_END = "timeIntervals.timeRange.end";
  
  /**
   * Initialise the share connection
   * @param linkedinClient the linkedin client
   */
  public ShareConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Look up a share by share id
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/shares/share-api#retrieve-shares">Retrieve Shares</a>
   * @param shareId the share id to look up
   * @return the share corresponding to the share id
   */
  public Share getShare(long shareId) {
    return linkedinClient.fetchObject(SHARES + "/" + shareId, Share.class);
  }
  
  /**
   * Retrieve the collection of shares owned by a specific member or organization. Use URNs
   * formatted as urn:li:person:{id} , urn:li:organization:{id} , or urn:li:organizationBrand:{id}
   * to retrieve shares for the relevant entity.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/shares/share-api#retrieve-shares">Retrieve Shares</a>
   * @param ownerURNs the URNs of the owner
   * @param sharesPerOwner the number of shares per owner
   * @return the share corresponding to the share id
   */
  public List<Share> getShares(List<URN> ownerURNs, int sharesPerOwner) {
    List<Parameter> params = new ArrayList<>();
  
    params.add(Parameter.with(QUERY_KEY, OWNERS));
    addParametersFromURNs(params, SHARES_PARAM, ownerURNs);
    params.add(Parameter.with(SHARES_PER_OWNER, sharesPerOwner));
    params.add(Parameter.with(COUNT, 20));
    
    return getListFromQuery(SHARES, Share.class, params.toArray(new Parameter[params.size()]));
  }
  
  /**
   * Post shares in the context of a specific member or organization. Use a URN in the owner
   * field to associate the share with an organization or authenticated member. The valid URN
   * formats are urn:li:person:{id} or urn:li:organization:{id}.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/shares/share-api#post-shares">Post Share</a>
   * @param shareBody the share body
   * @return the resulting share
   */
  public Share postShare(ShareRequestBody shareBody) {
    return linkedinClient.publish(SHARES, Share.class, shareBody);
  }
  
  /**
   * Update the text of the share
   * @param shareURN the share URN to update
   * @param shareText the share text to update
   * @return the share that was updated
   */
  public Share updateShare(URN shareURN, ShareText shareText) {
    return linkedinClient.publish(SHARES + "/" + shareURN.toString(), Share.class,
        new UpdateShareRequestBody(shareText,
        linkedinClient.getJsonMapper()));
  }
  
  /**
   * Deleting a share also deletes the associated activity.
   * @param shareId the share id to delete
   */
  public void deleteShare(long shareId) {
    linkedinClient.deleteObject(SHARES + "/" + shareId);
  }
  
  /**
   * retrieve both lifetime and time-bound organic statistics on shares for an organization,
   * including specific organization share URNs. This endpoint returns organic statistics only.
   * Sponsored activity is not counted in this endpoint.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-
   * management/organizations/share-statistics">Organization Share Statistics</a>
   * @param organizationURN the organizational entity URN for which the statistics represents
   * @param timeInterval Time restriction for the query. When omitted, lifetime stats are returned
   * @param shareURNs References to one or more shares for which statistics are returned
   * @return aggregated stats for an organization's shares
   */
  public List<ShareStatistic> retrieveShareStatistics(URN organizationURN,
      TimeInterval timeInterval, List<URN> shareURNs) {
    List<Parameter> params = new ArrayList<>();
    params.add(Parameter.with(QUERY_KEY, ORGANIZATIONAL_ENTITY));
    params.add(Parameter.with(ORGANIZATIONAL_ENTITY, organizationURN));
    
    if (timeInterval != null) {
      // Time restriction on retrieving share statistics
      params.add(Parameter.with(TIME_INTERVALS_GRANULARITY,
          timeInterval.getTimeGranularityType()));
      if (timeInterval.getTimeRange() != null) {
        if (timeInterval.getTimeRange().getStart() != null
            && timeInterval.getTimeRange().getStart() != null
            && timeInterval.getTimeRange().getEnd() != null) {
          params.add(Parameter.with(TIME_INTERVALS_START, timeInterval.getTimeRange().getStart()));
          params.add(Parameter.with(TIME_INTERVALS_END, timeInterval.getTimeRange().getEnd()));
        } else {
          throw new IllegalStateException("timeIntervals.timeRange cannot be null when "
              + "timeInterval is provided");
        }
      } else {
        throw new IllegalStateException("timeIntervals.timeGranularityType cannot be null when "
            + "timeInterval is provided");
      }
    }
    
    addParametersFromURNs(params, SHARES_PARAM, shareURNs);
    
    return getListFromQuery(SHARE_STATISTICS, ShareStatistic.class,
        params.toArray(new Parameter[params.size()]));
  }
  
}
