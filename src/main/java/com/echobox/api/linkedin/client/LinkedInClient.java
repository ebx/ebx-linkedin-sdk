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
package com.echobox.api.linkedin.client;

import static java.lang.String.format;

import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.scope.ScopeBuilder;
import com.echobox.api.linkedin.util.ReflectionUtils;
import com.echobox.api.linkedin.util.URLUtils;
import com.echobox.api.linkedin.version.Version;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Specifies how a <a href="http://developers.facebook.com/docs/api">Facebook Graph API</a> client must operate.
 * <p>
 * Projects that need to access the <a href="http://developers.facebook.com/docs/reference/rest/">old REST API</a>
 * should use {@link LegacyFacebookClient} instead. You might choose to do this because you have a legacy codebase or
 * you need functionality that is not yet available in the Graph API.
 * <p>
 * If you'd like to...
 * 
 * <ul>
 * <li>Fetch an object: use {@link #fetchObject(String, Class, Parameter...)} or
 * {@link #fetchObjects(List, Class, Parameter...)}</li>
 * <li>Fetch a connection: use {@link #fetchConnection(String, Class, Parameter...)}</li>
 * <li>Execute an FQL query: use {@link #executeFqlQuery(String, Class, Parameter...)} or
 * {@link #executeFqlMultiquery(Map, Class, Parameter...)}</li>
 * <li>Execute operations in batch: use {@link #executeBatch(BatchRequest...)} or {@link #executeBatch(List, List)}</li>
 * <li>Publish data: use {@link #publish(String, Class, Parameter...)} or
 * {@link #publish(String, Class, BinaryAttachment, Parameter...)}</li>
 * <li>Delete an object: use {@link #deleteObject(String, Parameter...)}</li>
 * </ul>
 * 
 * <p>
 * You may also perform some common access token operations. If you'd like to...
 * 
 * <ul>
 * <li>Extend the life of an access token: use {@link #obtainExtendedAccessToken(String, String, String)}</li>
 * <li>Obtain an access token for use on behalf of an application instead of a user, use
 * {@link #obtainAppAccessToken(String, String)}.</li>
 * <li>Convert old-style session keys to OAuth access tokens: use
 * {@link #convertSessionKeysToAccessTokens(String, String, String...)}</li>
 * <li>Verify and extract data from a signed request: use {@link #parseSignedRequest(String, String, Class)}</li>
 * </ul>
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @author Scott Hernandez
 * @author Mattia Tommasone
 * @author <a href="http://ex-nerd.com">Chris Petersen</a>
 * @author Josef Gierbl
 * @author Broc Seib
 */
public interface LinkedInClient {
  
  Version getVersion();
  
  /**
   * Fetches a single <a href="http://developers.facebook.com/docs/reference/api/">Graph API object</a>, mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param object
   *          ID of the object to fetch, e.g. {@code "me"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested object's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObject(String object, Class<T> objectType, Parameter... parameters);

  /**
   * Fetches multiple <a href="http://developers.facebook.com/docs/reference/api/">Graph API objects</a> in a single
   * call, mapping the results to an instance of {@code objectType}.
   * <p>
   * You'll need to write your own container type ({@code objectType}) to hold the results. See
   * <a href="http://restfb.com">http://restfb.com</a> for an example of how to do this.
   * 
   * @param <T>
   *          Java type to map to.
   * @param ids
   *          IDs of the objects to fetch, e.g. {@code "me", "arjun"}.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code objectType} which contains the requested objects' data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T fetchObjects(List<String> ids, Class<T> objectType, Parameter... parameters);

  /**
   * Fetches a Graph API {@code Connection} type, mapping the result to an instance of {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The name of the connection, e.g. {@code "me/feed"}.
   * @param connectionType
   *          Connection type token.
   * @param parameters
   *          URL parameters to include in the API call (optional).
   * @return An instance of type {@code connectionType} which contains the requested Connection's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> Connection<T> fetchConnection(String connection, Class<T> connectionType, Parameter... parameters);

  /**
   * Fetches a previous/next page of a Graph API {@code Connection} type, mapping the result to an instance of
   * {@code connectionType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connectionPageUrl
   *          The URL of the connection page to fetch, usually retrieved via {@link Connection#getPreviousPageUrl()} or
   *          {@link Connection#getNextPageUrl()}.
   * @param connectionType
   *          Connection type token.
   * @return An instance of type {@code connectionType} which contains the requested Connection's data.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> Connection<T> fetchConnectionPage(String connectionPageUrl, Class<T> connectionType);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection}, mapping the result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection} and includes some files - photos, for example - in the publish request, and mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param binaryAttachments
   *          The files to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, List<BinaryAttachment> binaryAttachments,
      Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#publishing">Graph API publish</a> operation on the
   * given {@code connection} and includes a file - a photo, for example - in the publish request, and mapping the
   * result to an instance of {@code objectType}.
   * 
   * @param <T>
   *          Java type to map to.
   * @param connection
   *          The Connection to publish to.
   * @param objectType
   *          Object type token.
   * @param binaryAttachment
   *          The file to include in the publish request.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return An instance of type {@code objectType} which contains the Facebook response to your publish request.
   * @throws FacebookException
   *           If an error occurs while performing the API call.
   */
  <T> T publish(String connection, Class<T> objectType, BinaryAttachment binaryAttachment, Parameter... parameters);

  /**
   * Performs a <a href="http://developers.facebook.com/docs/api#deleting">Graph API delete</a> operation on the given
   * {@code object}.
   * 
   * @param object
   *          The ID of the object to delete.
   * @param parameters
   *          URL parameters to include in the API call.
   * @return {@code true} if Facebook indicated that the object was successfully deleted, {@code false} otherwise.
   * @throws FacebookException
   *           If an error occurred while attempting to delete the object.
   */
  boolean deleteObject(String object, Parameter... parameters);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of a user.
   * <p>
   * See <a href="https://developers.facebook.com/docs/facebook-login/access-tokens">Access Tokens</a>.
   *
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @param redirectUri
   *          The redirect URI which was used to obtain the {@code verificationCode}.
   * @param verificationCode
   *          The verification code in the Graph API callback to the redirect URI.
   * @return The access token for the user identified by {@code appId}, {@code appSecret}, {@code redirectUri} and
   *         {@code verificationCode}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an access token.
   * @since 1.8.0
   */
  AccessToken obtainUserAccessToken(String appId, String appSecret, String redirectUri, String verificationCode);

  /**
   * Obtains an access token which can be used to perform Graph API operations on behalf of an application instead of a
   * user.
   * <p>
   * See <a href="https://developers.facebook.com/docs/authentication/applications/" >Facebook's authenticating as an
   * app documentation</a>.
   * 
   * @param appId
   *          The ID of the app for which you'd like to obtain an access token.
   * @param appSecret
   *          The secret for the app for which you'd like to obtain an access token.
   * @return The access token for the application identified by {@code appId} and {@code appSecret}.
   * @throws FacebookException
   *           If an error occurs while attempting to obtain an access token.
   * @since 1.6.10
   */
  AccessToken obtainAppAccessToken(String appId, String appSecret);

  /**
   * Gets the {@code JsonMapper} used to convert Facebook JSON to Java objects.
   * 
   * @return The {@code JsonMapper} used to convert Facebook JSON to Java objects.
   * @since 1.6.7
   */
  JsonMapper getJsonMapper();

  /**
   * Gets the {@code WebRequestor} used to talk to the Facebook API endpoints.
   * 
   * @return The {@code WebRequestor} used to talk to the Facebook API endpoints.
   * @since 1.6.7
   */
  WebRequestor getWebRequestor();

  /**
   * generates the login dialog url
   * 
   * @param appId
   *          The ID of your app, found in your app's dashboard.
   * @param redirectUri
   *          The URL that you want to redirect the person logging in back to. This URL will capture the response from
   *          the Login Dialog. If you are using this in a webview within a desktop app, this must be set to
   *          <code>https://www.facebook.com/connect/login_success.html</code>.
   * @param scope
   *          List of Permissions to request from the person using your app.
   * @param additionalParameters
   *          List of additional parameters
   * @since 1.9.0
   * @return
   */
  String getLoginDialogUrl(String appId, String redirectUri, ScopeBuilder scope, Parameter... additionalParameters);

  /**
   * Represents an access token/expiration date pair.
   * <p>
   * Facebook returns these types when performing access token-related operations - see
   * {@link com.restfb.FacebookClient#convertSessionKeysToAccessTokens(String, String, String...)},
   * {@link com.restfb.FacebookClient#obtainAppAccessToken(String, String)}, and
   * {@link com.restfb.FacebookClient#obtainExtendedAccessToken(String, String, String)} for details.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  class AccessToken {
    @LinkedIn("access_token")
    private String accessToken;

    @LinkedIn("expires_in")
    private Long rawExpires;

    private Long expires;

    @LinkedIn("token_type")
    private String tokenType;

    /**
     * Given a query string of the form {@code access_token=XXX} or {@code access_token=XXX&expires=YYY}, return an
     * {@code AccessToken} instance.
     * <p>
     * The {@code queryString} is required to contain an {@code access_token} parameter with a non-{@code null} value.
     * The {@code expires} value is optional and should be the number of seconds since the epoch. If the {@code expires}
     * value cannot be parsed, the returned {@code AccessToken} will have a {@code null} {@code expires} value.
     * 
     * @param queryString
     *          The Facebook query string out of which to parse an {@code AccessToken} instance.
     * @return An {@code AccessToken} instance which corresponds to the given {@code queryString}.
     * @throws IllegalArgumentException
     *           If no {@code access_token} parameter is present in the query string.
     * @since 1.6.10
     */
    public static AccessToken fromQueryString(String queryString) {
      // Query string can be of the form 'access_token=XXX' or
      // 'access_token=XXX&expires=YYY'
      Map<String, List<String>> urlParameters = URLUtils.extractParametersFromQueryString(queryString);

      String extendedAccessToken = null;
      String tokenType = null;

      if (urlParameters.containsKey("access_token")) {
        extendedAccessToken = urlParameters.get("access_token").get(0);
      }

      if (urlParameters.containsKey("token_type")) {
        tokenType = urlParameters.get("token_type").get(0);
      }

      if (extendedAccessToken == null) {
        throw new IllegalArgumentException(format(
          "Was expecting a query string of the form 'access_token=XXX' or 'access_token=XXX&expires=YYY'. Instead, the query string was '%s'",
          queryString));
      }

      Long expires = null;

      // If an expires or expires_in value was provided and it's a valid long, great - use it.
      // Otherwise ignore it.
      String rawExpires = null;

      if (urlParameters.containsKey("expires")) {
        rawExpires = urlParameters.get("expires").get(0);
      }

      if (urlParameters.containsKey("expires_in")) {
        rawExpires = urlParameters.get("expires_in").get(0);
      }

      if (rawExpires != null) {
        try {
          expires = Long.valueOf(rawExpires);
        } catch (NumberFormatException e) {}
        if (expires != null) {
          expires = new Date().getTime() + 1000L * expires;
        }
      }

      AccessToken accessToken = new AccessToken();
      accessToken.accessToken = extendedAccessToken;
      accessToken.expires = expires;
      accessToken.tokenType = tokenType;
      return accessToken;
    }

    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The access token's value.
     * 
     * @return The access token's value.
     */
    public String getAccessToken() {
      return accessToken;
    }

    /**
     * The date on which the access token expires.
     * 
     * @return The date on which the access token expires.
     */
    public Date getExpires() {
      return expires == null ? null : new Date(expires);
    }

    /**
     * The token type of this access token provided by Facebook
     * 
     * @return the access token type
     */
    public String getTokenType() {
      return tokenType;
    }

    @JsonMapper.JsonMappingCompleted
    void convertExpires() {
      if (rawExpires != null) {
        expires = new Date().getTime() + 1000L * rawExpires;
      }
    }

  }

  /**
   * <p>
   * Represents the result of a {@link FacebookClient#debugToken(String)} inquiry.
   * </p>
   * 
   * FIXME does this class belong here?
   * 
   * <p>
   * See <a href="https://developers.facebook.com/docs/howtos/login/debugging-access-tokens/">Debug access tokens</a>
   * 
   * @author Broc Seib
   */
  class DebugTokenInfo {
    // TODO: Does LinkedIn provide debug token info?
  }

  class DebugTokenError {

    // TODO: Does LinkedIn provide debug tokens?

  }
}
