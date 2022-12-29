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

package com.echobox.api.linkedin.types.posts;

/**
 * The version of the post.
 * @author Sergio Abplanalp
 */
public enum ViewContext {
  
  /**
   * The latest version of a post which may have not yet been published
   */
  AUTHOR,
  
  /**
   * The version of the post that has been published and is viewable to a general audience
   */
  READER,
  
  /**
   * The version of a post that has been published
   */
  PUBLISHED,
  
  /**
   * The draft version of a post
   */
  DRAFT,
  
  /**
   * The version of a post that is being processed
   */
  PROCESSING
  
}
