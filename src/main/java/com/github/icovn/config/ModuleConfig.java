package com.github.icovn.config;

import com.github.icovn.service.SmsService;
import com.github.icovn.service.SmsServiceSimpleImpl;
import com.github.icovn.service.SmsServiceVietnamImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

  @Value("${application.private.enabled}")
  private boolean privateEnabled;

  @Bean
  public SmsService smsService(){
    if(privateEnabled){
      return new SmsServiceVietnamImpl();
    }

    return new SmsServiceSimpleImpl();
  }
}
