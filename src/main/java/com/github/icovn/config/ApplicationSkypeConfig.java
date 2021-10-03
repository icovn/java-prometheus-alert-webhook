package com.github.icovn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.skype")
@Data
public class ApplicationSkypeConfig {

  private String topic;
  private String url;
}
