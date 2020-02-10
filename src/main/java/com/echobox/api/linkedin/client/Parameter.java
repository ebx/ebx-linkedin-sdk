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
//Source - https://restfb.com/

package com.echobox.api.linkedin.client;

import static java.lang.String.format;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Representation of a LinkedIn API request parameter.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public final class Parameter {
  /**
   * Parameter name.
   */
  public final String name;

  /**
   * Parameter value.
   */
  public final String value;

  /**
   * Creates a new parameter with the given {@code name} and {@code value}.
   * 
   * @param name
   *          The parameter name.
   * @param value
   *          The parameter value.
   * @param jsonMapper
   *          Mapper for converting the parameter value to JSON.
   * @throws IllegalArgumentException
   *           If {@code name} is {@code null} or a blank string or either {@code value} 
   *           {@code jsonMapper} is {@code null}.
   */
  private Parameter(String name, Object value, JsonMapper jsonMapper) {
    if (StringUtils.isBlank(name) || value == null) {
      throw new IllegalArgumentException(Parameter.class
          + " instances must have a non-blank name and non-null value.");
    }

    if (jsonMapper == null) {
      throw new IllegalArgumentException("Provided " + JsonMapper.class + " must not be null.");
    }

    this.name = StringUtils.trimToEmpty(name);

    // Special handling for Date types - turn them into LinkedIn date strings.
    // Otherwise, use the JSON value of the type.
    this.value = value instanceof Date ? DateUtils.toLongFormatFromDate((Date) value)
        : jsonMapper.toJson(value, true);
  }

  /**
   * Factory method which provides an instance with the given {@code name} and {@code value}.
   * <p>
   * The {@code value} parameter is often a {@link String} or primitive type like {@link Integer}, 
   * but you may pass in a {@link java.util.List}, {@link java.util.Map}, or your own 
   * {@link LinkedIn} annotated Javabean and it will be converted to JSON automatically.
   * 
   * @param name
   *          The parameter name.
   * @param value
   *          The parameter value.
   * @return A {@code Parameter} instance with the given {@code name} and {@code value}.
   * @throws IllegalArgumentException
   *           If {@code name} or {@code value} is {@code null} or a blank string.
   */
  public static Parameter with(String name, Object value) {
    return Parameter.with(name, value, new DefaultJsonMapper());
  }

  /**
   * Factory method which provides an instance with the given {@code name} and {@code value}, 
   * using the provided {@code jsonMapper} to turn {@code value} into a JSON string.
   * <p>
   * The {@code value} parameter is often a {@link String} or primitive type like {@link Integer},
   * but you may pass in a {@link java.util.List}, {@link java.util.Map}, or your own
   * {@link LinkedIn} annotated Javabean and it will be converted to JSON automatically.
   * 
   * @param name
   *          The parameter name.
   * @param value
   *          The parameter value.
   * @param jsonMapper
   *          The jsonMapper
   * @return A {@code Parameter} instance with the given {@code name} and {@code value}.
   * @throws IllegalArgumentException
   *           If {@code name} or {@code value} is {@code null} or a blank string.
   */
  public static Parameter with(String name, Object value, JsonMapper jsonMapper) {
    return new Parameter(name, value, jsonMapper);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!getClass().equals(obj.getClass())) {
      return false;
    }

    Parameter other = (Parameter) obj;

    if (this.name != other.name && !this.name.equals(other.name)) {
      return false;
    }
    if (this.value != other.value && !this.value.equals(other.value)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + this.name.hashCode();
    hash = 41 * hash + this.value.hashCode();
    return hash;
  }

  @Override
  public String toString() {
    return format("Parameter[%s=%s]", name, value);
  }
}
