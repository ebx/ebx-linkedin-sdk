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

package com.echobox.api.linkedin.connection.versioned;

import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.VersionedLinkedInClient;
import com.echobox.api.linkedin.types.posts.Post;
import com.echobox.api.linkedin.types.posts.ViewContext;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.util.URLUtils;

/**
 * Post connection
 * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api">Posts API</a>
 *
 * @author Sergio Abplanalp
 */
public class VersionedPostConnection extends VersionedConnection {
  
  /**
   * endpoint path
   */
  private static final String POSTS = "/posts";
  private static final String VIEW_CONTEXT = "viewContext";

  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public VersionedPostConnection(VersionedLinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  /**
   * Find a post using a share URN and the view context.
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#get-posts-by-urn">Get Posts by URN</a>
   * @param shareURN The UGC Post URN or Share URN
   * @param viewContext the context in which the user generated content is being viewed
   * @return the requested post
   */
  public Post retrievePost(URN shareURN, ViewContext viewContext) {
    Parameter viewContextParam = Parameter.with(VIEW_CONTEXT, viewContext);
    return linkedinClient.fetchObject(POSTS + "/" + URLUtils.urlDecode(shareURN.toString()),
        Post.class, viewContextParam);
  }
}
