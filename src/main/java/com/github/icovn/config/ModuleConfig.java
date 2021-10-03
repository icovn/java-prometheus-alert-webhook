package com.github.icovn.config;

import com.github.icovn.service.AlertService;
import com.github.icovn.service.AlertServiceSkypeImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

  @Bean
  public AlertService alertService(ApplicationSkypeConfig applicationSkypeConfig){
    return new AlertServiceSkypeImpl(applicationSkypeConfig);
  }
}
