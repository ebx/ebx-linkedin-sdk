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

package com.echobox.api.linkedin.types.social.actions;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Alexandros
 */
public class CommentAction {
  
  @Getter
  @Setter
  @LinkedIn
  private Long id;
  
  @Getter
  @Setter
  @LinkedIn
  private String actor;
  
  @Getter
  @Setter
  @LinkedIn
  private List<CommentContent> content;
  
  @Getter
  @Setter
  @LinkedIn
  private CommentMessage message;
  
  @Getter
  @Setter
  @LinkedIn
  private String parentComment;
  
  @Getter
  @Setter
  @LinkedIn
  private LikesSummary likesSummary;
  
  @Getter
  @Setter
  @LinkedIn
  private String object;
  
  @Getter
  @Setter
  @LinkedIn("$URN")
  private String urn;
  
  /**
   * A Model to describe the content of a comment
   */
  public static class CommentContent {
    
    @Getter
    @Setter
    @LinkedIn
    private String type;
    
    @Getter
    @Setter
    @LinkedIn
    private String url;
  }
  
  /**
   * A Model to describe the message of a comment
   */
  public static class CommentMessage {
    
    @Getter
    @Setter
    @LinkedIn
    private List<String> attributes;
    
    @Getter
    @Setter
    @LinkedIn
    private String text;
  }
}
