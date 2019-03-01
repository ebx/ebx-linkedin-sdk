package com.echobox.api.linkedin.types.request;

import com.echobox.api.linkedin.jsonmapper.JsonMapper;
import com.echobox.api.linkedin.jsonmapper.LinkedIn;
import com.echobox.api.linkedin.types.ShareText;
import com.eclipsesource.json.Json;

public class UpdateShareRequestBody {
  
  @LinkedIn("patch")
  private PatchBody patch;

  public UpdateShareRequestBody(ShareText shareText, JsonMapper jsonMapper) {
    PatchBody patchBody = new PatchBody();
    patchBody.setSet(Json.object().set("text", Json.parse(jsonMapper.toJson(shareText)).asObject()));
    patch = patchBody;
  }

}
