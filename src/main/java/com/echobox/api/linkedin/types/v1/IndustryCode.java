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

  /**
   * The accounting industry code.
   */
  ACCOUNTING(47, "Accounting", Group.CORP, Group.FIN),
  /**
   * The arlines aviation industry code.
   */
  ARLINES_AVIATION(94, "Airlines/Aviation", Group.MAN, Group.TECH, Group.TRAN),
  /**
   * The alternative dispute resolution industry code.
   */
  ALTERNATIVE_DISPUTE_RESOLUTION(120, "Alternative Dispute Resolution", Group.LEG, Group.ORG),
  /**
   * The alternative medicine industry code.
   */
  ALTERNATIVE_MEDICINE(125, "Alternative Medicine", Group.HLTH),
  /**
   * the animation industry code.
   */
  ANIMATION(127, "Animation", Group.ART, Group.MED),
  /**
   * The apparel fashion industry code.
   */
  APPAREL_FASHION(19, "Apparel & Fashion", Group.GOOD),
  /**
   * The architecture planning industry code.
   */
  ARCHITECTURE_PLANNING(50, "Architecture & Planning", Group.CONS),
  /**
   * The arts crafts industry code.
   */
  ARTS_CRAFTS(111, "Arts and Crafts", Group.ART, Group.MED, Group.REC),
  /**
   * The automotive industry code.
   */
  AUTOMOTIVE(53, "Automotive", Group.MAN),
  /**
   * The aviation aerospace industry code.
   */
  AVIATION_AREOSPACE(52, "Aviation & Aerospace", Group.GOV, Group.MAN),
  /**
   * The banking industry code.
   */
  BANKING(14, "Banking", Group.FIN),
  /**
   * The biotechnology industry code.
   */
  BIOTECHNOLOGY(12, "Biotechnology", Group.GOV, Group.HLTH, Group.TECH),
  /**
   * The broadcast media industry code.
   */
  BROADCAST_MEDIA(36, "Broadcast Media", Group.MED, Group.REC),
  /**
   * The building materials industry code.
   */
  BUILDING_MATERIALS(49, "Building Materials", Group.CONS),
  /**
   * The business supplies equipment industry code.
   */
  BUSINESS_SUPPLIES_EQUIPMENT(138, "Business Supplies and Equipment", Group.CORP, Group.MAN),
  /**
   * The capital markets industry code.
   */
  CAPITAL_MARKETS(129, "Capital Markets", Group.FIN),
  /**
   * The chemicals industry code.
   */
  CHEMICALS(54, "Chemicals", Group.MAN),
  /**
   * The civic social organisation industry code.
   */
  CIVIC_SOCIAL_ORGANISATION(90, "Civic & Social Organization", Group.ORG, Group.SERV),
  /**
   * The civil engineering industry code.
   */
  CIVIL_ENGINEERING(51, "Civil Engineering", Group.CONS, Group.GOV),
  /**
   * The commercial real estate industry code.
   */
  COMMERCIAL_REAL_ESTATE(128, "Commercial Real Estate", Group.CONS, Group.CORP, Group.FIN),
  /**
   * The computer network security industry code.
   */
  COMPUTER_NETWORK_SECURITY(118, "Computer & Network Security", Group.TECH),
  /**
   * The computer games industry code.
   */
  COMPUTER_GAMES(109, "Computer Games", Group.MED, Group.REC),
  /**
   * The computer hardware industry code.
   */
  COMPUTER_HARDWARE(3, "Computer Hardware", Group.TECH),
  /**
   * The computer networking industry code.
   */
  COMPUTER_NETWORKING(5, "Computer Networking", Group.TECH),
  /**
   * The computer software industry code.
   */
  COMPUTER_SOFTWARE(4, "Computer Software", Group.TECH),
  /**
   * The construction industry code.
   */
  CONSTRUCTION(48, "Construction", Group.CONS),
  /**
   * The consumer electronics industry code.
   */
  CONSUMER_ELECTRONICS(24, "Consumer Electronics", Group.GOOD, Group.MAN),
  /**
   * The consumer goods industry code.
   */
  CNSUMER_GOODS(25, "Consumer Goods", Group.GOOD, Group.MAN),
  /**
   * The consumer services industry code.
   */
  CONSUMER_SERVICES(91, "Consumer Services", Group.ORG, Group.SERV),
  /**
   * Cosmetics industry code.
   */
  COSMETICS(18, "Cosmetics", Group.GOOD),
  /**
   * The dairy industry code.
   */
  DAIRY(65, "Dairy", Group.AGR),
  /**
   * the defense space industry code.
   */
  DEFENSE_SPACE(1, "Defense & Space", Group.GOV, Group.TECH),
  /**
   * The design industry code.
   */
  DESIGN(99, "Design", Group.ART, Group.MED),
  /**
   * The educational management industry code.
   */
  EDUCATIONAL_MANAGEMENT(69, "Education Management", Group.EDU),
  /**
   * The E-learning industry code.
   */
  E_LEARNING(132, "E-Learning", Group.EDU, Group.MAN),
  /**
   * The electronical electronic manufacturing industry code.
   */
  ELECTRONICAL_ELECTRONIC_MANUFACTORING(112, "Electrical/Electronic Manufacturing", Group.GOOD,
      Group.MAN),
  /**
   * The entertainment industry code.
   */
  ENTERTAINMENT(28, "Entertainment", Group.MED, Group.REC),
  /**
   * The environmental services industry code.
   */
  ENVIRONMENTAL_SERVICES(86, "Environmental Services", Group.ORG, Group.SERV),
  /**
   * The events services industry code.
   */
  EVENTS_SERVICES(110, "Events Services", Group.CORP, Group.REC, Group.SERV),
  /**
   * The executive office industry code.
   */
  EXECUTIVE_OFFICE(76, "Executive Office", Group.GOV),
  /**
   * The facilities services industry code.
   */
  FACILITIES_SERVICES(122, "Facilities Services", Group.CORP, Group.SERV),
  /**
   * The farming industry code.
   */
  FARMING(63, "Farming", Group.AGR),
  /**
   * The financial services industry code.
   */
  FINANCIAL_SERVICES(43, "Financial Services", Group.FIN),
  /**
   * The fine art industry code.
   */
  FINE_ART(38, "Fine Art", Group.ART, Group.MED, Group.REC),
  /**
   * The fishery industry code.
   */
  FISHERY(66, "Fishery", Group.AGR),
  /**
   * The food beverages industry code.
   */
  FOOD_BEVERAGES(34, "Food & Beverages", Group.REC, Group.SERV),
  /**
   * The food production industry code.
   */
  FOOD_PRODUCTION(23, "Food Production", Group.GOOD, Group.MAN, Group.SERV),
  /**
   * The fund raising industry code.
   */
  FUND_RAISING(101, "Fund-Raising", Group.AGR),
  /**
   * The furniture industry code.
   */
  FURNITURE(26, "Furniture", Group.GOOD, Group.MAN),
  /**
   * The gambling casinos industry code.
   */
  GAMBLING_CASINOS(29, "Gambling & Casinos", Group.REC),
  /**
   * The glass ceramics concrete industry code.
   */
  GLASS_CERMAICS_CONCRETE(145, "Glass, Ceramics & Concrete", Group.CONS, Group.MAN),
  /**
   * The government administration industry code.
   */
  GOVENMENT_ADMINISTRATION(75, "Government Administration", Group.GOV),
  /**
   * The government relations industry code.
   */
  GOVERNMENT_RELATIONS(148, "Government Relations", Group.GOV),
  /**
   * The graphic design industry code.
   */
  GRAPHIC_DESIGN(140, "Graphic Design", Group.GOV),
  /**
   * The health wellness fitness industry code.
   */
  HEALTH_WELLNESS_FITNESS(124, "Health, Wellness and Fitness", Group.HLTH, Group.REC),
  /**
   * The higher education industry code.
   */
  HIGHER_EDUCATION(68, "Higher Education", Group.EDU),
  /**
   * The hospital health care industry code.
   */
  HOSPITAL_HEALTH_CARE(14, "Hospital & Health Care", Group.HLTH),
  /**
   * The hospitality industry code.
   */
  HOSPITALITY(31, "Hospitality", Group.REC, Group.SERV, Group.TRAN),
  /**
   * The human resources industry code.
   */
  HUMAN_RESOURCES(137, "Human Resources", Group.CORP),
  /**
   * The import export industry code.
   */
  IMPORT_EXPORT(134, "Import and Export", Group.CORP, Group.GOOD, Group.TRAN),
  /**
   * The individual family services industry code.
   */
  INDIVIDUAL_FAMILY_SERVICES(88, "Individual & Family Services", Group.ORG, Group.SERV),
  /**
   * The industrial automation industry code.
   */
  INDUSTRIAL_AUTOMATION(147, "Industrial Automation", Group.CONS, Group.MAN),
  /**
   * The informational services industry code.
   */
  INFORMATIONAL_SERVICES(84, "Information Services", Group.MED, Group.SERV),
  /**
   * The informational technology services industry code.
   */
  INFORMATIONAL_TECHNOLOGY_SERVICES(96, "Information Technology and Services", Group.TECH),
  /**
   * The insurance industry code.
   */
  INSURANCE(42, "Insurance", Group.FIN),
  /**
   * The international affairs industry code.
   */
  INTERNATIONAL_AFFAIRS(74, "International Affairs", Group.GOV),
  /**
   * The international trade development industry code.
   */
  INTERNATIONAL_TRADE_DEVELOPMENT(141, "International Trade and Development", Group.GOV, Group.ORG,
      Group.TRAN),
  /**
   * The internet industry code.
   */
  INTERNET(6, "Internet", Group.TECH),
  /**
   * The investment banking industry code.
   */
  INVESTMENT_BANKING(45, "Investment Banking", Group.FIN),
  /**
   * The investment management industry code.
   */
  INVESTMENT_MANAGEMENT(46, "Investment Management", Group.FIN),
  /**
   * The judiciary industry code.
   */
  JUDICIARY(73, "Judiciary", Group.GOV, Group.LEG),
  /**
   * The law inforcement industry code.
   */
  LAW_INFORCEMENT(77, "Law Enforcement", Group.GOV, Group.LEG),
  /**
   * The law practice industry code.
   */
  LAW_PRACTICE(9, "Law Practice", Group.LEG),
  /**
   * The legal services industry code.
   */
  LEGAL_SERVICES(10, "Legal Services", Group.LEG),
  /**
   * The legislative office industry code.
   */
  LEGISLATIVE_OFFICE(72, "Legislative Office", Group.GOV, Group.LEG),
  /**
   * The leisure travel tourism industry code.
   */
  LESUIRE_TRAVEL_TOURISM(30, "Leisure, Travel & Tourism", Group.GOV, Group.LEG),
  /**
   * The libraries industry code.
   */
  LIBRARIES(85, "Libraries", Group.MED, Group.REC, Group.SERV),
  /**
   * The logistics supply chain industry code.
   */
  LOGISTICS_SUPPLY_CHAIN(116, "Logistics and Supply Chain", Group.CORP, Group.TRAN),
  /**
   * The Luxury goods jewelry industry code.
   */
  LUXURY_GOODS_JEWELRY(143, "Luxury Goods & Jewelry", Group.GOOD),
  /**
   * The machinery industry code.
   */
  MACHINERY(55, "Machinery", Group.MAN),
  /**
   * The management consulting industry code.
   */
  MANAGEMENT_CONSULTING(11, "Management Consulting", Group.CORP),
  /**
   * The maritime industry code.
   */
  MARITIME(95, "Maritime", Group.TRAN),
  /**
   * The market research industry code.
   */
  MARKET_RESEARCH(97, "Market Research", Group.CORP),
  /**
   * The marketing advertising industry code.
   */
  MARKETING_ADVERTISING(80, "Marketing and Advertising", Group.CORP, Group.MED),
  /**
   * The mechanical industrial engineering industry code.
   */
  MECHANICAL_INDUSTRIAL_ENGINEERING(135, "Mechanical or Industrial Engineering", Group.CONS,
      Group.GOV, Group.MAN),
  /**
   * The media production industry code.
   */
  MEDIA_PRODUCTION(126, "Media Production", Group.MED, Group.REC),
  /**
   * The medical devices industry code.
   */
  MEDICAL_DEVICES(17, "Medical Devices", Group.HLTH),
  /**
   * The medical practicies industry code.
   */
  MEDICAL_PRACTICIES(13, "Medical Practice", Group.HLTH),
  /**
   * The mental health care industry code.
   */
  MENTAL_HEALTH_CARE(139, "Medical Practice", Group.HLTH),
  /**
   * The military industry code industry code.
   */
  MILITARY(71, "Military", Group.GOV),
  /**
   * mining materials industry code.
   */
  MINING_MATERIALS(56, "Mining & Metals", Group.MAN),
  /**
   * The motion pictures film industry code.
   */
  MOTION_PICTURES_FILM(35, "Motion Pictures and Film", Group.ART, Group.MED, Group.REC),
  /**
   * The museums institutions industry code.
   */
  MUSEUMS_INSTITUTIONS(37, "Museums and Institutions", Group.ART, Group.MED, Group.REC),
  /**
   * The music industry code.
   */
  MUSIC(115, "Music", Group.ART, Group.REC),
  /**
   * The nanotechnology industry code.
   */
  NANOTECHNOLOGY(114, "Nanotechnology", Group.GOV, Group.MAN, Group.TECH),
  /**
   * The newspapers industry code.
   */
  NEWSPAPERS(81, "Newspapers", Group.MED, Group.REC),
  /**
   * The non profit organization management industry code.
   */
  NON_PROFIT_ORGANIZATION_MANAGEMENT(100, "Non-Profit Organization Management", Group.ORG),
  /**
   * The oil energy industry code.
   */
  OIL_ENERGY(57, "Oil & Energy", Group.MAN),
  /**
   * The online media industry code.
   */
  ONLINE_MEDIA(113, "Online Media", Group.MED),
  /**
   * The outsourcing offshoring industry code.
   */
  OUTSOURCING_OFFSHORING(123, "Outsourcing/Offshoring", Group.CORP),
  /**
   * The package freight delivery industry code.
   */
  PACKAGE_FREIGHT_DELIVERY(87, "Package/Freight Delivery", Group.SERV, Group.TRAN),
  /**
   * The packaging containers industry code.
   */
  PACKAGING_CONTAINERS(146, "Packaging and Containers", Group.GOOD, Group.MAN),
  /**
   * The paper forest products industry code.
   */
  PAPER_FOREST_PRODUCTS(61, "Paper & Forest Products", Group.MAN),
  /**
   * The performing arts industry code.
   */
  PERFORMING_ARTS(39, "Performing Arts", Group.ART, Group.MED, Group.REC),
  /**
   * The pharmaceuticals industry code.
   */
  PHARMACEUTICALS(15, "Pharmaceuticals", Group.HLTH, Group.TECH),
  /**
   * The philanthropy industry code.
   */
  PHILANTHROPY(131, "Philanthropy", Group.ORG),
  /**
   * The photography industry code.
   */
  PHOTOGRAPHY(136, "Photography", Group.ART, Group.MED, Group.REC),
  /**
   * The plastics industry code.
   */
  PLASTICS(117, "Plastics", Group.MAN),
  /**
   * The political organization industry code.
   */
  POLITICAL_ORGANIZATION(107, "Political Organization", Group.GOV, Group.ORG),
  /**
   * The primary secondary education industry code.
   */
  PRIMARY_SECONDARY_EDUCATION(67, "Primary/Secondary Education", Group.EDU),
  /**
   * The printing industry code.
   */
  PRINTING(83, "Printing", Group.MED, Group.REC),
  /**
   * The professional training coaching industry code.
   */
  PROFESSIONAL_TRAINING_COACHING(105, "Professional Training & Coaching", Group.CORP),
  /**
   * The program development industry code.
   */
  PROGRAM_DEVELOPMENT(102, "Program Development", Group.CORP, Group.ORG),
  /**
   * The public policy industry code.
   */
  PUBLIC_POLICY(79, "Public Policy", Group.GOV),
  /**
   * The public relations communications industry code.
   */
  PUBLIC_RELATIONS_COMMUNICATIONS(98, "Public Relations and Communications", Group.CORP),
  /**
   * The public safety industry code.
   */
  PUBLIC_SAFETY(78, "Public Safety", Group.GOV),
  /**
   * The publishing industry code.
   */
  PUBLISHING(82, "Publishing", Group.MED, Group.REC),
  /**
   * The railroad manufacture industry code.
   */
  RAILROAD_MANUFACTURE(62, "Railroad Manufacture", Group.MAN),
  /**
   * The ranching industry code.
   */
  RANCHING(64, "Ranching", Group.AGR),
  /**
   * The real estate industry code.
   */
  REAL_ESTATE(44, "Real Estate", Group.CONS, Group.FIN, Group.GOOD),
  /**
   * The recreational facilities services industry code.
   */
  RECREATIONAL_FACILITIES_SERVICES(40, "Recreational Facilities and Services", Group.REC,
      Group.SERV),
  /**
   * The religious institutions industry code.
   */
  RELIGIOUS_INSTITUTIONS(89, "Religious Institutions", Group.ORG, Group.SERV),
  /**
   * The renewables environment industry code.
   */
  RENEWABLES_ENVIRONMENT(144, "Renewables & Environment", Group.GOV, Group.MAN, Group.ORG),
  /**
   * the research industry code.
   */
  RESEARCH(70, "Research", Group.EDU, Group.GOV),
  /**
   * The restaurants industry code.
   */
  RESTAURANTS(32, "Restaurants", Group.REC, Group.SERV),
  /**
   * The retail industry code.
   */
  RETAIL(27, "Retail", Group.GOOD, Group.MAN),
  /**
   * The security investigations industry code.
   */
  SECURITY_INVESTIGATIONS(121, "Security and Investigations", Group.CORP, Group.ORG, Group.SERV),
  /**
   * The seimconductors industry code.
   */
  SEIMCONDUCTORS(7, "Semiconductors", Group.TECH),
  /**
   * Th shipbuilding industry code.
   */
  SHIPBUILDING(58, "Shipbuilding", Group.MAN),
  /**
   * The sporting goods industry code.
   */
  SPORTING_GOODS(20, "Sporting Goods", Group.GOOD, Group.REC),
  /**
   * The sports industry code.
   */
  SPORTS(33, "Sports", Group.REC),
  /**
   * The staffing recruiting industry code.
   */
  STAFFING_RECRUITING(104, "Staffing and Recruiting", Group.CORP),
  /**
   * The supermarkets industry code.
   */
  SUPERMARKETS(22, "Supermarkets", Group.GOOD),
  /**
   * The telecommunications industry code.
   */
  TELECOMMUNICATIONS(8, "Telecommunications", Group.GOV, Group.TECH),
  /**
   * The textiles industry code.
   */
  TEXTILES(60, "Textiles", Group.MAN),
  /**
   * The think tanks industry code.
   */
  THINK_TANKS(130, "Think Tanks", Group.GOV, Group.ORG),
  /**
   * The tobacco industry code.
   */
  TOBACCO(21, "Tobacco", Group.GOOD),
  /**
   * The translation localization industry code.
   */
  TRANSLATION_LOCALIZATION(108, "Translation and Localization", Group.CORP, Group.GOV, Group.SERV),
  /**
   * The transportation trucking railroad industry code.
   */
  TRANSPORTATION_TRUCKING_RAILROAD(92, "Transportation/Trucking/Railroad", Group.TRAN),
  /**
   * The utilities industry code.
   */
  UTILITIES(59, "Utilities", Group.MAN),
  /**
   * The venture capital private equity industry code.
   */
  VENTURE_CAPITAL_PRIVATE_EQUITY(106, "Venture Capital & Private Equity", Group.FIN, Group.TECH),
  /**
   * VThe veterinary industry code.
   */
  VETERINARY(16, "Veterinary", Group.HLTH),
  /**
   * The warehousing industry code.
   */
  WAREHOUSING(93, "Warehousing", Group.TRAN),
  /**
   * The wholesale industry code.
   */
  WHOLESALE(133, "Wholesale", Group.GOOD),
  /**
   * The wine spirits industry code.
   */
  WINE_SPIRITS(142, "Wine and Spirits", Group.GOOD, Group.MAN, Group.REC),
  /**
   * The wireless industry code.
   */
  WIRELESS(119, "Wireless", Group.TECH),
  /**
   * The writing editing industry code.
   */
  WRITING_EDITING(103, "Writing and Editing", Group.ART, Group.MED, Group.REC);
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  @Getter
  private final Integer code;

  /**
   * The industry name.
   */
  @Getter final String name;
  
  @Getter
  private final Group[] groups;

  /**
   * Instantiates new industry code
   * @param code the industry code
   * @param name the industry name
   * @param groups the industry groups
   */
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
    /**
     * CORP industry group.
     */
    CORP,
    /**
     * FIN industry group.
     */
    FIN,
    /**
     * MAN industry group.
     */
    MAN,
    /**
     * TECH industry group.
     */
    TECH,
    /**
     * TRAN industry group.
     */
    TRAN,
    /**
     * LEG industry group.
     */
    LEG,
    /**
     * ORG industry group.
     */
    ORG,
    /**
     * HLTH industry group.
     */
    HLTH,
    /**
     * ART industry group.
     */
    ART,
    /**
     * MED industry group.
     */
    MED,
    /**
     * GOOD industry group.
     */
    GOOD,
    /**
     * CONS industry group.
     */
    CONS,
    /**
     * REC industry group.
     */
    REC,
    /**
     * GOV industry group.
     */
    GOV,
    /**
     * SERV industry group.
     */
    SERV,
    /**
     * AGR industry group.
     */
    AGR,
    /**
     * EDU industry group.
     */
    EDU;
  }

}
