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
 * Share media category
 * @author joanna
 */
public enum ShareMediaCategory {
  
  /**
   * Represents shared content that only contains articles.
   */
  ARTICLE,
  
  /**
   * Represents shared content that only contains images.
   */
  IMAGE,
  
  /**
   * Represents shared content that does not contain any media.
   */
  NONE,
  
  /**
   * Represents shared content that only cotains rich media.
   */
  RICH,
  
  /**
   * Represents shared content that only contains videos.
   */
  VIDEO,
  
  /**
   * Represents shared content that only contains learning courses.
   */
  LEARNING_COURSE,
  
  /**
   * Represents shared content that only contains jobs.
   */
  JOB,
  
  /**
   * Represents shared content that only contains questa questions.
   */
  QUESTION,
  
  /**
   * Represents shared content that only contains questa answers.
   */
  ANSWER,
  
  /**
   * Represents shared content of various types that should be rendered in carousel format.
   */
  CAROUSEL,
  
  /**
   * Represents shared content that only contains topics.
   */
  TOPIC,
  
  /**
   * Represents shared content of document file types that are uploaded natively.
   */
  NATIVE_DOCUMENT,
  
  /**
   * Refer to the media urn for the category of the content, except when urn type is digital media
   * asset because this urn type does not expose the content type. Use mediaType in ShareMedia
   * when disambiguation is required.
   */
  URN_REFERENCE,
  
  /**
   * Represents shared content of a video that is streamed live. This means that the ugcPost may
   * be consumed during recording. The resource serving the media is the source of truth for
   * whether the video is currently live.
   */
  LIVE_VIDEO;

}
