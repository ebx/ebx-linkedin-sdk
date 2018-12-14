package com.echobox.api.linkedin.connection;

import com.echobox.api.linkedin.client.LinkedInClient;

public abstract class ConnectionBase {
  
  protected LinkedInClient linkedinClient;
  
  protected ConnectionBase(LinkedInClient linkedinClient) {
    this.linkedinClient = linkedinClient;
  }

}
