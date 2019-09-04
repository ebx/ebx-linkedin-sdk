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

import lombok.Getter;

/**
 * StockExchange
 * @author Joanna
 *
 */
public enum StockExchange implements CodeType<Integer> {

  /**
   * The American stock exchange.
   */
  ASE(1, "American Stock Exchange"),
  /**
   * The New York stock exchange.
   */
  NYS(2, "New York Stock Exchange"),
  /**
   * The National market stock exchange.
   */
  NMS(3, "NASDAQ"),
  /**
   * The London stock exchange.
   */
  LSE(4, "London Stock Exchange"),
  /**
   * The Frankfurt stock exchange.
   */
  FRA(5, "Frankfurt Stock Exchange"),
  /**
   * The German stock excahnge.
   */
  GER(6, "XETRA Stock Exchange"),
  /**
   * The Paris stock exchange.
   */
  PAR(7, "Euronext Paris");
  
  @Getter
  private final Integer code;
  
  @Getter
  private String name;

  /**
   * Instantiates new stock exchange
   * @param code the stock exchange code
   * @param name the stock exchange name
   */
  StockExchange(int code, String name) {
    this.code = code;
    this.name = name;
  }
  
  /**
   * Convert the provided code into a status type
   *
   * @param code the code
   * @return if successful the desired status type otherwise null
   */
  public static StockExchange fromCode(String code) {
    for (StockExchange stockExchange : StockExchange.values()) {
      if (stockExchange.getCode().equals(code)) {
        return stockExchange;
      }
    }
    return null;
  }

}
