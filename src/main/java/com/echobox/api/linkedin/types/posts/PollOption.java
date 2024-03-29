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

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PollOption object
 *
 * @author Sergio Abplanalp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollOption {
  /**
   * Text describing the option.
   */
  @LinkedIn
  private String text;
  
  /**
   * Boolean value to show whether the viewer voted this poll option.
   */
  @LinkedIn
  private Boolean isVotedByViewer;
  
  /**
   * Vote counts of the poll option.
   */
  @LinkedIn
  private Long voteCount;
}
