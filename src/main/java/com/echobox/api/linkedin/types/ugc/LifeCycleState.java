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

package com.echobox.api.linkedin.types.ugc;

/**
 * The state of the content
 * @author joanna
 */
public enum LifeCycleState {
  
  /**
   * Represents a content that is accessible only to the author and is not yet published.
   */
  DRAFT,
  
  /**
   * Represents a content that is accessible to all entities.
   */
  PUBLISHED,
  
  /**
   * Represents a content that has been submitted for publish but is not yet ready for rendering.
   * The content will be published asynchronously once the processing has successfully completed.
   */
  PROCESSING,
  
  /**
   * Represents a content that has been submitted for publish but a processing failure caused
   * the publish to fail. An edit is required in order to re-attempt the publish.
   */
  PROCESSING_FAILED,
  
  /**
   * Represents a content that was once in another state, but has been deleted.
   */
  DELETED,
  
  /**
   * Represents a content that was published and later edited.
   */
  PUBLISHED_EDITED;

}
