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

package com.echobox.api.linkedin.util;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON utility class
 * @author Joanna
 *
 */
public abstract class JsonUtils {
  
  /**
   * Convert a JsonObject to a map
   * @param jsonObject the json object to convert
   * @return a map representing the JSON object
   */
  public static Map<String, Object> toMap(JsonObject jsonObject) {
    Map<String, Object> map = new HashMap<>();
    jsonObject.names().forEach(key -> {
      JsonValue jsonValue = jsonObject.get(key);
      map.put(key, getValue(jsonValue));
    });

    return map;
  }

  private static List<Object> toList(JsonArray array) throws ParseException {
    List<Object> list = new ArrayList<>();
    array.forEach(jsonValue -> {
      list.add(getValue(jsonValue));
    });
    return list;
  }
  
  private static Object getValue(JsonValue jsonValue) {
    Object value = jsonValue;
    if (jsonValue.isArray()) {
      value = toList(jsonValue.asArray());
    } else if (jsonValue.isObject()) {
      value = toMap(jsonValue.asObject());
    } else if (jsonValue.isString()) {
      value = jsonValue.asString();
    } else if (jsonValue.isNumber()) {
      value = getNumber(jsonValue);
    }
    return value;
  }
  
  private static Object getNumber(JsonValue jsonValue) {
    try {
      return jsonValue.asLong();
    } catch (NumberFormatException e) {
      try {
        return jsonValue.asDouble();
      } catch (NumberFormatException ex) {
        return jsonValue;
      }
    }
  }

}
