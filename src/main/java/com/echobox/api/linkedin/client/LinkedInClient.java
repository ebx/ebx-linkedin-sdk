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

import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.jsonmapper.JsonMapper.JsonMappingCompleted;
import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.version.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Specifies how a LinkedIn API client must operate.
 * @author Joanna
 *
 */
public interface LinkedInClient {
  
  /**
   * Get the current LinkedIn version
   * @return the current LinkedIn version
   */
  Version getVersion();
  
  /**
   * Fetches a single LinkedIn API object, mapping the result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param object
   *          ID of the object to fetch
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested object's data.
   */
  <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters);

  /**
   * Fetches a LinkedIn API {@code Connection} type, mapping the result to an instance of
   * {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The name of the connection, e.g. {@code "shares"}.
   * @param connectionType
   *          Connection type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code connectionType} which contains the requested Connection's
   *          data.
   */
  <T> Connection<T> fetchConnection(String connection, Class<T> connectionType,
      Parameter... parameters);

  /**
   * Fetches a previous/next page of a Graph API {@code Connection} type, mapping the result to an
   * instance of {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connectionPageUrl
   *          The URL of the connection page to fetch, usually retrieved via
   *          {@link Connection#getPreviousPageUrl()} or {@link Connection#getNextPageUrl()}.
   * @param connectionType
   *          Connection type token.
   * @return An instance of type {@code connectionType} which contains the requested Connection's
   *          data.
   */
  <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType);

  /**
   * Performs a LinkedIn API publish operation on the given {@code connection}, mapping the result
   * to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param jsonBody
   *          The json body to publish.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the LinkedIn response to your
   *          publish request.
   */
  <T> T publish(String connection, Class<T> objectType, Object jsonBody, Parameter... parameters);

  /**
   * Performs a LinkedIn API publish operation on the given {@code connection} and includes some
   * files - photos, for example - in the publish request, and mapping the result to an instance of
   * {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param jsonBody
   *          The json body to publish.
   * @param binaryAttachments
   *          The files to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the LinkedIn response to your
   *          publish request.
   */
  <T> T publish(String connection, Class<T> objectType, Object jsonBody,
      List<BinaryAttachment> binaryAttachments, Parameter... parameters);

  /**
   * Performs a LinkedIn API publish operation on the given {@code connection} and includes a 
   * file - a photo, for example - in the publish request, and mapping the result to an instance of
   * {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param jsonBody
   *          The json body to publish.
   * @param binaryAttachment
   *          The file to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the LinkedIn response to your
   *          publish request.
   */
  <T> T publish(String connection, Class<T> objectType, Object jsonBody, BinaryAttachment binaryAttachment,
      Parameter... parameters);

  /**
   * Performs a LinkedIn API delete operation on the given {@code object}.
   * 
   * @param object
   *          The ID of the object to delete.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return {@code true} if LinkedIn indicated that the object was successfully deleted,
   *          {@code false} otherwise.
   */
  boolean deleteObject(String object, Parameter... parameters);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of a user.
   * <p>
   * See <a href="https://developer.linkedin.com/docs/oauth2">Access Tokens</a>.
   *
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @param redirectUri
   *          The redirect URI which was used to obtain the {@code verificationCode}.
   * @param verificationCode
   *          The verification code in the Graph API callback to the redirect URI.
   * @return The access token for the user identified by {@code appId}, {@code appSecret},
   *          {@code redirectUri} and {@code verificationCode}.
   */
  AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri,
      String verificationCode);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of an
   * application instead of a user.
   * See <a href="https://developer.linkedin.com/docs/oauth2">LinkedIn's authenticating as an
   * app documentation</a>.
   * 
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @return The access token for the application identified by {@code appId} and {@code appSecret}.
   */
  AccessToken obtainAppAccessToken(String appId, String appSecret);

  /**
   * Gets the {@code JsonMapper} used to convert LinkedIn JSON to Java objects.
   * 
   * @return The {@code JsonMapper} used to convert LinkedIn JSON to Java objects.
   */
  JsonMapper getJsonMapper();

  /**
   * Gets the {@code WebRequestor} used to talk to the LinkedIn API endpoints.
   * 
   * @return The {@code WebRequestor} used to talk to the LinkedIn API endpoints.
   */
  WebRequestor getWebRequestor();

  /**
   * Represents an access token/expiration date pair.
   * <p>
   * LinkedIn returns these types when performing access token-related operations
   * 
   * @author Joanna
   */
  @EqualsAndHashCode
  @ToString
  class AccessToken {

    @Getter
    @LinkedIn("access_token")
    private String accessToken;

    @LinkedIn("expires_in")
    private Long rawExpires;

    @Getter
    private Date expires;

    /**
     * Given a query string of the form {@code access_token=XXX} or
     * {@code access_token=XXX&expires=YYY}, return an {@code AccessToken} instance.
     * <p>
     * The {@code queryString} is required to contain an {@code access_token} parameter with a 
     * non-{@code null} value.
     * The {@code expires} value is optional and should be the number of seconds since the epoch.
     * If the {@code expires} value cannot be parsed, the returned {@code AccessToken} will have a
     * {@code null} {@code expires} value.
     * 
     * @param queryString
     *          The LinkedIn query string out of which to parse an {@code AccessToken} instance.
     * @return An {@code AccessToken} instance which corresponds to the given {@code queryString}.
     * @throws IllegalArgumentException
     *           If no {@code access_token} parameter is present in the query string.
     */
    public static AccessToken fromQueryString(String queryString) {
      // Query string can be of the form 'access_token=XXX' or 'access_token=XXX&expires=YYY'
      Map<String, List<String>> urlParameters =
          URLUtils.extractParametersFromQueryString(queryString);

      String extendedAccessToken = null;

      if (urlParameters.containsKey("access_token")) {
        extendedAccessToken = urlParameters.get("access_token").get(0);
      }

      if (extendedAccessToken == null) {
        throw new IllegalArgumentException(format(
          "Was expecting a query string of the form 'access_token=XXX' or "
          + "'access_token=XXX&expires=YYY'. Instead, the query string was '%s'", queryString));
      }

      Long expires = null;

      // If an expires or expires_in value was provided and it's a valid long, great - use it.
      // Otherwise ignore it.
      String rawExpires = null;

      if (urlParameters.containsKey("expires_in")) {
        rawExpires = urlParameters.get("expires_in").get(0);
      }

      if (rawExpires != null) {
        try {
          expires = Long.valueOf(rawExpires);
        } catch (NumberFormatException e) {
          // No-op
        }
        if (expires != null) {
          expires = new Date().getTime() + 1000L * expires;
        }
      }

      AccessToken accessToken = new AccessToken();
      accessToken.accessToken = extendedAccessToken;
      accessToken.expires = new Date(expires);
      return accessToken;
    }

    @JsonMappingCompleted
    void convertExpires() {
      if (rawExpires != null) {
        expires = new Date(new Date().getTime() + 1000L * rawExpires);
      }
    }

  }
}
