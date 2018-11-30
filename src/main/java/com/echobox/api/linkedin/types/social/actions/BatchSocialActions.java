package com.echobox.api.linkedin.types.social.actions;

import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.Error;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * A model to describe a batch of social actions
 * @author Alexandros
 */
public class BatchSocialActions {
  
  @Getter
  @Setter
  @LinkedIn
  private Map<String, SocialAction> results;
  
  @Getter
  @Setter
  @LinkedIn
  private Error error;
  
}
