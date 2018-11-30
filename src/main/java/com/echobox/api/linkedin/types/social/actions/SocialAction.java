package com.echobox.api.linkedin.types.social.actions;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;

import lombok.Getter;
import lombok.Setter;


/**
 * Social Action Model
 * @author Alexandros
 */
public class SocialAction {
  
  @Getter
  @Setter
  @LinkedIn
  private CommentsSummary commentsSummary;
  
  @Getter
  @Setter
  @LinkedIn
  private LikesSummary likesSummary;
  
  @Getter
  @Setter
  @LinkedIn
  private String target;
  
  @Getter
  @Setter
  @LinkedIn("$URN")
  private String URN;
  
  public static class CommentsSummary {
    
    @Getter
    @Setter
    @LinkedIn
    private int totalFirstLevelComments;
    
    @Getter
    @Setter
    @LinkedIn
    private int aggregatedTotalComments;
  }
  
  public static class LikesSummary {
    @Getter
    @Setter
    @LinkedIn
    private int totalLikes;
  }
}

