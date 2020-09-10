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
import com.echobox.api.linkedin.types.LinkedInIdAndNameType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The company's profile model type
 * See <a href="https://developer.linkedin.com/docs/fields/company-profile">Company Profile</a>
 * for more information
 * @author Joanna
 *
 */
public class Company extends LinkedInIdAndNameType {

  /**
   * The unique string identifier of a company.
   */
  @Getter
  @LinkedIn
  private String universalName;

  /**
   * Company email domains.
   */
  @Getter
  @Setter
  @LinkedIn("emailDomains")
  private List<String> emailDomainsRaw;

  /**
   * The type of company.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">Company types</a>
   * for more information
   */
  @Getter
  @LinkedIn("companyType")
  private CodeAndNameType companyTypeRaw;

  /**
   * Company ticker identification for the stock exchange. Available only for public companies.
   */
  @Getter
  @LinkedIn
  private String ticker;

  /**
   * Company web site address.
   */
  @Getter
  @LinkedIn("websiteUrl")
  private String websiteURL;

  /**
   * A collection containing a code and name pertaining to the company's industry. 
   * @see <a href="https://developer.linkedin.com/docs/reference/industry-codes">Industry Codes</a>
   * for the list of industries available.
   */
  @Getter
  @LinkedIn("industries")
  private List<CodeAndNameType> industriesRaw;

  /**
   * Company status.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">status</a> for more
   * information
   */
  @Getter
  @LinkedIn("status")
  private CodeAndNameType statusRaw;

  /**
   * URL for the company logo in JPG format.
   */
  @Getter
  @LinkedIn("logoUrl")
  private String logoURL;

  /**
   * URL for the company logo in a square format.
   */
  @Getter
  @LinkedIn("squareLogoUrl")
  private String squareLogoURL;

  /**
   * URL for the company blog.
   */
  @Getter
  @LinkedIn
  private String blogRSSURL;

  /**
   * Handle for the company Twitter feed.
   */
  @Getter
  @LinkedIn
  private String twitterId;

  /**
   * Number range of employees at the company.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">
   * employee-count-range</a> for more information
   */
  @Getter
  @LinkedIn("employeeCountRange")
  private CodeAndNameType employeeCountRangeRaw;

  /**
   * Company specialties. Retrieves information from string input.
   */
  @Getter
  @LinkedIn
  private List<String> specialties;

  /**
   * Company location.
   */
  @Getter
  @LinkedIn
  private List<Location> locations;

  /**
   * Company description. Limit of 500 characters.
   */
  @Getter
  @LinkedIn
  private String description;

  /**
   * Stock exchange the company is in. Available only for public companies.
   * @see <a href="https://developer.linkedin.com/docs/fields/company-profile">stock-exchange</a>
   * for more information
   */
  @Getter
  @LinkedIn("stockExchange")
  private CodeAndNameType stockExchangeRaw;

  /**
   * Year listed for the company's founding.
   */
  @Getter
  @LinkedIn
  private Integer foundedYear;

  /**
   * Year listed for when the company closed or was acquired by another.
   */
  @Getter
  @LinkedIn
  private Integer endYear;

  /**
   * The number of followers for the company's profile.
   */
  @Getter
  @LinkedIn
  private Integer numFollowers;

}
