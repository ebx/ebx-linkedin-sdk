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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for the DefaultJsonMapper tests
 * @author Joanna
 *
 */
public abstract class DefaultJsonMapperTestBase {

  /**
   * Read file to string.
   *
   * @param fileName the file name
   * @return the converted to string file
   */
  protected String readFileToString(final String fileName) {
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
