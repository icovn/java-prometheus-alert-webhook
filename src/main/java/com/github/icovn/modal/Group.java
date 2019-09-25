package com.github.icovn.modal;

import java.util.List;
import lombok.Data;

@Data
public class Group {

  private String id;
  private List<User> users;
}
