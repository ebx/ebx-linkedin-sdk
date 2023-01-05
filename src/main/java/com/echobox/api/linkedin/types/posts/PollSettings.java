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
 * PollSettings object
 *
 * @author Sergio Abplanalp
 */
public class PollSettings {
  /**
   * The selection type of votes on the poll.
   */
  private VoteSelectionType voteSelectionType;
  
  /**
   * Duration of poll being open for votes.
   */
  private Duration duration;
  
  /**
   * Poll author’s visibility to voters.
   * Currently, isVoterVisibleToAuthor=false is not supported
   */
  private Boolean isVoterVisibleToAuthor = true;
  
  /**
   * VoteSelectionType enum
   * @author Sergio Abplanalp
   */
  public enum VoteSelectionType {
    /**
     * Single-select vote.
     */
    SINGLE_VOTE,
  
    /**
     * Multiple-select vote.
     * To be supported later in future.
     */
    MULTIPLE_VOTE
  }
  
  public enum Duration {
    /**
    * Poll is open for 1 day.
    */
    ONE_DAY,
    
    /**
    * Poll is open for 3 days.
    */
    THREE_DAYS,
    
    /**
    * Poll is open for 7 days.
    */
    SEVEN_DAYS,
    
    /**
    * Poll is open for 14 days.
    */
    FOURTEEN_DAYS
  }
}
