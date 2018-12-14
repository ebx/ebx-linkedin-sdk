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
 * The type of institution
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/organization-lookup-api
 * #schoolattr">School Attributes Schema</a>
 * @author joanna
 *
 */
public enum SchoolType {
  
  /**
   * A public school operated by the state, as opposed to being a private enterprise.
   */
  PUBLIC,
  
  /**
   * A private school, also known as independent school, non-governmental, or nonstate school, 
   * not administered by a government.
   */
  PRIVATE,
  
  /**
   * A for-profit school operated by a private, profit-seeking business.
   */
  PROFIT;

}
