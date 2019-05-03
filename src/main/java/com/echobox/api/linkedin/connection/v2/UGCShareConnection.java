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

import com.echobox.api.linkedin.client.Connection;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.ugc.UGCShare;
import com.echobox.api.linkedin.types.ugc.ViewContext;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.util.URLUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UGC Share connection
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api">UGC Shares</a>
 * @author joanna
 */
public class UGCShareConnection extends ConnectionBaseV2 {
  
  private static final String UGC_POST = "/ugcPosts";
  private static final String VIEW_CONTEXT = "viewContext";
  private static final String AUTHORS = "authors";
  
  /**
   * Initialise a UGC share connection
   * @param linkedinClient the LinkedIn client
   */
  public UGCShareConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Create a UGC post
   * To upload an image share, use the RichMediaConnection to get the location as the media field
   * To upload a video share, use the AssetsConnection to get the digital media asset location as
   * the media field
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#create-ugc-posts">Create UGC Post</a>
   * @param shareBody the share body
   * @return the created UGC share
   */
  public UGCShare createUGCPost(UGCShare shareBody) {
    return linkedinClient.publish(UGC_POST, UGCShare.class, shareBody);
  }
  
  /**
   * To retrieve a UGC Post, provide the context in which the user generated content is being
   * viewed.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#retrieve-ugc-posts">Retrieve UGC Post</a>
   * @param shareURN share URN - can be either a UGC share URN or share URN from the share API
   * @param viewContext the context in which the user generated content is being viewed
   * @return the UGC post from the share URN
   */
  public UGCShare retrieveUGCPost(URN shareURN, ViewContext viewContext) {
    Parameter viewContextParam = Parameter.with(VIEW_CONTEXT, viewContext);
    return linkedinClient.fetchObject(UGC_POST + "/" + URLUtils.urlDecode(shareURN.toString()),
        UGCShare.class, viewContextParam);
  }
  
  /**
   * Multiple UGC Posts can be retrieved in a single API call by passing in multiple UGC Post Urns
   * into the ids parameter.
   * @param shareURNs share URNs to query
   * @return List of UGC shares
   */
  public List<UGCShare> batchRetrieveUGCPosts(List<URN> shareURNs) {
    throw new UnsupportedOperationException("batchRetrieveUGCPosts is not yet "
        + "implemented");
  }
  
  /**
   * Retrieve all UGC posts for a member or an organization.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#retrieve-ugc-posts-by-authors">Retrieve UGC Postsby authors</a>
   * @param authorURN the author URN - can be either an organization or person URN
   * @param count the number of entries to be returned per paged request
   * @return the connection of UGC share which is paged
   */
  public Connection<UGCShare> retrieveUGCPostsByAuthors(URN authorURN, Integer count) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, AUTHORS));
    addParametersFromURNs(parameters, AUTHORS, Arrays.asList(authorURN));
    addStartAndCountParams(parameters, null, count);
    return linkedinClient.fetchConnection(UGC_POST, UGCShare.class,
        parameters.toArray(new Parameter[parameters.size()]));
  }
  
  /**
   * Retrieve all UGC posts of a group using containerEntities
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#retrieve-ugc-posts-by-containerentities">Retrieve UGC Posts by containerEntities</a>
   * @param groupURNs the list of group URNs
   * @return the connection of UGC share which is paged
   */
  public Connection<UGCShare> retrieveUGCPostsByContainerEntities(List<URN> groupURNs) {
    throw new UnsupportedOperationException("retrieveUGCPostsByContainerEntities is not yet "
        + "implemented");
  }
  
  /**
   * Retrieve a UGC post based on the permaLinkSuffixes.
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#retrieve-ugc-posts-by-permalinksuffixes">Retrieve UGC Posts by permalinkSuffixes</a>
   * @param permalinkSuffixes the list of permalink suffixes
   * @return the connection of the UGC share which is paged
   */
  public Connection<UGCShare> retrieveUGCPostsByPermalinkSuffixes(List<URL> permalinkSuffixes) {
    throw new UnsupportedOperationException("retrieveUGCPostsByPermalinkSuffixes is not yet "
        + "implemented");
  }
  
  /**
   * Delete UGC Posts
   * @see <a href="https://docs.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/ugc-post-api#delete-ugc-posts">Delete UGC Posts</a>
   * @param shareURN the share URN of the share to delete - can be either a UGC share or share URN
   */
  public void deleteUGCPost(URN shareURN) {
    linkedinClient.deleteObject(UGC_POST + "/" + URLUtils.urlEncode(shareURN.toString()));
  }

}
