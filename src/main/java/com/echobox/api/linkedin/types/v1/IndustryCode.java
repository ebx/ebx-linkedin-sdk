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

import com.echobox.api.linkedin.logging.LinkedInLogger;

import lombok.Getter;

import org.slf4j.Logger;

/**
 * Industry code
 * @author Joanna
 *
 */
public enum IndustryCode implements CodeType<Integer> {
  
  ACCOUNTING(47, "Accounting", Group.CORP, Group.FIN),
  ARLINES_AVIATION(94, "Airlines/Aviation", Group.MAN, Group.TECH, Group.TRAN),
  ALTERNATIVE_DISPUTE_RESOLUTION(120, "Alternative Dispute Resolution", Group.LEG, Group.ORG),
  ALTERNATIVE_MEDICINE(125, "Alternative Medicine", Group.HLTH),
  ANIMATION(127, "Animation", Group.ART, Group.MED),
  APPAREL_FASHION(19, "Apparel & Fashion", Group.GOOD),
  ARCHITECTURE_PLANNING(50, "Architecture & Planning", Group.CONS),
  ARTS_CRAFTS(111, "Arts and Crafts", Group.ART, Group.MED, Group.REC),
  AUTOMOTIVE(53, "Automotive", Group.MAN),
  AVIATION_AREOSPACE(52, "Aviation & Aerospace", Group.GOV, Group.MAN),
  BANKING(14, "Banking", Group.FIN),
  BIOTECHNOLOGY(12, "Biotechnology", Group.GOV, Group.HLTH, Group.TECH),
  BROADCAST_MEDIA(36, "Broadcast Media", Group.MED, Group.REC),
  BUILDING_MATERIALS(49, "Building Materials", Group.CONS),
  BUSINESS_SUPPLIES_EQUIPMENT(138, "Business Supplies and Equipment", Group.CORP, Group.MAN),
  CAPITAL_MARKETS(129, "Capital Markets", Group.FIN),
  CHEMICALS(54, "Chemicals", Group.MAN),
  CIVIC_SOCIAL_ORGANISATION(90, "Civic & Social Organization", Group.ORG, Group.SERV),
  CIVIL_ENGINEERING(51, "Civil Engineering", Group.CONS, Group.GOV),
  COMMERCIAL_REAL_ESTATE(128, "Commercial Real Estate", Group.CONS, Group.CORP, Group.FIN),
  COMPUTER_NETWORK_SECURITY(118, "Computer & Network Security", Group.TECH),
  COMPUTER_GAMES(109, "Computer Games", Group.MED, Group.REC),
  COMPUTER_HARDWARE(3, "Computer Hardware", Group.TECH),
  COMPUTER_NETWORKING(5, "Computer Networking", Group.TECH),
  COMPUTER_SOFTWARE(4, "Computer Software", Group.TECH),
  CONSTRUCTION(48, "Construction", Group.CONS),
  CONSUMER_ELECTRONICS(24, "Consumer Electronics", Group.GOOD, Group.MAN),
  CNSUMER_GOODS(25, "Consumer Goods", Group.GOOD, Group.MAN),
  CONSUMER_SERVICES(91, "Consumer Services", Group.ORG, Group.SERV),
  COSMETICS(18, "Cosmetics", Group.GOOD),
  DAIRY(65, "Dairy", Group.AGR),
  DEFENSE_SPACE(1, "Defense & Space", Group.GOV, Group.TECH),
  DESIGN(99, "Design", Group.ART, Group.MED),
  EDUCATIONAL_MANAGEMENT(69, "Education Management", Group.EDU),
  E_LEARNING(132, "E-Learning", Group.EDU, Group.MAN),
  ELECTRONICAL_ELECTRONIC_MANUFACTORING(112, "Electrical/Electronic Manufacturing", Group.GOOD,
      Group.MAN),
  ENTERTAINMENT(28, "Entertainment", Group.MED, Group.REC),
  ENVIRONMENTAL_SERVICES(86, "Environmental Services", Group.ORG, Group.SERV),
  EVENTS_SERVICES(110, "Events Services", Group.CORP, Group.REC, Group.SERV),
  EXECUTIVE_OFFICE(76, "Executive Office", Group.GOV),
  FACILITIES_SERVICES(122, "Facilities Services", Group.CORP, Group.SERV),
  FARMING(63, "Farming", Group.AGR),
  FINANCIAL_SERVICES(43, "Financial Services", Group.FIN),
  FINE_ART(38, "Fine Art", Group.ART, Group.MED, Group.REC),
  FISHERY(66, "Fishery", Group.AGR),
  FOOD_BEVERAGES(34, "Food & Beverages", Group.REC, Group.SERV),
  FOOD_PRODUCTION(23, "Food Production", Group.GOOD, Group.MAN, Group.SERV),
  FUND_RAISING(101, "Fund-Raising", Group.AGR),
  FURNITURE(26, "Furniture", Group.GOOD, Group.MAN),
  GAMBLING_CASINOS(29, "Gambling & Casinos", Group.REC),
  GLASS_CERMAICS_CONCRETE(145, "Glass, Ceramics & Concrete", Group.CONS, Group.MAN),
  GOVENMENT_ADMINISTRATION(75, "Government Administration", Group.GOV),
  GOVERNMENT_RELATIONS(148, "Government Relations", Group.GOV),
  GRAPHIC_DESIGN(140, "Graphic Design", Group.GOV),
  HEALTH_WELLNESS_FITNESS(124, "Health, Wellness and Fitness", Group.HLTH, Group.REC),
  HIGHER_EDUCATION(68, "Higher Education", Group.EDU),
  HOSPITAL_HEALTH_CARE(14, "Hospital & Health Care", Group.HLTH),
  HOSPITALITY(31, "Hospitality", Group.REC, Group.SERV, Group.TRAN),
  HUMAN_RESOURCES(137, "Human Resources", Group.CORP),
  IMPORT_EXPORT(134, "Import and Export", Group.CORP, Group.GOOD, Group.TRAN),
  INDIVIDUAL_FAMILY_SERVICES(88, "Individual & Family Services", Group.ORG, Group.SERV),
  INDUSTRIAL_AUTOMATION(147, "Industrial Automation", Group.CONS, Group.MAN),
  INFORMATIONAL_SERVICES(84, "Information Services", Group.MED, Group.SERV),
  INFORMATIONAL_TECHNOLOGY_SERVICES(96, "Information Technology and Services", Group.TECH),
  INSURANCE(42, "Insurance", Group.FIN),
  INTERNATIONAL_AFFAIRS(74, "International Affairs", Group.GOV),
  INTERNATIONAL_TRADE_DEVELOPMENT(141, "International Trade and Development", Group.GOV, Group.ORG,
      Group.TRAN),
  INTERNET(6, "Internet", Group.TECH),
  INVESTMENT_BANKING(45, "Investment Banking", Group.FIN),
  INVESTMENT_MANAGEMENT(46, "Investment Management", Group.FIN),
  JUDICIARY(73, "Judiciary", Group.GOV, Group.LEG),
  LAW_INFORCEMENT(77, "Law Enforcement", Group.GOV, Group.LEG),
  LAW_PRACTICE(9, "Law Practice", Group.LEG),
  LEGAL_SERVICES(10, "Legal Services", Group.LEG),
  LEGISLATIVE_OFFICE(72, "Legislative Office", Group.GOV, Group.LEG),
  LESUIRE_TRAVEL_TOURISM(30, "Leisure, Travel & Tourism", Group.GOV, Group.LEG),
  LIBRARIES(85, "Libraries", Group.MED, Group.REC, Group.SERV),
  LOGISTICS_SUPPLY_CHAIN(116, "Logistics and Supply Chain", Group.CORP, Group.TRAN),
  LUXURY_GOODS_JEWELRY(143, "Luxury Goods & Jewelry", Group.GOOD),
  MACHINERY(55, "Machinery", Group.MAN),
  MANAGEMENT_CONSULTING(11, "Management Consulting", Group.CORP),
  MARITIME(95, "Maritime", Group.TRAN),
  MARKET_RESEARCH(97, "Market Research", Group.CORP),
  MARKETING_ADVERTISING(80, "Marketing and Advertising", Group.CORP, Group.MED),
  MECHANICAL_INDUSTRIAL_ENGINEERING(135, "Mechanical or Industrial Engineering", Group.CONS,
      Group.GOV, Group.MAN),
  MEDIA_PRODUCTION(126, "Media Production", Group.MED, Group.REC),
  MEDICAL_DEVICES(17, "Medical Devices", Group.HLTH),
  MEDICAL_PRACTICIES(13, "Medical Practice", Group.HLTH),
  MENTAL_HEALTH_CARE(139, "Medical Practice", Group.HLTH),
  MILITARY(71, "Military", Group.GOV),
  MINING_MATERIALS(56, "Mining & Metals", Group.MAN),
  MOTION_PICTURES_FILM(35, "Motion Pictures and Film", Group.ART, Group.MED, Group.REC),
  MUSEUMS_INSTITUTIONS(37, "Museums and Institutions", Group.ART, Group.MED, Group.REC),
  MUSIC(115, "Music", Group.ART, Group.REC),
  NANOTECHNOLOGY(114, "Nanotechnology", Group.GOV, Group.MAN, Group.TECH),
  NEWSPAPERS(81, "Newspapers", Group.MED, Group.REC),
  NON_PROFIT_ORGANIZATION_MANAGEMENT(100, "Non-Profit Organization Management", Group.ORG),
  OIL_ENERGY(57, "Oil & Energy", Group.MAN),
  ONLINE_MEDIA(113, "Online Media", Group.MED),
  OUTSOURCING_OFFSHORING(123, "Outsourcing/Offshoring", Group.CORP),
  PACKAGE_FREIGHT_DELIVERY(87, "Package/Freight Delivery", Group.SERV, Group.TRAN),
  PACKAGING_CONTAINERS(146, "Packaging and Containers", Group.GOOD, Group.MAN),
  PAPER_FOREST_PRODUCTS(61, "Paper & Forest Products", Group.MAN),
  PERFORMING_ARTS(39, "Performing Arts", Group.ART, Group.MED, Group.REC),
  PHARMACEUTICALS(15, "Pharmaceuticals", Group.HLTH, Group.TECH),
  PHILANTHROPY(131, "Philanthropy", Group.ORG),
  PHOTOGRAPHY(136, "Photography", Group.ART, Group.MED, Group.REC),
  PLASTICS(117, "Plastics", Group.MAN),
  POLITICAL_ORGANIZATION(107, "Political Organization", Group.GOV, Group.ORG),
  PRIMARY_SECONDARY_EDUCATION(67, "Primary/Secondary Education", Group.EDU),
  PRINTING(83, "Printing", Group.MED, Group.REC),
  PROFESSIONAL_TRAINING_COACHING(105, "Professional Training & Coaching", Group.CORP),
  PROGRAM_DEVELOPMENT(102, "Program Development", Group.CORP, Group.ORG),
  PUBLIC_POLICY(79, "Public Policy", Group.GOV),
  PUBLIC_RELATIONS_COMMUNICATIONS(98, "Public Relations and Communications", Group.CORP),
  PUBLIC_SAFETY(78, "Public Safety", Group.GOV),
  PUBLISHING(82, "Publishing", Group.MED, Group.REC),
  RAILROAD_MANUFACTURE(62, "Railroad Manufacture", Group.MAN),
  RANCHING(64, "Ranching", Group.AGR),
  REAL_ESTATE(44, "Real Estate", Group.CONS, Group.FIN, Group.GOOD),
  RECREATIONAL_FACILITIES_SERVICES(40, "Recreational Facilities and Services", Group.REC,
      Group.SERV),
  RELIGIOUS_INSTITUTIONS(89, "Religious Institutions", Group.ORG, Group.SERV),
  RENEWABLES_ENVIRONMENT(144, "Renewables & Environment", Group.GOV, Group.MAN, Group.ORG),
  RESEARCH(70, "Research", Group.EDU, Group.GOV),
  RESTAURANTS(32, "Restaurants", Group.REC, Group.SERV),
  RETAIL(27, "Retail", Group.GOOD, Group.MAN),
  SECURITY_INVESTIGATIONS(121, "Security and Investigations", Group.CORP, Group.ORG, Group.SERV),
  SEIMCONDUCTORS(7, "Semiconductors", Group.TECH),
  SHIPBUILDING(58, "Shipbuilding", Group.MAN),
  SPORTING_GOODS(20, "Sporting Goods", Group.GOOD, Group.REC),
  SPORTS(33, "Sports", Group.REC),
  STAFFING_RECRUITING(104, "Staffing and Recruiting", Group.CORP),
  SUPERMARKETS(22, "Supermarkets", Group.GOOD),
  TELECOMMUNICATIONS(8, "Telecommunications", Group.GOV, Group.TECH),
  TEXTILES(60, "Textiles", Group.MAN),
  THINK_TANKS(130, "Think Tanks", Group.GOV, Group.ORG),
  TOBACCO(21, "Tobacco", Group.GOOD),
  TRANSLATION_LOCALIZATION(108, "Translation and Localization", Group.CORP, Group.GOV, Group.SERV),
  TRANSPORTATION_TRUCKING_RAILROAD(92, "Transportation/Trucking/Railroad", Group.TRAN),
  UTILITIES(59, "Utilities", Group.MAN),
  VENTURE_CAPITAL_PRIVATE_EQUITY(106, "Venture Capital & Private Equity", Group.FIN, Group.TECH),
  VETERINARY(16, "Veterinary", Group.HLTH),
  WAREHOUSING(93, "Warehousing", Group.TRAN),
  WHOLESALE(133, "Wholesale", Group.GOOD),
  WINE_SPIRITS(142, "Wine and Spirits", Group.GOOD, Group.MAN, Group.REC),
  WIRELESS(119, "Wireless", Group.TECH),
  WRITING_EDITING(103, "Writing and Editing", Group.ART, Group.MED, Group.REC);
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  @Getter
  private final Integer code;
  
  @Getter final String name;
  
  @Getter
  private final Group[] groups;
  
  IndustryCode(Integer code, String name, Group... groups) {
    this.code = code;
    this.name = name;
    this.groups = groups;
  }
  
  /**
   * Convert the provided code into a status type
   *
   * @param code the code
   * @return if successful the desired status type otherwise null
   */
  public static IndustryCode fromCode(Integer code) {
    for (IndustryCode industryCode : IndustryCode.values()) {
      if (industryCode.getCode().equals(code)) {
        return industryCode;
      }
    }
    LOGGER.warn("Could not get industry code from code " + code);
    return null;
  }
  
  /**
   * Group enum
   * @author Joanna
   *
   */
  public enum Group {
    CORP,
    FIN,
    MAN,
    TECH,
    TRAN,
    LEG,
    ORG,
    HLTH,
    ART,
    MED,
    GOOD,
    CONS,
    REC,
    GOV,
    SERV,
    AGR,
    EDU;
  }

}
