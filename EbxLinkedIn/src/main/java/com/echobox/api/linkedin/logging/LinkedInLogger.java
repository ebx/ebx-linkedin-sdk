/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
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
package com.echobox.api.linkedin.logging;

import com.echobox.api.linkedin.exception.LinkedInLoggerException;

import java.lang.reflect.Constructor;

/**
 *
 */
public abstract class LinkedInLogger {

  private static Class LOGGER_CLASS = null;

  public static final LinkedInLogger HTTP_LOGGER;

  public static final LinkedInLogger MAPPER_LOGGER;

  public static final LinkedInLogger UTILS_LOGGER;

  public static final LinkedInLogger CLIENT_LOGGER;

  public static final LinkedInLogger VALUE_FACTORY_LOGGER;
  
  public static final LinkedInLogger TYPE_LOGGER;
  
  private static final String PACKAGE_NAME = "com.echobox.api.linkedin.";

  static {
    // define our logger class
    Class loggerClass = JulLogger.class;
    LOGGER_CLASS = loggerClass;

    // create the loggers
    HTTP_LOGGER = getLoggerInstance(PACKAGE_NAME + "HTTP");
    MAPPER_LOGGER = getLoggerInstance(PACKAGE_NAME + "DefaultJsonMapper");
    UTILS_LOGGER = getLoggerInstance(PACKAGE_NAME + "UTILITY");
    CLIENT_LOGGER = getLoggerInstance(PACKAGE_NAME + "DefaultFacebookClient");
    VALUE_FACTORY_LOGGER = getLoggerInstance(PACKAGE_NAME + "webhook.ChangeValueFactory");
    TYPE_LOGGER = getLoggerInstance(PACKAGE_NAME + "TYPES");
  }

  public static LinkedInLogger getLoggerInstance(String logCategory) {
    Object obj;
    Class[] ctrTypes = new Class[] { String.class };
    Object[] ctrArgs = new Object[] { logCategory };
    try {
      Constructor ctor = LOGGER_CLASS.getConstructor(ctrTypes);
      obj = ctor.newInstance(ctrArgs);
    } catch (Exception e) {
      throw new LinkedInLoggerException("logger cannot be created");
    }

    return (LinkedInLogger) obj;
  }

  public abstract void trace(Object msg);

  public abstract void trace(Object msg, Throwable thr);

  public abstract void debug(Object msg);

  public abstract void debug(Object msg, Throwable thr);

  public abstract void info(Object msg);

  public abstract void info(Object msg, Throwable thr);

  public abstract void warn(Object msg);

  public abstract void warn(Object msg, Throwable thr);

  public abstract void error(Object msg);

  public abstract void error(Object msg, Throwable thr);

  public abstract void fatal(Object msg);

  public abstract void fatal(Object msg, Throwable thr);

  public abstract boolean isDebugEnabled();

  public abstract boolean isInfoEnabled();

  public abstract boolean isTraceEnabled();
}
