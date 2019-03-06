package com.echobox.api.linkedin.connection.v2;

import com.echobox.api.linkedin.client.BinaryAttachment;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.client.Parameter;
import com.echobox.api.linkedin.types.assets.CheckStatusUpload;
import com.echobox.api.linkedin.types.assets.CompleteMultiPartUploadBody;
import com.echobox.api.linkedin.types.assets.RegisterUpload;
import com.echobox.api.linkedin.types.assets.RegisterUploadRequestBody;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

public class AssetsConnection extends ConnectionBaseV2 {
  
  private static final String ASSETS = "/assets";

  public AssetsConnection(LinkedInClient linkedInClient) {
    super(linkedInClient);
  }
  
  public RegisterUpload registerUpload(RegisterUploadRequestBody registerUploadRequestBody) {
    return linkedinClient.publish(ASSETS, RegisterUpload.class, registerUploadRequestBody,
        Parameter.with("action", "registerUpload"));
  }
  
  public Map<String, String> uploadAsset(URL uploadURL, Map<String, String> headers,
      String filename, File file) throws IOException {
    byte[] bytes = Files.readAllBytes(file.toPath());
    return uploadAsset(uploadURL, headers, filename, bytes);
  }
  
  public Map<String, String> uploadAsset(URL uploadURL, Map<String, String> headers,
      String filename, byte[] bytes) throws IOException {
    return linkedinClient
        .publishForHeader(uploadURL.toString(), BinaryAttachment.with(filename, bytes), headers);
  }
  
  public void completeMultiPartUpload(CompleteMultiPartUploadBody completeMultiPartUploadBody) {
    linkedinClient.publish(ASSETS, Void.class, completeMultiPartUploadBody, Parameter.with(
        "action", "completeMultiPartUpload"));
  }
  
  public CheckStatusUpload checkStatusOfUpload(String digitalMediaAsset) {
    return linkedinClient.fetchObject(ASSETS + "/" + digitalMediaAsset, CheckStatusUpload.class);
  }

}
