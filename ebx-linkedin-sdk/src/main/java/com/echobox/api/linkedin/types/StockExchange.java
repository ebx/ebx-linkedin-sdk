package com.echobox.api.linkedin.types;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

public enum StockExchange {
  
  AMERICAN_STOCK_EXCHANGE("ASE"),
  NEW_YORK_STOCK_EXCHANGE("NYS"),
  NASDAQ("NMS"),
  LONDON_STOCK_EXCHANGE("LSE"),
  FRANKFURT_STOCK_EXCHANGE("FRA"),
  XETRA_TRADING_PLATFORM("GER"),
  EURONEXT_PARIS("PAR");
  
  @Getter
  private String code;
  
  StockExchange(String code) {
    this.code = code;
  }
  
  public static StockExchange fromCode(String code) {
    if (!StringUtils.isBlank(code)) {
      for (StockExchange stockExchange : StockExchange.values()) {
        if (stockExchange.code.equals(code)) {
          return stockExchange;
        }
      }
    }
    return null;
  }

}
