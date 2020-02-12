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

package com.echobox.api.linkedin.types.v1;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Share request body model
 * @see <a href="https://developer.linkedin.com/docs/share-on-linkedin">Share on LinkedIn</a>
 * @author Joanna
 *
 */
@AllArgsConstructor
public class Share {

  /**
   * A collection of fields describing the shared content.
   */
  @Getter
  @Setter
  @LinkedIn
  private Content content;
  
  /**
   * A comment by the member to associated with the share.
   */
  @Getter
  @Setter
  @LinkedIn
  private String comment;
  
  /**
   * A collection of visibility information about the share.
   */
  @Getter
  @NonNull
  @LinkedIn
  private Visibility visibility;
  
  @Getter
  @Setter
  @LinkedIn
  private Targeting shareTargetReach;
  
  /**
   * A collection of fields describing the shared content.
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class Content {
    
    /**
     * The title of the content being shared.
     */
    @Getter
    @Setter
    @LinkedIn
    private String title;
    
    /**
     * The description of the content being shared.
     */
    @Getter
    @Setter
    @LinkedIn
    private String description;
    
    /**
     * A fully qualified URL for the content being shared.
     */
    @Getter
    @Setter
    @LinkedIn
    private String submittedURL;
    
    /**
     * A fully qualified URL to a thumbnail image to accompany the shared content.
     */
    @Getter
    @Setter
    @LinkedIn
    private String submittedImageURL;
    
  }
  
  /**
   * A collection of visibility information about the share.
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class Visibility {
    
    /**
     * One of the following values:
     * anyone:  Share will be visible to all members.
     * connections-only:  Share will only be visible to connections of the member performing the
     * share.
     */
    @Getter
    @Setter
    @NonNull
    @LinkedIn
    private String code;
    
  }
  
  /**
   * Targeting POJO
   * @see <a href="https://developer.linkedin.com/docs/company-pages#targetting_shares">
   * Share on LinkedIn</a>
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class Targeting {
    
    @Getter
    @LinkedIn
    private ShareTargets shareTargets;
    
  }
  
  /**
   * Share targets POJO
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class ShareTargets {
    
    @Getter
    @LinkedIn
    private ShareTarget shareTarget;
    
  }
  
  /**
   * Share Target POJO
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class ShareTarget {
    
    @Getter
    @LinkedIn
    private Tvalues tvalues;
    
    @Getter
    @LinkedIn
    private String code;
    
  }
  
  /**
   * Tvalues POJO
   * @author Joanna
   *
   */
  @AllArgsConstructor
  public static class Tvalues {
    
    @Getter
    @LinkedIn
    private String tvalue;
    
  }
  
}


