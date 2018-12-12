package com.echobox.api.linkedin.client.paging;

import com.echobox.api.linkedin.client.DefaultLinkedInClient;
import com.echobox.api.linkedin.client.LinkedInClient;
import com.echobox.api.linkedin.version.Version;

public class Main {
  public static void main(String[] args) {
    final DefaultLinkedInClient client = new DefaultLinkedInClient(Version.DEFAULT_VERSION);

    final LinkedInClient.AccessToken accessToken = client
        .obtainUserAccessToken("8654ce61xh8qlj", "wtmk6V1QBQrKytoN",
            "http://localhost:8080/v3/apis/connect/redirect",
            "AQSrZE2FC3NyrYlB5hjypVdvBpfPnWyPlHjg9Rh3jq0-ZXTGVHN-QuGP4EGEHlHFzN"
                + "-jC_Xm9f9A1tzawbN1yyH6mfGJ17KtnqFiEn72rJT50Btl0xC"
                + "-ZNLJfc3p7aA5V2w74eQ3UnLHbiuc1onJZPXgZctW0u_VF6ta5pbUpFL4JvBuWCk7UiJECLOVrQ");

    System.out.println(accessToken);
  }
}
