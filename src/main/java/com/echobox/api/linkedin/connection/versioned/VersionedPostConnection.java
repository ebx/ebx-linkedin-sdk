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

import com.echobox.api.linkedin.client.Connection;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.client.VersionedLinkedInClient;
import com.echobox.api.linkedin.client.WebRequestor;
import com.echobox.api.linkedin.exception.LinkedInResponseException;
import com.echobox.api.linkedin.types.posts.Post;
import com.echobox.api.linkedin.types.posts.ViewContext;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.util.URLUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
  
  /**
   * keys
   */
  private static final String KEY_AUTHOR = "author";
  private static final String VIEW_CONTEXT = "viewContext";
  
  /**
   * Param
   */
  private static final String PARAM_AUTHOR = "author";

  /**
   * Headers
   */
  private static final String HEADER_ID = "x-linkedin-id";
  
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
  
  /**
   * Retrieve posts by author (organization)
   * @see
   *
   * <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api?view=li-lms-2022-12&tabs=http#find-posts-by-authors">Find Posts by Authors</a>
   *
   * @param authorURN author of the post - Posts author Organization
   * @param count page count
   * @return Connection object of the posts by the author
   */
  public Connection<Post> retrievePostsByAuthor(URN authorURN, Integer count) {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(Parameter.with(QUERY_KEY, KEY_AUTHOR));
    parameters.add(Parameter.with(PARAM_AUTHOR, authorURN));
    addStartAndCountParams(parameters, null, count);
    return linkedinClient.fetchConnection(POSTS, Post.class,
        parameters.toArray(new Parameter[parameters.size()]));
  }
  
  /**
   * Delete Posts
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api?view=li-lms-2022-12&tabs=http#delete-posts">Delete Posts</a>
   * @param shareURN the share URN of the share to delete - can be either a UGC share or share URN
   */
  public void deletePost(URN shareURN) {
    linkedinClient.deleteObject(POSTS + "/" + URLUtils.urlEncode(shareURN.toString()));
  }
  
  
  /**
   * Create a post.
   * @see <a href="https://learn.microsoft.com/en-us/linkedin/marketing/integrations/community-management/shares/posts-api#create-a-post">Create a Post</a>
   * @param post The Post to share
   * @return The URN of the post that was created.
   */
  public URN createPost(Post post) {
    WebRequestor.Response response = linkedinClient.publish(POSTS, post);
    Map<String, String> headers = response.getHeaders();
    if (headers == null) {
      throw new LinkedInResponseException("No headers were found on the response.");
    }
  
    String postURN = headers.get(HEADER_ID);
    if (postURN == null) {
      throw new LinkedInResponseException(String.format("The header [%s] is missing from the "
          + "response.", HEADER_ID));
    }
  
    return new URN(postURN);
  }
}
