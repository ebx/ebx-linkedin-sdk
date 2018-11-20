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

package com.echobox.api.linkedin.jsonmapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.echobox.api.linkedin.types.AuditStamp;
import com.echobox.api.linkedin.types.ContentEntity;
import com.echobox.api.linkedin.types.DistributionTarget;
import com.echobox.api.linkedin.types.Share;
import com.echobox.api.linkedin.types.ShareContent;
import com.echobox.api.linkedin.types.Thumbnail;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Share serialisation test
 * @author joanna
 *
 */
public class ShareTest {
  
  /**
   * Valid Share JSON data file
   */
  private static final String SHARE_JSON = "com.echobox.api.linkedin.jsonmapper/Share.json";
  
  /**
   * Test Share deserialisation form JSON to Java object
   * TODO: URNs...
   */
  @Test
  public void testShareDeserialisesToJavaObject() {
    String shareTypeJSON = readFileToString(SHARE_JSON);
    DefaultJsonMapper mapper = new DefaultJsonMapper();
    Share share = mapper.toJavaObject(shareTypeJSON, Share.class);
    assertEquals("urn:li:activity:12345657", share.getActivity());
    assertNull(share.getAgent());
    assertNotNull(share.getContent());
    assertNotNull(share.getCreated());
    assertNotNull(share.getDistribution());
    assertNull(share.getEdited());
    assertNotNull(share.getLastModified());
    assertNull(share.getOriginalShare());
    assertEquals("urn:li:organization:1000", share.getOwner());
    assertNull(share.getResharedShare());
    assertNull(share.getSubject());
    assertNull(share.getServiceProvider());
    assertNotNull(share.getText());
    
    ShareContent content = share.getContent();
    assertEquals(1, content.getContentEntities().size());
    assertEquals("content description", content.getDescription());
    assertEquals("Test Share with Content", content.getTitle());
    assertNull(content.getShareMediaCategory());
    ContentEntity contentEntity = content.getContentEntities().get(0);
    assertEquals("urn:li:article:0", contentEntity.getEntity());
    assertEquals("https://www.example.com/content.html", contentEntity.getEntityLocation());
    assertNull(contentEntity.getShareMediaCategory());
    assertEquals(1, contentEntity.getThumbnails().size());
    Thumbnail thumbnail = contentEntity.getThumbnails().get(0);
    assertEquals("https://www.example.com/image.jpg", thumbnail.getResolvedUrl());
    assertNull(thumbnail.getImageSpecificContent().getWidth());
    assertNull(thumbnail.getImageSpecificContent().getHeight());
    
    AuditStamp created = share.getCreated();
    assertEquals("urn:li:person:A8xe03Qt10", created.getActor());
    assertEquals(new Long(1471967236000L), created.getTime());
    assertNull(created.getImpersonator());
    
    DistributionTarget distributionTarget = share.getDistribution().getDistributionTarget();
    assertNotNull(distributionTarget);
    assertEquals(2, distributionTarget.getIndustries().size());
    assertEquals("urn:li:industry:12", distributionTarget.getIndustries().get(0));
    assertEquals("urn:li:industry:37", distributionTarget.getIndustries().get(1));
    assertEquals(2, distributionTarget.getSeniorities().size());
    assertEquals("urn:li:seniority:4", distributionTarget.getSeniorities().get(0));
    assertEquals("urn:li:seniority:8", distributionTarget.getSeniorities().get(1));
    
    assertEquals("Test Share!", share.getText().getText());
  }
  
  private String readFileToString(final String fileName) {
    Stream<String> lines = null;
    try {
      Path path = Paths.get(getClass().getClassLoader()
          .getResource(fileName).toURI());
      lines = Files.lines(path);
      String data = lines.collect(Collectors.joining("\n"));
      return data;
    } catch (IOException | URISyntaxException ex) {
      throw new RuntimeException(ex);
    } finally {
      if (lines != null) {
        lines.close();
      }
    }
  }

}
