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

package com.echobox.api.linkedin.types.organization;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.ImageV2Elements;
import com.echobox.api.linkedin.types.urn.URN;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Cropped image.
 * @see <a href="https://developer.linkedin.com/docs/guide/v2/organizations/organization-lookup-api#croppedimg"> Cropped Image Schema</a>
 * @author clementcaylux 
 */
public class CroppedImage {
  
  @Getter
  @Setter
  @LinkedIn
  private CropInfo cropInfo;

  @Getter
  @Setter
  @LinkedIn
  private URN cropped;

  @Getter
  @Setter
  @LinkedIn
  private URN original;
  
  @Setter
  @Getter
  @LinkedIn("original~")
  private ImageV2Elements originalElements;
}
