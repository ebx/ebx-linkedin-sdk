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

package com.echobox.api.linkedin.types;

import lombok.Getter;

import java.util.List;

/**
 * The company's profile model type
 * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">Company Profile</a>
 * for more informatiom
 * @author Joanna
 *
 */
public class Company extends LinkedInIdAndNameType {

  /**
   * The unique string identifier of a company.
   */
  @Getter
  private String universalName;

  /**
   * Company email domains.
   */
  @Getter
  private List<String> emailDomains;

  /**
   * The type of company.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">Company types</a>
   * for more information
   */
  @Getter
  private CodeAndNameType companyTypeRaw;
  
  /**
   * The type of company as an email.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">Company types</a>
   * for more information
   */
  @Getter
  private CompanyType companyType;

  /**
   * Company ticker identification for the stock exchange. Available only for public companies.
   */
  @Getter
  private String ticker;

  /**
   * Company web site address.
   */
  @Getter
  private String websiteURL;

  /**
   * A collection containing a code and name pertaining to the company's industry. 
   * @see <a href="https://developer.linkedin.com/docs/reference/industry-codes">Industry Codes</a>
   * for the list of industries available.
   */
  @Getter
  private List<CodeAndNameType> industriesRaw;
  
  /**
   * A collection containing a code and name pertaining to the company's industry. 
   * @see <a href="https://developer.linkedin.com/docs/reference/industry-codes">Industry Codes</a>
   * for the list of industries available.
   */
  @Getter
  private List<IndustryCode> industries;

  /**
   * Company status.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">status</a> for more
   * information
   */
  @Getter
  private CodeAndNameType statusRaw;
  
  @Getter
  private StatusType status;

  /**
   * URL for the company logo in JPG format.
   */
  @Getter
  private String logoURL;

  /**
   * URL for the company logo in a square format.
   */
  @Getter
  private String squareLogoURL;

  /**
   * URL for the company blog.
   */
  @Getter
  private String blogRSSURL;

  /**
   * Handle for the company Twitter feed.
   */
  @Getter
  private long twitterId;

  /**
   * Number range of employees at the company.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">
   * employee-count-range</a> for more information
   */
  @Getter
  private CodeAndNameType employeeCountRangeRaw;
  
  /**
   * Number range of employees at the company.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">
   * employee-count-range</a> for more information
   */
  @Getter
  private EmployeeCountRange employeeCountRange;

  /**
   * Company specialties. Retrieves information from string input.
   */
  @Getter
  private List<String> specialities;

  /**
   * Company location.
   */
  @Getter
  private List<Location> locations;

  /**
   * Company description. Limit of 500 characters.
   */
  @Getter
  private String description;

  /**
   * Stock exchange the company is in. Available only for public companies.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">stock-exchange</a>
   * for more information
   */
  @Getter
  private CodeAndNameType stockExchangeRaw;
  
  /**
   * Stock exchange the company is in. Available only for public companies.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">stock-exchange</a>
   * for more information
   */
  @Getter
  private StockExchange stockExchange;

  /**
   * Year listed for the company's founding.
   */
  @Getter
  private int foundedYear;

  /**
   * Year listed for when the company closed or was acquired by another.
   */
  @Getter
  private int endYear;

  /**
   * The number of followers for the company's profile.
   */
  @Getter
  private int numFollowers;

}
