package com.github.icovn.config;

import com.github.icovn.modal.User;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.private")
@Data
public class ApplicationConfig {

  private List<String> ips;
  private List<User> users;

  public boolean isInWhiteListIp(String userIp){
    for(String ip: ips){
      if(userIp.startsWith(ip)){
        return true;
      }
    }
    return false;
  }
}
