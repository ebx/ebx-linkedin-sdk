/**
 * Copyright (c) 2010-2018 Mark Allen, Norbert Bartels.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
//Source - https://restfb.com/

package com.echobox.api.linkedin.util;

import static java.lang.String.format;

import com.echobox.api.linkedin.logging.LinkedInLogger;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.Date;

/**
 * A collection of date-handling utility methods.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public final class DateUtils {
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  /**
   * LinkedIn "long" date format (IETF RFC 3339). Example: {@code 2010-02-28T16:11:08+0000}
   */
  public static final String LINKEDIN_LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

  /**
   * LinkedIn "long" date format (IETF RFC 3339) without a timezone component. Example: {@code 2010-02-28T16:11:08}
   */
  public static final String LINKEDIN_LONG_DATE_FORMAT_WITHOUT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";

  /**
   * LinkedIn "long" date format (IETF RFC 3339) without a timezone or seconds component. Example: {@code 2010-02-28T16:11}
   */
  public static final String LINKEDIN_LONG_DATE_FORMAT_WITHOUT_TIMEZONE_OR_SECONDS = "yyyy-MM-dd'T'HH:mm";

  /**
   * LinkedIn short date format. Example: {@code 04/15/1984}
   */
  public static final String LINKEDIN_SHORT_DATE_FORMAT = "MM/dd/yyyy";

  /**
   * LinkedIn alternate short date format. Example: {@code 2012-09-15}
   */
  public static final String LINKEDIN_ALTERNATE_SHORT_DATE_FORMAT = "yyyy-MM-dd";

  /**
   * LinkedIn month-year only date format. Example: {@code Example: 2007-03}
   */
  public static final String LINKEDIN_MONTH_YEAR_DATE_FORMAT = "yyyy-MM";

  /**
   * DateFormatStrategy (default: SimpleDateFormat).
   */
  private static DateFormatStrategy strategy = new SimpleDateFormatStrategy();

  /**
   * Prevents instantiation.
   */
  private DateUtils() {
    // Prevents instantiation
  }

  /**
   * Returns a Java representation of a LinkedIn "long" {@code date} string, or the number of seconds since the epoch.
   * <p>
   * Supports dates with or without timezone information.
   * 
   * @param date
   *          LinkedIn {@code date} string.
   * @return Java date representation of the given LinkedIn "long" {@code date} string or {@code null} if {@code date}
   *         is {@code null} or invalid.
   */
  public static Date toDateFromLongFormat(String date) {
    if (date == null) {
      return null;
    }

    // Is this an all-digit date? Then assume it's the "seconds since epoch"
    // variant
    if (date.trim().matches("\\d+")) {
      return new Date(Long.parseLong(date) * 1000L);
    }

    Date parsedDate = toDateWithFormatString(date, LINKEDIN_LONG_DATE_FORMAT);

    // Fall back to variant without timezone if the initial parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, LINKEDIN_LONG_DATE_FORMAT_WITHOUT_TIMEZONE);
    }

    // Fall back to variant without seconds if secondary parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, LINKEDIN_LONG_DATE_FORMAT_WITHOUT_TIMEZONE_OR_SECONDS);
    }

    return parsedDate;
  }

  /**
   * Returns a Java representation of a LinkedIn "short" {@code date} string.
   * 
   * @param date
   *          LinkedIn {@code date} string.
   * @return Java date representation of the given LinkedIn "short" {@code date} string or {@code null} if {@code date}
   *         is {@code null} or invalid.
   */
  public static Date toDateFromShortFormat(String date) {
    if (date == null) {
      return null;
    }

    Date parsedDate = toDateWithFormatString(date, LINKEDIN_SHORT_DATE_FORMAT);

    // Fall back to variant if initial parse fails
    if (parsedDate == null) {
      parsedDate = toDateWithFormatString(date, LINKEDIN_ALTERNATE_SHORT_DATE_FORMAT);
    }

    return parsedDate;
  }

  /**
   * Returns a Java representation of a LinkedIn "month-year" {@code date} string.
   * 
   * @param date
   *          LinkedIn {@code date} string.
   * @return Java date representation of the given LinkedIn "month-year" {@code date} string or {@code null} if
   *         {@code date} is {@code null} or invalid.
   */
  public static Date toDateFromMonthYearFormat(String date) {
    if (date == null) {
      return null;
    }

    if ("0000-00".equals(date)) {
      return null;
    }

    return toDateWithFormatString(date, LINKEDIN_MONTH_YEAR_DATE_FORMAT);
  }

  /**
   * Returns a String representation of a {@code date} object
   * 
   * @param date
   *          as Date
   * @return String representation of a {@code date} object. The String is in the form {@code 2010-02-28T16:11:08}
   */
  public static String toLongFormatFromDate(Date date) {
    if (date == null) {
      return null;
    }

    return strategy.formatFor(LINKEDIN_LONG_DATE_FORMAT_WITHOUT_TIMEZONE).format(date);
  }

  /**
   * Returns a Java representation of a {@code date} string.
   * 
   * @param date
   *          Date in string format.
   * @return Java date representation of the given {@code date} string or {@code null} if {@code date} is {@code null}
   *         or invalid.
   */
  private static Date toDateWithFormatString(String date, String format) {
    if (date == null) {
      return null;
    }

    try {
      return strategy.formatFor(format).parse(date);
    } catch (ParseException e) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER.trace(format("Unable to parse date '%s' using format string '%s': %s", date, format, e));
      }

      return null;
    }
  }

  /**
   * get the current DateFormatStrategy.
   * 
   * @return the current DateFormatStrategy
   */
  public static DateFormatStrategy getDateFormatStrategy() {
    return strategy;
  }

  /**
   * set the {@link DateFormatStrategy}.
   * 
   * default value: {@link SimpleDateFormatStrategy}
   * 
   * @param dateFormatStrategy
   *          the used @see com.echobox.api.linkedin.util.DateFormatStrategy
   * 
   */
  public static void setDateFormatStrategy(DateFormatStrategy dateFormatStrategy) {
    strategy = dateFormatStrategy;
  }
}
