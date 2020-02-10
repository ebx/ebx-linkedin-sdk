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

import com.echobox.api.linkedin.util.ReflectionUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Represents a binary file that can be uploaded to LinkedIn.
 * Normally this would be a photo or video.
 * @author Joanna
 *
 */
public class BinaryAttachment {

  @Getter
  private String filename;

  private byte[] data;

  private InputStream dataStream;

  private String contentType = null;

  @Getter
  private String fieldName;

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  private BinaryAttachment(String filename, byte[] data) {
    if (StringUtils.isBlank(filename)) {
      throw new IllegalArgumentException("Binary attachment filename cannot be blank.");
    }
    if (data == null) {
      throw new IllegalArgumentException("Binary attachment data cannot be null.");
    }

    this.filename = filename;
    this.data = data;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  private BinaryAttachment(String fieldName, String filename, byte[] data) {
    this(filename, data);
    if (StringUtils.isBlank(fieldName)) {
      throw new IllegalArgumentException("Field name cannot be null.");
    }

    this.fieldName = fieldName;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null}, {@code filename} is {@code null} or blank,
   *           or {@code contentType} is {@code null} or blank.
   */
  private BinaryAttachment(String filename, byte[] data, String contentType) {
    this(filename, data);
    if (StringUtils.isBlank(contentType)) {
      throw new IllegalArgumentException("ContentType cannot be null.");
    }

    this.contentType = contentType;
  }

  /**
   * Creates a new binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @param fieldName
   *          The field name the binary belongs to
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null}, {@code filename} is {@code null} or blank, or
   *           {@code contentType} is {@code null} or blank.
   */
  private BinaryAttachment(String fieldName, String filename, byte[] data, String contentType) {
    this(filename, data, contentType);
    if (StringUtils.isBlank(fieldName)) {
      throw new IllegalArgumentException("Field name cannot be null.");
    }

    this.fieldName = fieldName;
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String filename, byte[] data) {
    return new BinaryAttachment(filename, data);
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param fieldName
   *          The field name the binary belongs to
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String fieldName, String filename, byte[] data) {
    return new BinaryAttachment(fieldName, filename, data);
  }

  /**
   * Creates a binary attachment.
   * 
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String filename, byte[] data, String contentType) {
    return new BinaryAttachment(filename, data, contentType);
  }

  /**
   * Creates a binary attachment.
   *
   * @param filename
   *          The attachment's filename.
   * @param data
   *          The attachment's data.
   * @param contentType
   *          The attachment's contentType.
   * @param fieldName
   *          The field name the binary belongs to
   * @return A binary attachment.
   * @throws IllegalArgumentException
   *           If {@code data} is {@code null} or {@code filename} is {@code null} or blank.
   */
  public static BinaryAttachment with(String fieldName, String filename, byte[] data,
      String contentType) {
    return new BinaryAttachment(fieldName, filename, data, contentType);
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
    return format("[filename=%s]", getFilename());
  }

  /**
   * The attachment's data.
   * 
   * @return The attachment's data.
   */
  public InputStream getDataInputStream() {
    if (data != null) {
      return new ByteArrayInputStream(data);
    } else if (dataStream != null) {
      return dataStream;
    } else {
      throw new IllegalStateException("Either the byte[] or the stream mustn't be null at this"
          + "point.");
    }
  }
  
  /**
   * The attachment's data.
   * 
   * @return The attachment's data.
   */
  public byte[] getData() {
    if (data != null) {
      return data;
    } else {
      throw new IllegalStateException("Either the byte[] or the stream mustn't be null at this"
          + "point.");
    }
  }

  /**
   * return the given content type or try to guess from stream or file name. Depending of the
   * available data.
   * 
   * @return the content type
   */
  public String getContentType() {
    if (contentType != null) {
      return contentType;
    }

    if (dataStream != null) {
      try {
        contentType = URLConnection.guessContentTypeFromStream(dataStream);
      } catch (IOException ioe) {
        // ignore exception
      }
    }

    if (data != null) {
      contentType = URLConnection.guessContentTypeFromName(filename);
    }

    // fallback - if we have no contenttype and cannot detect one, use 'application/octet-stream'
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return contentType;
  }
}
