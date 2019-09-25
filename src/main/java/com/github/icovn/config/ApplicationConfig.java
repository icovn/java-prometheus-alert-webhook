package com.github.icovn.config;

import com.github.icovn.modal.Group;
import com.github.icovn.modal.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.private")
@Data
public class ApplicationConfig {

  private List<String> ips;
  private List<Group> groups;

  public boolean isInWhiteListIp(String userIp){
    for(String ip: ips){
      if(userIp.startsWith(ip)){
        return true;
      }
    }
    return false;
  }

  public List<User> getUsers(String groupId){
    for(Group group: groups){
      if(group.getId().equals(groupId)){
        return group.getUsers();
      }
    }

    return new ArrayList<>();
  }
}
