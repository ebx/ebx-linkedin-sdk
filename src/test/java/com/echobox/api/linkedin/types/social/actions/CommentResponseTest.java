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

package com.echobox.api.linkedin.types.social.actions;

import static org.junit.Assert.assertEquals;

import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapper;
import com.echobox.api.linkedin.jsonmapper.DefaultJsonMapperTestBase;
import com.echobox.api.linkedin.types.objectype.AuditStamp;
import org.junit.Test;

public class CommentResponseTest extends DefaultJsonMapperTestBase {
  
  @Test
  public void testCommentResponse() {
    String json = readFileToString(
        "com.echobox.api.linkedin.jsonmapper/commentResponse.json");
    DefaultJsonMapper defaultJsonMapper = new DefaultJsonMapper();
    CommentResponse response = defaultJsonMapper.toJavaObject(json, CommentResponse.class);
    
    final String actorURN = "urn:li:organization:5637409";
    
    assertEquals(actorURN, response.getActor().toString());
    CommentAction.CommentMessage commentMessage = response.getMessage();
    assertEquals("commentV2 with image urn", commentMessage.getText());
  
    AuditStamp created = response.getCreated();
    assertEquals(actorURN, created.getActor());
    assertEquals(1583863835990L, created.getTime().longValue());
  
    AuditStamp lastModified = response.getLastModified();
    assertEquals(actorURN, lastModified.getActor());
    assertEquals(1583863835991L, lastModified.getTime().longValue());
  }
}
