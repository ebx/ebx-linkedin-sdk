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
import com.echobox.api.linkedin.types.social.actions.CommentAction;
import com.echobox.api.linkedin.types.social.actions.CommentResponse;
import com.echobox.api.linkedin.types.urn.URN;

/**
 * Comment connection class for posting comments
 *
 * @author Sergio Abplanalp
 *
 */
public class CommentConnection extends Connection {
  
  /**
   * endpoint path
   */
  private static final String SOCIAL_ACTIONS_COMMENT = "/socialActions/%s/comments";

  /**
   * Instantiates a new comment connection.
   *
   * @param linkedinClient the LinkedIn client
   */
  public CommentConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  public CommentResponse postComment(CommentAction commentRequest, URN postURN) {
    String path = String.format(SOCIAL_ACTIONS_COMMENT, postURN.toURLEncodedString());
    return linkedinClient.publish(path, CommentResponse.class, commentRequest);
  }
}
