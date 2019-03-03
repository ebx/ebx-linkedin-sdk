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

package com.echobox.api.linkedin.types.urn.location;

import com.echobox.api.linkedin.types.objectype.MultiLocaleString;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.types.urn.function.FunctionURN;

/**
 * Degree URN
 * @see <a href="https://docs.microsoft.com/en-us/linkedin/shared/references/v2/standardized-data
 * /degrees?context=linkedin/marketing/context">Degree URN</a>
 * @author joanna
 */
public class DegreeURN extends FunctionURN {
  
  /**
   * All aliases to this standardized degree name. Represented as an array of MultiLocaleString.
   */
  private MultiLocaleString alias;
  
  /**
   * The general standardized degree category for the standardized degree.
   * Represented as a degree URN
   */
  private URN rollup;

}
