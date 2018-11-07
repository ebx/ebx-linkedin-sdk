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

package com.echobox.api.linkedin.jsonmapper;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import com.echobox.api.linkedin.logging.LinkedInLogger;
import com.echobox.api.linkedin.util.DateUtils;
import com.echobox.api.linkedin.util.ReflectionUtils;
import com.echobox.api.linkedin.util.ReflectionUtils.FieldWithAnnotation;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Default implementation of a JSON-to-Java mapper.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class DefaultJsonMapper implements JsonMapper {

  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();

  /**
   * We call this instance's
   * {@link JsonMappingErrorHandler#handleMappingError(String, Class, Exception)} method on
   * mapping failure so client code can decide how to handle the problem.
   */
  protected JsonMappingErrorHandler jsonMappingErrorHandler;

  /**
   * Creates a JSON mapper which will throw
   * {@link com.echobox.api.linkedin.exception.LinkedInJsonMappingException} whenever an error
   * occurs when mapping JSON data to Java objects.
   */
  public DefaultJsonMapper() {
    this(new JsonMappingErrorHandler() {
      /**
       * @see com.echobox.api.linkedin.DefaultJsonMapper.JsonMappingErrorHandler
       * #handleMappingError(java.lang.String, java.lang.Class,
       *      java.lang.Exception)
       */
      @Override
      public boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception ex) {
        return false;
      }
    });
  }

  /**
   * Creates a JSON mapper which delegates to the provided {@code jsonMappingErrorHandler}
   * for handling mapping errors.
   * 
   * @param jsonMappingErrorHandler
   *          The JSON mapping error handler to use.
   * @throws IllegalArgumentException
   *           If {@code jsonMappingErrorHandler} is {@code null}.
   * @since 1.6.2
   */
  public DefaultJsonMapper(JsonMappingErrorHandler jsonMappingErrorHandler) {
    if (jsonMappingErrorHandler == null) {
      throw new IllegalArgumentException("The jsonMappingErrorHandler parameter cannot be null.");
    }

    this.jsonMappingErrorHandler = jsonMappingErrorHandler;
  }

  /**
   * @see com.echobox.api.linkedin.jsonmapper.JsonMapper#toJavaList(
   * java.lang.String, java.lang.Class)
   */
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
      // Sometimes LinkedIn returns the empty object {} when it really should be
      // returning an empty list [] (example: do an FQL query for a user's
      // affiliations - it's a list except when there are none, then it turns
      // into an object). Check for that special case here.
      if (isEmptyObject(json)) {
        if (LOGGER.isTraceEnabled()) {
          LOGGER
              .trace(
                  "Encountered {} when we should've seen []. Mapping the {} as an empty list and "
                      + "moving on...");
        }

        return new ArrayList<T>();
      }

      // Special case: if the only element of this object is an array called
      // "data", then treat it as a list. The Graph API uses this convention for
      // connections and in a few other places, e.g. comments on the Post
      // object.
      // Doing this simplifies mapping, so we don't have to worry about having a
      // little placeholder object that only has a "data" value.
      try {
        JSONObject jsonObject = new JSONObject(json);
        String[] fieldNames = JSONObject.getNames(jsonObject);

        if (fieldNames != null) {
          boolean hasSingleDataProperty = fieldNames.length == 1 && "data".equals(fieldNames[0]);
          Object jsonDataObject = jsonObject.get("data");

          if (!hasSingleDataProperty && !(jsonDataObject instanceof JSONArray)) {
            if (jsonMappingErrorHandler.handleMappingError(json, type, null)) {
              return null;
            } else {
              throw new LinkedInJsonMappingException(
                  "JSON is an object but is being mapped as a list instead. Offending JSON is '"
                      + json + "'.");
            }
          }

          json = jsonDataObject.toString();
        }
      } catch (JSONException e) {
        // Should never get here, but just in case...
        if (jsonMappingErrorHandler.handleMappingError(json, type, e)) {
          return null;
        } else {
          throw new LinkedInJsonMappingException("Unable to convert LinkedIn response JSON to a "
              + "list of " + type.getName() + " instances.  Offending JSON is " + json,
              e);
        }
      }
    }

    try {
      List<T> list = new ArrayList<>();

      JSONArray jsonArray = new JSONArray(json);
      for (int i = 0; i < jsonArray.length(); i++) {
        String rebuildJson = jsonArray.get(i).toString();
        if (jsonArray.optJSONArray(i) == null && rebuildJson.startsWith("[")) {
          // the inner JSON starts with square brackets but the parser don't think this is a JSON
          // array so we think the parser is right and add quotes around the string
          // solves Issue #719
          list.add(toJavaObject('"' + rebuildJson + '"', type));
        } else {
          list.add(toJavaObject(rebuildJson, type));
        }
      }

      return unmodifiableList(list);
    } catch (LinkedInJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, e)) {
        return null;
      } else {
        throw new LinkedInJsonMappingException(
            "Unable to convert LinkedIn response JSON to a list of " + type.getName()
                + " instances",
            e);
      }
    }
  }

  /**
   * @see com.echobox.api.linkedin.jsonmapper.JsonMapper#toJavaObject(
   * java.lang.String, java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T toJavaObject(String json, Class<T> type) {
    if ("[]".equals(json)) {
      return toJavaObject("{}", type);
    }

    if (StringUtils.isBlank(json)) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, null)) {
        return null;
      } else {
        throw new LinkedInJsonMappingException("JSON is an empty string - can't map it.");
      }
    }

    if (json.startsWith("[")) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, null)) {
        return null;
      } else {
        throw new LinkedInJsonMappingException("JSON is an array but is being mapped as an object "
            + "- you should map it as a List instead. Offending JSON is '" + json + "'.");
      }
    }

    try {
      // Are we asked to map to JsonObject? If so, short-circuit right away.
      if (type.equals(JSONObject.class)) {
        return (T) new JSONObject(json);
      }

      List<FieldWithAnnotation<LinkedIn>> fieldsWithAnnotation =
          ReflectionUtils.findFieldsWithAnnotation(type, LinkedIn.class);
      Set<String> linkedinFieldNamesWithMultipleMappings =
          linkedinFieldNamesWithMultipleMappings(fieldsWithAnnotation);

      // If there are no annotated fields, assume we're mapping to a built-in
      // type. If this is actually the empty object, just return a new instance
      // of the corresponding Java type.
      if (fieldsWithAnnotation.isEmpty()) {
        if (isEmptyObject(json)) {
          T instance = createInstance(type);

          // If there are any methods annotated with @JsonMappingCompleted,
          // invoke them.
          invokeJsonMappingCompletedMethods(instance);

          return instance;
        } else {
          return toPrimitiveJavaType(json, type);
        }
      }

      // LinkedIn will sometimes return the string "null".
      // Check for that and bail early if we find it.
      if ("null".equals(json)) {
        return null;
      }

      // LinkedIn will sometimes return the string "false" to mean null.
      // Check for that and bail early if we find it.
      if ("false".equals(json)) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER
              .debug(format("Encountered 'false' from LinkedIn when trying to map to %s - mapping "
                  + "null instead.", type.getSimpleName()));
        }
        return null;
      }

      JSONObject jsonObject = new JSONObject(json);
      T instance = createInstance(type);

      if (instance instanceof JSONObject) {
        return (T) jsonObject;
      }

      // For each LinkedIn-annotated field on the current Java object, pull data
      // out of the JSON object and put it in the Java object
      for (FieldWithAnnotation<LinkedIn> fieldWithAnnotation : fieldsWithAnnotation) {
        String linkedinFieldName = getLinkedInFieldName(fieldWithAnnotation);

        if (!jsonObject.has(linkedinFieldName)) {
          if (LOGGER.isTraceEnabled()) {
            LOGGER
                .trace(format("No JSON value present for '%s', skipping. JSON is '%s'.",
                    linkedinFieldName, json));
          }

          continue;
        }

        fieldWithAnnotation.getField().setAccessible(true);

        // Set the Java field's value.
        //
        // If we notice that this LinkedIn field name is mapped more than once,
        // go into a special mode where we swallow any exceptions that occur
        // when mapping to the Java field. This is because LinkedIn will
        // sometimes return data in different formats for the same field name.
        // See issues 56 and 90 for examples of this behavior and discussion.
        if (linkedinFieldNamesWithMultipleMappings.contains(linkedinFieldName)) {
          try {
            fieldWithAnnotation.getField().set(instance,
                toJavaType(fieldWithAnnotation, jsonObject, linkedinFieldName));
          } catch (LinkedInJsonMappingException e) {
            logMultipleMappingFailedForField(linkedinFieldName, fieldWithAnnotation, json);
          } catch (JSONException e) {
            logMultipleMappingFailedForField(linkedinFieldName, fieldWithAnnotation, json);
          }
        } else {
          try {
            fieldWithAnnotation.getField().set(instance,
                toJavaType(fieldWithAnnotation, jsonObject, linkedinFieldName));
          } catch (Exception e) {
            if (!jsonMappingErrorHandler.handleMappingError(json, type, e)) {
              throw e;
            }
          }
        }
      }

      // If there are any methods annotated with @JsonMappingCompleted,
      // invoke them.
      invokeJsonMappingCompletedMethods(instance);

      return instance;
    } catch (LinkedInJsonMappingException e) {
      throw e;
    } catch (Exception e) {
      if (jsonMappingErrorHandler.handleMappingError(json, type, e)) {
        return null;
      } else {
        throw new LinkedInJsonMappingException("Unable to map JSON to Java. Offending JSON is '"
            + json + "'.", e);
      }
    }
  }

  /**
   * Finds and invokes methods on {@code object} that are annotated with the
   * {@code @JsonMappingCompleted} annotation.
   * <p>
   * This will even work on {@code private} methods.
   * 
   * @param object
   *          The object on which to invoke the method.
   * @throws IllegalArgumentException
   *           If unable to invoke the method.
   * @throws IllegalAccessException
   *           If unable to invoke the method.
   * @throws InvocationTargetException
   *           If unable to invoke the method.
   */
  protected void invokeJsonMappingCompletedMethods(Object object)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    for (Method method : ReflectionUtils.findMethodsWithAnnotation(object.getClass(),
        JsonMappingCompleted.class)) {
      method.setAccessible(true);

      if (method.getParameterTypes().length == 0) {
        method.invoke(object);        
      } else if (method.getParameterTypes().length == 1
          && JsonMapper.class.equals(method.getParameterTypes()[0])) {
        method.invoke(object, this);        
      } else {
        throw new LinkedInJsonMappingException(
            format("Methods annotated with @%s must take 0 parameters or a single %s parameter."
                + "Your method was %s",
                JsonMappingCompleted.class.getSimpleName(), JsonMapper.class.getSimpleName(),
                method));
      }
    }
  }

  /**
   * Dumps out a log message when one of a multiple-mapped LinkedIn field name JSON-to-Java mapping
   * operation fails.
   * 
   * @param linkedinFieldName
   *          The LinkedIn field name.
   * @param fieldWithAnnotation
   *          The Java field to map to and its annotation.
   * @param json
   *          The JSON that failed to map to the Java field.
   */
  protected void logMultipleMappingFailedForField(String linkedinFieldName,
      FieldWithAnnotation<LinkedIn> fieldWithAnnotation, String json) {
    if (!LOGGER.isTraceEnabled()) {
      return;
    }

    Field field = fieldWithAnnotation.getField();

    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace("Could not map '" + linkedinFieldName + "' to "
          + field.getDeclaringClass().getSimpleName()
          + "." + field.getName() + ", but continuing on because '" + linkedinFieldName
          + "' is mapped to multiple fields in " + field.getDeclaringClass().getSimpleName()
          + ". JSON is " + json);
    }
  }

  /**
   * For a Java field annotated with the {@code LinkedIn} annotation, figure out what the
   * corresponding LinkedIn JSON
   * field name to map to it is.
   * 
   * @param fieldWithAnnotation
   *          A Java field annotated with the {@code LinkedIn} annotation.
   * @return The LinkedIn JSON field name that should be mapped to this Java field.
   */
  protected String getLinkedInFieldName(FieldWithAnnotation<LinkedIn> fieldWithAnnotation) {
    String linkedinFieldName = fieldWithAnnotation.getAnnotation().value();
    Field field = fieldWithAnnotation.getField();

    // If no LinkedIn field name was specified in the annotation, assume
    // it's the same name as the Java field
    if (StringUtils.isBlank(linkedinFieldName)) {
      if (LOGGER.isTraceEnabled()) {
        LOGGER
            .trace(format(
                "No explicit LinkedIn field name found for %s, so defaulting to the field "
                    + "name itself (%s)",
                field, field.getName()));
      }

      linkedinFieldName = field.getName();
    }

    return linkedinFieldName;
  }

  /**
   * Finds any LinkedIn JSON fields that are mapped to more than 1 Java field.
   * 
   * @param fieldsWithAnnotation
   *          Java fields annotated with the {@code LinkedIn} annotation.
   * @return Any LinkedIn JSON fields that are mapped to more than 1 Java field.
   */
  protected Set<String> linkedinFieldNamesWithMultipleMappings(
      List<FieldWithAnnotation<LinkedIn>> fieldsWithAnnotation) {
    Map<String, Integer> linkedinFieldsNamesWithOccurrenceCount = new HashMap<>();
    Set<String> linkedinFieldNamesWithMultipleMappings = new HashSet<>();

    // Get a count of LinkedIn field name occurrences for each
    // @LinkedIn-annotated field
    for (FieldWithAnnotation<LinkedIn> fieldWithAnnotation : fieldsWithAnnotation) {
      String fieldName = getLinkedInFieldName(fieldWithAnnotation);
      int occurrenceCount = linkedinFieldsNamesWithOccurrenceCount.containsKey(fieldName)
          ? linkedinFieldsNamesWithOccurrenceCount.get(fieldName)
          : 0;
      linkedinFieldsNamesWithOccurrenceCount.put(fieldName, occurrenceCount + 1);
    }

    // Pull out only those field names with multiple mappings
    for (Entry<String, Integer> entry : linkedinFieldsNamesWithOccurrenceCount.entrySet()) {
      if (entry.getValue() > 1) {
        linkedinFieldNamesWithMultipleMappings.add(entry.getKey());
      }
    }

    return unmodifiableSet(linkedinFieldNamesWithMultipleMappings);
  }

  /**
   * @see com.echobox.api.linkedin.jsonmapper.JsonMapper#toJson(java.lang.Object)
   */
  @Override
  public String toJson(Object object) {
    // Delegate to recursive method
    return toJsonInternal(object, false).toString();
  }

  /**
   * @see com.echobox.api.linkedin.JsonMapper#toJson(java.lang.Object, boolean)
   */
  @Override
  public String toJson(Object object, boolean ignoreNullValuedProperties) {
    return toJsonInternal(object, ignoreNullValuedProperties).toString();
  }

  /**
   * Recursively marshal the given {@code object} to JSON.
   * <p>
   * Used by {@link #toJson(Object)}.
   * 
   * @param object
   *          The object to marshal.
   * @param ignoreNullValuedProperties
   *          If this is {@code true}, no Javabean properties with {@code null} values will be
   *          included in the generated
   *          JSON.
   * @return JSON representation of the given {@code object}.
   * @throws LinkedInJsonMappingException
   *           If an error occurs while marshaling to JSON.
   */
  protected Object toJsonInternal(Object object, boolean ignoreNullValuedProperties) {
    if (object == null) {
      return JSONObject.NULL;
    }

    if (object instanceof JSONObject) {
      return (JSONObject) object;
    }

    if (object instanceof List<?>) {
      JSONArray jsonArray = new JSONArray();
      for (Object o : (List<?>) object) {
        jsonArray.put(toJsonInternal(o, ignoreNullValuedProperties));        
      }

      return jsonArray;
    }

    if (object instanceof Map<?, ?>) {
      JSONObject jsonObject = new JSONObject();
      for (Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
        if (!(entry.getKey() instanceof String)) {
          throw new LinkedInJsonMappingException("Your Map keys must be of type " + String.class
              + " in order to be converted to JSON.  Offending map is " + object);
        }

        try {
          jsonObject.put((String) entry.getKey(), toJsonInternal(entry.getValue(),
              ignoreNullValuedProperties));
        } catch (JSONException e) {
          throw new LinkedInJsonMappingException(
              "Unable to process value '" + entry.getValue() + "' for key '" + entry.getKey()
                  + "' in Map " + object, e);
        }
      }

      return jsonObject;
    }

    if (ReflectionUtils.isPrimitive(object)) {
      return object;
    }

    if (object instanceof BigInteger) {
      return ((BigInteger) object).longValue();
    }

    if (object instanceof BigDecimal) {
      return ((BigDecimal) object).doubleValue();
    }

    if (object instanceof Enum) {
      return ((Enum) object).name();
    }

    if (object instanceof Date) {
      return DateUtils.toLongFormatFromDate((Date) object);
    }

    // We've passed the special-case bits, so let's try to marshal this as a
    // plain old Javabean...

    List<FieldWithAnnotation<LinkedIn>> fieldsWithAnnotation =
        ReflectionUtils.findFieldsWithAnnotation(object.getClass(), LinkedIn.class);

    JSONObject jsonObject = new JSONObject();

    // No longer throw an exception in this case. If there are multiple fields
    // with the same @LinkedIn value, it's luck of the draw which is picked for
    // JSON marshaling.
    // TODO: A better implementation would query each duplicate-mapped field. If
    // it has is a non-null value and the other duplicate values are null, use
    // the non-null field.
    Set<String> linkedinFieldNamesWithMultipleMappings =
        linkedinFieldNamesWithMultipleMappings(fieldsWithAnnotation);
    if (!linkedinFieldNamesWithMultipleMappings.isEmpty() && LOGGER.isDebugEnabled()) {
      LOGGER
          .debug(format("Unable to convert to JSON because multiple @%s annotations for the same "
              + "name are present: %s",
              LinkedIn.class.getSimpleName(), linkedinFieldNamesWithMultipleMappings));
    }

    for (FieldWithAnnotation<LinkedIn> fieldWithAnnotation : fieldsWithAnnotation) {
      String linkedinFieldName = getLinkedInFieldName(fieldWithAnnotation);
      fieldWithAnnotation.getField().setAccessible(true);

      try {
        Object fieldValue = fieldWithAnnotation.getField().get(object);

        if (!(ignoreNullValuedProperties && (fieldValue == null
            || isEmptyCollectionOrMap(fieldValue)))) {
          jsonObject.put(linkedinFieldName, toJsonInternal(fieldValue, ignoreNullValuedProperties));
        }
      } catch (Exception e) {
        throw new LinkedInJsonMappingException(
            "Unable to process field '" + linkedinFieldName + "' for " + object.getClass(), e);
      }
    }

    return jsonObject;
  }

  private boolean isEmptyCollectionOrMap(Object fieldValue) {
    if (fieldValue instanceof Collection) {
      return ((Collection) fieldValue).isEmpty();
    }

    return (fieldValue instanceof Map && ((Map) fieldValue).isEmpty());
  }

  /**
   * Given a {@code json} value of something like {@code MyValue} or {@code 123} , return a 
   * representation of that value
   * of type {@code type}.
   * <p>
   * This is to support non-legal JSON served up by LinkedIn for API calls like {@code Friends.get}
   * (example result:
   * {@code [222333,1240079]}).
   * 
   * @param <T>
   *          The Java type to map to.
   * @param json
   *          The non-legal JSON to map to the Java type.
   * @param type
   *          Type token.
   * @return Java representation of {@code json}.
   * @throws LinkedInJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  @SuppressWarnings("unchecked")
  protected <T> T toPrimitiveJavaType(String json, Class<T> type) {

    if (json.length() > 1 && json.startsWith("\"") && json.endsWith("\"")) {
      json = json.replaceFirst("\"", "");
      json = json.substring(0, json.length() - 1);
    }

    if (String.class.equals(type)) {
      return (T) json;
    }
    if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
      return (T) Integer.valueOf(json);
    }
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) {
      return (T) Boolean.valueOf(json);
    }
    if (Long.class.equals(type) || Long.TYPE.equals(type)) {
      return (T) Long.valueOf(json);
    }
    if (Double.class.equals(type) || Double.TYPE.equals(type)) {
      return (T) Double.valueOf(json);
    }
    if (Float.class.equals(type) || Float.TYPE.equals(type)) {
      return (T) Float.valueOf(json);
    }
    if (BigInteger.class.equals(type)) {
      return (T) new BigInteger(json);
    }
    if (BigDecimal.class.equals(type)) {
      return (T) new BigDecimal(json);
    }

    if (jsonMappingErrorHandler.handleMappingError(json, type, null)) {
      return null;
    }

    throw new LinkedInJsonMappingException("Don't know how to map JSON to " + type
        + ". Are you sure you're mapping to the right class? " + "Offending JSON is '"
        + json + "'.");
  }

  /**
   * Extracts JSON data for a field according to its {@code LinkedIn} annotation and returns it 
   * converted to the proper
   * Java type.
   * 
   * @param fieldWithAnnotation
   *          The field/annotation pair which specifies what Java type to convert to.
   * @param jsonObject
   *          "Raw" JSON object to pull data from.
   * @param linkedinFieldName
   *          Specifies what JSON field to pull "raw" data from.
   * @return A
   * @throws JsonException
   *           If an error occurs while mapping JSON to Java.
   * @throws LinkedInJsonMappingException
   *           If an error occurs while mapping JSON to Java.
   */
  protected Object toJavaType(FieldWithAnnotation<LinkedIn> fieldWithAnnotation,
      JSONObject jsonObject, String linkedinFieldName) {
    Class<?> type = fieldWithAnnotation.getField().getType();
    Object rawValue = jsonObject.get(linkedinFieldName);

    // Short-circuit right off the bat if we've got a null value.
    if (JSONObject.NULL.equals(rawValue)) {
      return null;
    }

    if (String.class.equals(type)) {
      /*
       * Special handling here for better error checking.
       *
       * Since {@code JsonObject.getString()} will return literal JSON text even if it's _not_ a
       * JSON string, we check the marshaled type and bail if needed. For example, calling {@code
       * JsonObject.getString("results")} on the below JSON...
       *
       * {@code {"results":[{"name":"Mark Allen"}]}}
       *
       * ... would return the string {@code "[{"name":"Mark Allen"}]"} instead of throwing an error.
       * So we throw the error ourselves.
       *
       * Per Antonello Naccarato, sometimes LI will return an empty JSON array instead of an empty
       * string. Look for that here.
       */
      if (rawValue instanceof JSONArray) {
        if (((JSONArray) rawValue).length() == 0) {
          if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(format("Coercing an empty JSON array to an empty string for %s",
                fieldWithAnnotation));
          }

          return "";
        }
      }

      /*
       * If the user wants a string, _always_ give her a string.
       *
       * This is useful if, for example, you've got a @LinkedIn-annotated string field that you'd
       * like to have a numeric type shoved into.
       *
       * User beware: this will turn *anything* into a string, which might lead to results you don't
       * expect.
       */
      return rawValue.toString();
    }

    if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
      return jsonObject.getInt(linkedinFieldName);
    }
    if (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) {
      return jsonObject.getBoolean(linkedinFieldName);
    }
    if (Long.class.equals(type) || Long.TYPE.equals(type)) {
      return jsonObject.getLong(linkedinFieldName);
    }
    if (Double.class.equals(type) || Double.TYPE.equals(type)) {
      return jsonObject.getDouble(linkedinFieldName);
    }
    if (Float.class.equals(type) || Float.TYPE.equals(type)) {
      return new BigDecimal(jsonObject.getString(linkedinFieldName)).floatValue();
    }
    if (BigInteger.class.equals(type)) {
      return new BigInteger(jsonObject.getString(linkedinFieldName));
    }
    if (BigDecimal.class.equals(type)) {
      return new BigDecimal(jsonObject.getString(linkedinFieldName));
    }
    if (List.class.equals(type)) {
      return toJavaList(rawValue.toString(),
          ReflectionUtils.getFirstParameterizedTypeArgument(fieldWithAnnotation.getField()));
    }
    if (Map.class.equals(type)) {
      return convertJsonObjectToMap(rawValue.toString(), fieldWithAnnotation.getField());
    }

    if (type.isEnum()) {
      Class<? extends Enum> enumType = type.asSubclass(Enum.class);
      try {
        return Enum.valueOf(enumType, jsonObject.getString(linkedinFieldName));
      } catch (IllegalArgumentException iae) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug(
              format("Cannot map string %s to enum %s", jsonObject.getString(linkedinFieldName),
                  enumType.getName()));
        }
      }
    }

    String rawValueAsString = rawValue.toString();

    // Some other type - recurse into it
    return toJavaObject(rawValueAsString, type);
  }

  private Map convertJsonObjectToMap(String json, Field field) {
    Class<?> firstParam = ReflectionUtils.getFirstParameterizedTypeArgument(field);
    if (!String.class.equals(firstParam)) {
      throw new LinkedInJsonMappingException("The java type map needs to have a 'String' key, but "
          + "is " + firstParam);
    }

    Class<?> secondParam = ReflectionUtils.getSecondParameterizedTypeArgument(field);

    if (json.startsWith("{")) {
      JSONObject jsonObject = new JSONObject(json);
      Map map = new HashMap();
      Iterator<?> keyIt = jsonObject.keys();
      while (keyIt.hasNext()) {
        String key = (String) keyIt.next();
        String value = jsonObject.getString(key);
        map.put(key, toJavaObject(value, secondParam));
      }
      return map;
    }

    return null;
  }

  /**
   * Creates a new instance of the given {@code type}.
   * <p>
   * 
   * 
   * @param <T>
   *          Java type to map to.
   * @param type
   *          Type token.
   * @return A new instance of {@code type}.
   * @throws LinkedInJsonMappingException
   *           If an error occurs when creating a new instance ({@code type} is inaccessible, 
   *           doesn't have a no-arg constructor, etc.)
   */
  protected <T> T createInstance(Class<T> type) {
    String errorMessage = "Unable to create an instance of " + type
        + ". Please make sure that if it's a nested class, is marked 'static'. "
        + "It should have a no-argument constructor.";

    try {
      Constructor<T> defaultConstructor = type.getDeclaredConstructor();

      if (defaultConstructor == null) {
        throw new LinkedInJsonMappingException("Unable to find a default constructor for " + type);
      }

      // Allows protected, private, and package-private constructors to be
      // invoked
      defaultConstructor.setAccessible(true);
      return defaultConstructor.newInstance();
    } catch (Exception e) {
      throw new LinkedInJsonMappingException(errorMessage, e);
    }
  }

  /**
   * Is the given JSON equivalent to the empty object (<code>{}</code>)?
   * 
   * @param json
   *          The JSON to check.
   * @return {@code true} if the JSON is equivalent to the empty object, {@code false} otherwise.
   */
  protected boolean isEmptyObject(String json) {
    return "{}".equals(json);
  }

  /**
   * Callback interface which allows client code to specify how JSON mapping errors should be 
   * handled.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.2
   */
  public interface JsonMappingErrorHandler {
    /**
     * This method will be called by {@code DefaultJsonMapper} if it encounters an error while 
     * attempting to map JSON to a Java object.
     * <p>
     * You may perform any behavior you'd like here in response to an error, e.g. logging it.
     * <p>
     * If the mapper should continue processing, return {@code true} and {@code null} will be mapped
     * to the target type.
     * If you would like the mapper to stop processing and throw
     * {@link com.echobox.api.linkedin.exception.LinkedInJsonMappingException},
     * return {@code false}.
     * 
     * @param unmappableJson
     *          The JSON that couldn't be mapped to a Java type.
     * @param targetType
     *          The Java type we were attempting to map to.
     * @param ex
     *          The exception that occurred while performing the mapping operation, or {@code null}
     *          if there was no exception.
     * @return {@code true} to continue processing, {@code false} to throw a
     *         {@link com.echobox.api.linkedin.exception.LinkedInJsonMappingException}.
     */
    boolean handleMappingError(String unmappableJson, Class<?> targetType, Exception ex);
  }
}
