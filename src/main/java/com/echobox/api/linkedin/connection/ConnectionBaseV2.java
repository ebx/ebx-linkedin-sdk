package com.echobox.api.linkedin.connection;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.version.Version;

public class ConnectionBaseV2 extends ConnectionBase {
  
  protected ConnectionBaseV2(LinkedInClient linkedinClient) {
    super(linkedinClient);
    if (!Version.V2.equals(linkedinClient.getVersion())) {
      throw new IllegalStateException(
          "The LinkedIn clinet should be set to V2 to access the endpoints");
    }
  }
}
