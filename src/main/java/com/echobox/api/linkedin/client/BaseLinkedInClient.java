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

import com.echobox.api.linkedin.exception.ResponseErrorJsonParsingException;
import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.util.URLUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class that contains data and functionality common to LinkedIn clients
 * 
 * @author Joanna
 *
 */
abstract class BaseLinkedInClient {

  /**
   * Handles REST request methods to the LinkedIn API endpoint.
   */
  protected WebRequestor webRequestor;

  /**
   * Handles mapping LinkedIn response JSON to Java objects.
   */
  protected JsonMapper jsonMapper;

  /**
   * Set of parameter names that user must not specify themselves, since we use these parameters
   * internally.
   */
  protected final Set<String> illegalParamNames = new HashSet<>();

  /**
  * If the error object is not in the correct format, throw a ResponseErrorJsonParsingException
  * @param json error JSON
   * @throws ResponseErrorJsonParsingException ResponseErrorJsonParsingException
  */
  protected void skipResponseStatusExceptionParsing(String json)
      throws ResponseErrorJsonParsingException {
    // If this is not an object, it's not an error response.
    if (!json.startsWith("{")) {
      throw new ResponseErrorJsonParsingException();
    }
  }

  /**
   * Appends the given {@code parameter} to the given {@code parameters} array.
   * 
   * @param parameter
   *          The parameter value to append.
   * @param parameters
   *          The parameters to which the given {@code parameter} is appended.
   * @return A new array which contains both {@code parameter} and {@code parameters}.
   */
  protected Parameter[] parametersWithAdditionalParameter(Parameter parameter,
      Parameter... parameters) {
    Parameter[] updatedParameters = new Parameter[parameters.length + 1];
    System.arraycopy(parameters, 0, updatedParameters, 0, parameters.length);
    updatedParameters[parameters.length] = parameter;
    return updatedParameters;
  }

  /**
   * Gets the URL-encoded version of the given {@code value} for the parameter named {@code name}.
   * <p>
   * Parameter types should always be URL-encoded.
   * 
   * @param value
   *          The value of the parameter which should be URL-encoded and returned.
   * @return The URL-encoded version of the given {@code value}.
   */
  protected String urlEncodedValueForParameterName(String value) {
    // URL-encode as normal.
    return URLUtils.urlEncode(value);
  }

  /**
   * Given an api call, returns the correct LinkedIn API endpoint to use.
   * <p>
   * 
   * @param apiCall
   *          The LI API call for which we'd like an endpoint.
   * @param hasAttachment
   *          Are we including a multipart file when making this API call?
   * @return An absolute endpoint URL to communicate with.
   */
  protected abstract String createEndpointForApiCall(String apiCall, boolean hasAttachment);

  /**
   * Verifies that the provided parameter names don't collide with the ones we internally pass along
   * to LinkedIn.
   * 
   * @param parameters
   *          The parameters to check.
   * @throws IllegalArgumentException
   *           If there's a parameter name collision.
   */
  protected void verifyParameterLegality(Parameter... parameters) {
    for (Parameter parameter : parameters) {
      if (illegalParamNames.contains(parameter.name)) {
        throw new IllegalArgumentException(
            "Parameter '" + parameter.name + "' is reserved for ebx-linkedin-sdk use - "
                + "you cannot specify it yourself.");
      }
    }
  }

}
