package com.echobox.api.linkedin.version;

import com.echobox.api.linkedin.logging.LinkedInLogger;

import org.slf4j.Logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Version {
  
  V1("v1"),
  
  V2("v2");
  
  private static Logger LOGGER = LinkedInLogger.getLoggerInstance();
  
  @Getter
  private final String version;
  
  /**
   * Convert the provided code into a company type
   *
   * @param code the code
   * @return if successful the desired company type otherwise null
   */
  public static Version getVersionFromString(String urlElement) {
    for (Version version : Version.values()) {
      if (version.getVersion().equals(urlElement)) {
        return version;
      }
    }
    LOGGER.warn("Could not get company type from code " + urlElement + ", defaulting to v1");
    return V1;
  }

}
