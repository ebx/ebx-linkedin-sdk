package com.echobox.api.linkedin.client;

import lombok.Getter;

public class DebugHeaderInfo {
  
  /**
   * x-li-fabric
   */
  @Getter
  private final String fabric;

  /**
   * x-li-format
   */
  @Getter
  private final String format;

  /**
   * x-li-request-id
   */
  @Getter
  private final String requestId;
  
  /**
   * x-li-uuid
   */
  @Getter
  private final String uuid;

  public DebugHeaderInfo(String fabic, String format, String requestId, String uuid) {
    this.fabric = fabic;
    this.format = format;
    this.requestId = requestId;
    this.uuid = uuid;
  }

}
