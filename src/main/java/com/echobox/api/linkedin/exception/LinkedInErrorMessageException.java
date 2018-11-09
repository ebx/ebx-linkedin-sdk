
package com.echobox.api.linkedin.exception;

import org.json.JSONObject;

/**
 * Abstract class to provide access to the JSON Facebook provides in case of an error
 *
 * Sometime a developer needs to access the plain error to get a more in depth view to the error.
 */
abstract public class LinkedInErrorMessageException extends LinkedInException {

  /**
   * Default serial version UID
   */
  private static final long serialVersionUID = 1L;
  
  private JSONObject rawErrorJson;

  public LinkedInErrorMessageException(String message) {
    super(message);
  }

  public LinkedInErrorMessageException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * return the raw error as JSON, may be <code>null</code>
   * 
   * @return raw error
   */
  public JSONObject getRawErrorJson() {
    return rawErrorJson;
  }

  protected void setRawErrorJson(JSONObject rawError) {
    rawErrorJson = rawError;
  }
}
