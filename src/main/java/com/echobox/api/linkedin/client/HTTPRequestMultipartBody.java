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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle multipart form data in the body of an HTTP request
 * https://varaprasadh.medium.com/how-to-send-multipart-form-data-requests-using-java-native
 * * -httpclient-989f6921dbfa
 * @author Myrto Papakonstantinou
 *
 */

public class HTTPRequestMultipartBody {
  
  private byte[] bytes;
  
  public String getBoundary() {
    return boundary;
  }
  
  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }
  
  private String boundary;
  
  private HTTPRequestMultipartBody(byte[] bytes, String boundary) {
    this.bytes = bytes;
    this.boundary = boundary;
  }
  
  public String getContentType() {
    return "multipart/form-data; boundary=" + this.getBoundary();
  }
  
  public byte[] getBody() {
    return this.bytes;
  }
  
  public static class Builder {
    private static final String DEFAULT_MIMETYPE = "text/plain";
    
    public static class MultiPartRecord {
      private String fieldName;
      private String filename;
      private String contentType;
      private Object content;
      
      public String getFieldName() {
        return fieldName;
      }
      
      public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
      }
      
      public String getFilename() {
        return filename;
      }
      
      public void setFilename(String filename) {
        this.filename = filename;
      }
      
      public String getContentType() {
        return contentType;
      }
      
      public void setContentType(String contentType) {
        this.contentType = contentType;
      }
      
      public Object getContent() {
        return content;
      }
      
      public void setContent(Object content) {
        this.content = content;
      }
    }
    
    private List<MultiPartRecord> parts;
    
    public Builder() {
      this.parts = new ArrayList<>();
    }
    
    public Builder addPart(String fieldName, String fieldValue) {
      MultiPartRecord part = new MultiPartRecord();
      part.setFieldName(fieldName);
      part.setContent(fieldValue);
      part.setContentType(DEFAULT_MIMETYPE);
      this.parts.add(part);
      return this;
    }
    
    public Builder addPart(String fieldName, String fieldValue, String contentType) {
      MultiPartRecord part = new MultiPartRecord();
      part.setFieldName(fieldName);
      part.setContent(fieldValue);
      part.setContentType(contentType);
      this.parts.add(part);
      return this;
    }
    
    public Builder addPart(String fieldName, Object fieldValue, String contentType,
        String fileName) {
      MultiPartRecord part = new MultiPartRecord();
      part.setFieldName(fieldName);
      part.setContent(fieldValue);
      part.setContentType(contentType);
      part.setFilename(fileName);
      this.parts.add(part);
      return this;
    }
    
    public HTTPRequestMultipartBody build() throws IOException {
      String boundary = new BigInteger(256, new SecureRandom()).toString();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      for (MultiPartRecord record : parts) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\""
            + record.getFieldName());
        if (record.getFilename() != null) {
          stringBuilder.append("\"; filename=\"" + record.getFilename());
        }
        out.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        out.write(("\"\r\n").getBytes(StandardCharsets.UTF_8));
        Object content = record.getContent();
        if (content instanceof String) {
          out.write(("\r\n\r\n").getBytes(StandardCharsets.UTF_8));
          out.write(((String) content).getBytes(StandardCharsets.UTF_8));
        } else if (content instanceof byte[]) {
          out.write(
              ("Content-Type: application/octet-stream\r\n\r\n").getBytes(StandardCharsets.UTF_8));
          out.write((byte[]) content);
        } else if (content instanceof File) {
          out.write(
              ("Content-Type: application/octet-stream\r\n\r\n").getBytes(StandardCharsets.UTF_8));
          Files.copy(((File) content).toPath(), out);
        } else {
          out.write(
              ("Content-Type: application/octet-stream\r\n\r\n").getBytes(StandardCharsets.UTF_8));
          ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
          objectOutputStream.writeObject(content);
          objectOutputStream.flush();
        }
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
      }
      out.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
      
      return new HTTPRequestMultipartBody(out.toByteArray(), boundary);
    }
    
  }
}
