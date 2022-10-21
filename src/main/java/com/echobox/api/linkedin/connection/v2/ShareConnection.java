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
import com.echobox.api.linkedin.types.request.ShareRequestBody;
import com.echobox.api.linkedin.types.request.UpdateShareRequestBody;
import com.echobox.api.linkedin.types.urn.URN;

import java.util.ArrayList;
import java.util.List;

/**
 * Share connection class that should contain all share operations
 *
 * @author joanna
 *
 */
public class ShareConnection extends ConnectionBaseV2 {
  
  private static final String SHARES = "/shares";
  private static final String OWNERS = "owners";
  private static final String SHARES_PER_OWNER = "sharesPerOwner";
  private static final String COUNT = "count";
  
  /**
   * Initialise the share connection
   * @param linkedinClient the linkedin client
   */
  public ShareConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Look up a share by share id
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/share-api#retrieve-shares">
   * Retrieve Shares</a>
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
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/share-api#retrieve-shares">
   * Retrieve Shares</a>
   * @param ownerURN the URN of the owner
   * @param sharesPerOwner the number of shares per owner
   * @param count the number of entries to be returned per paged request
   * @return the share corresponding to the share id
   */
  public List<Share> getShares(URN ownerURN, int sharesPerOwner, Integer count) {
    List<Parameter> params = new ArrayList<>();
  
    params.add(Parameter.with(QUERY_KEY, OWNERS));
    params.add(Parameter.with(OWNERS, ownerURN));
    params.add(Parameter.with(SHARES_PER_OWNER, sharesPerOwner));
    addStartAndCountParams(params, null, count);
    
    return getListFromQuery(SHARES, Share.class, params.toArray(new Parameter[0]));
  }
  
  /**
   * Post shares in the context of a specific member or organization. Use a URN in the owner
   * field to associate the share with an organization or authenticated member. The valid URN
   * formats are urn:li:person:{id} or urn:li:organization:{id}.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/share-api#post-shares">
   * Post Share</a>
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
  
}
