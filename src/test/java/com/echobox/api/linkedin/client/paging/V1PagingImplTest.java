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

package com.echobox.api.linkedin.client.paging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

/**
 * V1 paging implementation test
 * @author Joanna
 *
 */
public class V1PagingImplTest {
  
  /**
   * Test providing a null full endpoint should throw an exception as wit cannot build the endpoints
   */
  @Test(expected = IllegalStateException.class)
  public void testNullFullendpointShouldThrowException() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(new JsonObject(), null);
  }

  /**
   * Test providing a null JSON object will return null next and previous page URLs
   */
  @Test
  public void testEmptyJSONShouldReturnNullNextPrevURLs() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(null, "https://test.com/test");
    assertNull(strategy.getNextPageUrl());
    assertNull(strategy.getPreviousPageUrl());
  }
  
  /**
   * Test providing a count + start >= total should return a null next page URL
   * If the start = 0 the previous page URL should also be null
   */
  @Test
  public void testNextAndPrevURLsAreBothNull() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(
        Json.parse("{\"_count\":5,\"_start\":0,\"_total\":5,\"values\":[1,2,3,4,5]}").asObject(),
        "https://test.com/test");
    assertNull(strategy.getNextPageUrl());
    assertNull(strategy.getPreviousPageUrl());
  }
  
  /**
   * If the start = 0, there should be no previous page URL available
   */
  @Test
  public void testNextPageURLIsAvailableButPrevURLIsNull() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(
        Json.parse("{\"_count\":5,\"_start\":0,\"_total\":10,\"values\":[1,2,3,4,5]}").asObject(),
        "https://test.com/test");
    assertEquals("https://test.com/test?start=5&count=5", strategy.getNextPageUrl());
    assertNull(strategy.getPreviousPageUrl());
  }
  
  /**
   * If the count + start < total, there should be a next page URL available with no overlap
   * If the start != 0, there should be a previous page URL with no overlap
   */
  @Test
  public void testNextAndPrevURLsAvailable() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(
        Json.parse("{\"_count\":5,\"_start\":5,\"_total\":20,\"values\":[1,2,3,4,5]}").asObject(),
        "https://test.com/test");
    assertEquals("https://test.com/test?start=10&count=5", strategy.getNextPageUrl());
    assertEquals("https://test.com/test?start=0&count=5", strategy.getPreviousPageUrl());
  }
  
  /**
   * If the count + start >= total, there next page URL should be null
   * If the start != 0, there should be a previous page URL with no overlap
   */
  @Test
  public void testNextURLIsNullAndPrevURLsAvailable() {
    PagingStrategy strategy = new V1PagingImpl();
    strategy.populatePages(
        Json.parse("{\"_count\":5,\"_start\":15,\"_total\":20,\"values\":[1,2,3,4,5]}").asObject(),
        "https://test.com/test");
    assertNull(strategy.getNextPageUrl());
    assertEquals("https://test.com/test?start=10&count=5", strategy.getPreviousPageUrl());
  }
  
}
