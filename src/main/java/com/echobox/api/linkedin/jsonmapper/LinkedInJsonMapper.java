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

package com.echobox.api.linkedin.jsonmapper;

import static java.util.Collections.unmodifiableList;

import com.echobox.api.linkedin.exception.LinkedInJsonMappingException;
import com.echobox.api.linkedin.logging.LinkedInLogger;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * LinkedIn implementation of a JSON-to-Java mapper.
 * 
 * @author Joanna
 *
 */
public class LinkedInJsonMapper extends DefaultJsonMapper {

  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();

  /**
   * Creates a JSON mapper which will throw
   * {@link com.echobox.api.linkedin.exception.LinkedInJsonMappingException} whenever an error
   * occurs when mapping JSON data to Java objects.
   */
  public LinkedInJsonMapper() {
    super();
  }

  /**
   * Creates a JSON mapper which delegates to the provided {@code jsonMappingErrorHandler} for
   * handling mapping errors.
   * 
   * @param jsonMappingErrorHandler
   *          The JSON mapping error handler to use.
   * @throws IllegalArgumentException
   *           If {@code jsonMappingErrorHandler} is {@code null}.
   */
  public LinkedInJsonMapper(JsonMappingErrorHandler jsonMappingErrorHandler) {
    super(jsonMappingErrorHandler);
  }

  @Override
  public <T> List<T> toJavaList(String json, Class<T> type) {
    if (type == null) {
      throw new LinkedInJsonMappingException("You must specify the Java type to map to.");
    }

    json = StringUtils.trimToEmpty(json);

    if (StringUtils.isBlank(json)) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, null)) {
        return null;
      }
      throw new LinkedInJsonMappingException("JSON is an empty string - can't map it.");
    }

    if (json.startsWith("{")) {
      // LinkedIn may return an empty object as a list - handle it here
      if (isEmptyObject(json)) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER
              .trace("Encountered {} when we should've seen []. Mapping the {} as an empty list and"
                  + "moving on...");
        }
        return new ArrayList<>();
      }

      // LinkedIn returns arrays wrapped in an object
      // Extract the values out of the JSON response
      try {
        JSONObject jsonObject = new JSONObject(json);
        int total = jsonObject.optInt("_total");
        JSONArray values = jsonObject.optJSONArray("values");
        if (values == null) {
          if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("No values provided in the JSON, carrying on...");
          }
          return new ArrayList<>();
        }
        if (total != values.length()) {
          LOGGER
              .error("The total number of object expected was different to the size or the array."
                  + "Total was " + total + " but response contained " + values.length());
        }
        return toJavaListInternal(values, type);
      } catch (JSONException ex) {
        // Should never get here, but just in case...
        if (jsonMappingErrorHandler.handleMappingError(json, type, ex)) {
          return null;
        } else {
          throw new LinkedInJsonMappingException(
              "Unable to convert LinkedIn response JSON to a list of " + type.getName()
                  + " instances.  Offending JSON is " + json, ex);
        }
      }

    } else {
      // If LinkedIn returns an actual array, at least we handle it here...
      return toJavaListInternal(new JSONArray(json), type);      
    }
  }

  private <T> List<T> toJavaListInternal(JSONArray jsonArray, Class<T> type) {
    try {
      List<T> list = new ArrayList<>();
      for (int i = 0; i < jsonArray.length(); i++) {
        String rebuildJson = jsonArray.get(i).toString();
        if (jsonArray.optJSONArray(i) == null && rebuildJson.startsWith("[")) {
          // the inner JSON starts with square brackets but the parser don't think this is a JSON
          // array so we think the parser is right and add quotes around the string
          list.add(toJavaObject('"' + rebuildJson + '"', type));
        } else {
          list.add(toJavaObject(rebuildJson, type));
        }
      }

      return unmodifiableList(list);
    } catch (Exception ex) {
      if (jsonMappingErrorHandler.handleMappingError(jsonArray.toString(), type, ex)) {
        return null;
      } else {
        throw new LinkedInJsonMappingException(
            "Unable to convert LinkedIn response JSON to a list of " + type.getName()
                + " instances", ex);
      }
    }
  }
}
