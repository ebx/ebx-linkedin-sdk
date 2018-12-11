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

package com.echobox.api.linkedin.types.organization;

/**
 * Type of organization
 * @author joanna
 *
 */
public enum OrganizationType {
  
  /**
   * A public company
   */
  PUBLIC_COMPANY,
  
  /**
   * An educational institution
   */
  EDUCATIONAL,
  
  /**
   * A self-employed business
   */
  SELF_EMPLOYED,
  
  /**
   * A government agency
   */
  GOVERNMENT_AGENCY,
  
  /**
   * A non-profit organization
   */
  NON_PROFIT,
  
  /**
   * A self-owned business
   */
  SELF_OWNED,
  
  /**
   * A privately held business
   */
  PRIVATELY_HELD,
  
  /**
   * A partnership
   */
  PARTNERSHIP;

}
