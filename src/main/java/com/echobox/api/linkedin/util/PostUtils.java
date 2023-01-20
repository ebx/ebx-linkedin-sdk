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

package com.echobox.api.linkedin.util;

import com.echobox.api.linkedin.types.posts.ArticleContent;
import com.echobox.api.linkedin.types.posts.Content;
import com.echobox.api.linkedin.types.posts.Post;
import com.echobox.api.linkedin.types.urn.URN;
import org.apache.commons.lang3.StringUtils;

public abstract class PostUtils {
  
  /**
   * Helper function for filling article content to post
   * @param post post to be filled
   * @param articleUrl link of the article
   * @param thumbnail thumbnail image as ImageURN
   * @param title title
   * @param description description
   */
  public static void fillArticleContent(Post post, String articleUrl, URN thumbnail, String title,
      String description) {
    if (post == null || StringUtils.isBlank(articleUrl)) {
      throw new IllegalArgumentException("Post and articleUrl must be provided");
    }
    Content content = new Content();
    ArticleContent article = new ArticleContent();
    article.setSource(articleUrl);
    article.setThumbnail(thumbnail);
    article.setTitle(title);
    article.setDescription(description);
    content.setArticle(article);
    post.setContent(content);
  }
  
  private PostUtils() {}
}
