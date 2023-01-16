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

package com.echobox.api.linkedin.types.post;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.posts.AdContext;
import com.echobox.api.linkedin.types.posts.Distribution;
import com.echobox.api.linkedin.types.posts.Post;
import com.echobox.api.linkedin.types.urn.URN;
import org.junit.Test;

public class PostTest extends DefaultJsonMapperTestBase {
  
  @Test
  public void testPost() {
    String json = readFileToString("com.echobox.api.linkedin.jsonmapper/post.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    Post post = defaultJsonMapper.toJavaObject(json, Post.class);
    
    assertEquals(Post.LifecycleState.PUBLISHED, post.getLifecycleState());
    assertEquals(1634790968775L, post.getLastModifiedAt().longValue());
    assertEquals(Post.Visibility.PUBLIC, post.getVisibility());
    assertEquals(1634790968774L, post.getPublishedAt().longValue());
    assertEquals(new URN("urn:li:organization:5515715"), post.getAuthor());
    
    assertEquals(Distribution.FeedDistribution.NONE, post.getDistribution().getFeedDistribution());
    assertTrue(post.getDistribution().getThirdPartyDistributionChannels().isEmpty());
    
    assertEquals(new URN("urn:li:video:C5F10AQGKQg_6y2a4sQ"),
        post.getContent().getMedia().getId());
    
    assertFalse(post.getLifecycleStateInfo().isEditedByAuthor());
    assertFalse(post.isReshareDisabledByAuthor());
    assertEquals(1634790968743L, post.getCreatedAt().longValue());
    assertEquals(new URN("urn:li:share:6844785523593134080"), post.getId());
    assertEquals("comment on Oct 20", post.getCommentary());
    
    assertEquals(AdContext.Status.ACTIVE, post.getAdContext().getDscStatus());
    assertEquals(AdContext.AdType.VIDEO, post.getAdContext().getDscAdType());
    assertTrue(post.getAdContext().isDsc());
    assertEquals(new URN("urn:li:sponsoredAccount:520866471"),
        post.getAdContext().getDscAdAccount());
  }
}
