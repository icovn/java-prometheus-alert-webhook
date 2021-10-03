package com.github.icovn.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.private")
@Data
public class ApplicationConfig {
  
  private Boolean enabled;
  
  private List<String> ips;

  public boolean isInWhiteListIp(String userIp){
    for(String ip: ips){
      if(userIp.startsWith(ip)){
        return true;
      }
    }
    return false;
  }
}
