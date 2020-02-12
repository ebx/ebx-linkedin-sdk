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

package com.echobox.api.linkedin.client;

import com.echobox.api.linkedin.exception.LinkedInOAuthException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.GeneralSecurityException;

/**
 * DefaultLinkedInClient test
 * @author Joanna
 */
public class DefaultLinkedInClientTest {
  
  /**
   * Test 200 response status does not throw a LinkedInOauthException
   * @throws GeneralSecurityException GeneralSecurityException
   * @throws IOException IOException
   */
  @Test
  public void test200Response()
      throws GeneralSecurityException, IOException {
    DefaultLinkedInClient client = new DefaultLinkedInClient("test");
    WebRequestor.Response response =
        client.makeRequestAndProcessResponse(() -> new WebRequestor.Response(200, null, null));
  }
  
  /**
   * Test 202 response status does not throw a LinkedInOauthException
   * @throws GeneralSecurityException GeneralSecurityException
   * @throws IOException IOException
   */
  @Test
  public void test201Response()
      throws GeneralSecurityException, IOException {
    DefaultLinkedInClient client = new DefaultLinkedInClient("test");
    WebRequestor.Response response =
        client.makeRequestAndProcessResponse(() -> new WebRequestor.Response(201, null, null));
    Assert.assertEquals(HttpURLConnection.HTTP_CREATED, response.getStatusCode().intValue());
  }
  
  /**
   * Test 204 response status does not throw a LinkedInOauthException
   * @throws GeneralSecurityException GeneralSecurityException
   * @throws IOException IOException
   */
  @Test
  public void test204Response()
      throws GeneralSecurityException, IOException {
    DefaultLinkedInClient client = new DefaultLinkedInClient("test");
    WebRequestor.Response response =
        client.makeRequestAndProcessResponse(() -> new WebRequestor.Response(204, null, null));
    Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, response.getStatusCode().intValue());
  }
  
  /**
   * Test LinkedIn 401 error throws a LinkedInOauthException
   * @throws GeneralSecurityException GeneralSecurityException
   * @throws IOException IOException
   */
  @Test(expected = LinkedInOAuthException.class)
  public void test401ResponseError()
      throws GeneralSecurityException, IOException {
    try {
      DefaultLinkedInClient client = new DefaultLinkedInClient("test");
      client.makeRequestAndProcessResponse(() -> new WebRequestor.Response(401, null, "{"
          + "\"message\": \"Empty oauth2_access_token\",\"serviceErrorCode\": 401,"
          + "\"status\": 401}"));
    } catch (LinkedInOAuthException ex) {
      Assert.assertEquals("Empty oauth2_access_token", ex.getErrorMessage());
      Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, ex.getHttpStatusCode().intValue());
      throw ex;
    }
  }
  
  /**
   * Test 401 error with unparsable JSON body throws a LinkedInOauthException
   * @throws GeneralSecurityException GeneralSecurityException
   * @throws IOException IOException
   */
  @Test(expected = LinkedInOAuthException.class)
  public void test401ResponseErrorNoJSON()
      throws GeneralSecurityException, IOException {
    try {
      DefaultLinkedInClient client = new DefaultLinkedInClient("test");
      client.makeRequestAndProcessResponse(() -> new WebRequestor.Response(401, null, null));
    } catch (LinkedInOAuthException ex) {
      Assert.assertEquals("LinkedIn request failed", ex.getErrorMessage());
      Assert.assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, ex.getHttpStatusCode().intValue());
      throw ex;
    }
  }
  
}
