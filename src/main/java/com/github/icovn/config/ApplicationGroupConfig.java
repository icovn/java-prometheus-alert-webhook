package com.github.icovn.config;

import com.github.icovn.modal.Group;
import com.github.icovn.modal.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application")
@Data
public class ApplicationGroupConfig {

  private List<Group> groups;

  public List<User> getUsers(String groupId){
    for(Group group: groups){
      if(group.getId().equals(groupId)){
        return group.getUsers();
      }
    }

    return new ArrayList<>();
  }
}
