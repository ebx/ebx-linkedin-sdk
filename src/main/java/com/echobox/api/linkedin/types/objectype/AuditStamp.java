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

package com.echobox.api.linkedin.types.objectype;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import lombok.Getter;

/**
 * Audit stamp POJO
 * @see <a href="https://developer.linkedin.com/docs/ref/v2/object-types#AuditStamp">
 * Audit Stamp Schema</a>
 * @author joanna
 *
 */
public class AuditStamp {

  /**
   * The entity authorized the change
   */
  @Getter
  @LinkedIn
  private String actor;
  
  /**
   * The entity which performs the change on behalf of the actor. Must be authorized to act as the
   * actor.
   */
  @Getter
  @LinkedIn
  private String impersonator;
  
  /**
   * The entity which performs the change on behalf of the actor. Must be authorized to act as the
   * actor.
   */
  @Getter
  @LinkedIn
  private Long time;
  
}
