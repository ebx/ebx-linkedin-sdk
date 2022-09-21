package com.echobox.api.linkedin.connection.versioned;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.connection.ConnectionBase;
import com.echobox.api.linkedin.version.Version;

public class VersionedConnection extends ConnectionBase {
  
  protected final String version;
  
  /**
   * Instantiates a new connection base.
   *
   * @param linkedinClient the LinkedIn client
   */
  public VersionedConnection(LinkedInClient linkedinClient, String version) {
    super(linkedinClient);
    if (Version.VERSIONING != linkedinClient.getVersion()) {
      throw new IllegalArgumentException("The linkedInClient should be VERSIONING");
    }
    this.version = version;
  }
  
  
}
