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
 * Classification of the length of the institution's educational programs. 
 * These levels are defined by the National Center for Education Statistics 
 * (http://nces.ed.gov/pubs2015/2015097rev.pdf).
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/organization-lookup-api#schoolattr">School Attributes Schema</a>
 * @author joanna
 *
 */
public enum SchoolYearLevel {
  
  /**
   * As defined by National Center for Education Statistics: 
   * 'This group includes any postsecondary institution that offers programs of less than 2 years’
   * duration below the baccalaureate level, as well as occupational and vocational schools with
   * programs that do not exceed 1,800 contact hours.'
   */
  UNDER_TWO_YEAR,
  
  /**
   * As defined by the National Center for Education Statistics: ': 
   * Any postsecondary institution that offers programs of at least 2 but less than 4 years’ 
   * duration, as well as occupational and vocational schools with programs of at least 1,800 
   * hours and academic institutions with programs of less than 4 years. Does not include 
   * bachelor's-degree-granting institutions where the baccalaureate program can be completed in 
   * 3 years.'
   */
  TWO_YEAR,
  
  /**
   * As defined by the National Center for Education Statistics: 
   * 'Any postsecondary institution that offers programs of at least 4 years’ duration or one 
   * that offers programs at or above the baccalaureate level, as well as schools that offer 
   * postbaccalaureate certificates only or those that offer graduate programs only.'
   */
  FOUR_YEAR;

}
