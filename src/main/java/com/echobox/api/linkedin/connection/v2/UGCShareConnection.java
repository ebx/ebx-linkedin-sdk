package com.echobox.api.linkedin.connection.v2;

import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.ucg.UCGShare;
import com.echobox.api.linkedin.types.ucg.ViewContext;
import com.echobox.api.linkedin.types.urn.URN;
import com.echobox.api.linkedin.util.URLUtils;

import java.util.List;

public class UGCShareConnection extends ConnectionBaseV2 {
  
  private static final String UGC_POST = "/ugcPosts";

  public UGCShareConnection(LinkedInClient linkedinClient) {
    super(linkedinClient);
  }
  
  public UCGShare createUCGPost(UCGShare shareBody) {
    return linkedinClient.publish(UGC_POST, UCGShare.class, shareBody);
  }
  
  public UCGShare retrieveUCGPost(URN shareURN, ViewContext viewContext) {
    Parameter viewContextParam = Parameter.with("viewContext", viewContext);
    return linkedinClient.fetchObject(UGC_POST + "/" + URLUtils.urlDecode(shareURN.toString()),
        UCGShare.class, viewContextParam);
  }
  
  public List<UCGShare> retrieveUGCPostsByAuthors() {
    return null;
  }
  
  public List<UCGShare> retrieveUGCPostsByContainerEntities() {
    return null;
  }
  
  public List<UCGShare> retrieveUGCPostsByPermalinkSuffixes() {
    return null;
  }
  
  public void deleteUGCPost(URN shareURN) {
    linkedinClient.deleteObject(UGC_POST + "/" + URLUtils.urlEncode(shareURN.toString()));
  }

}
