package com.github.icovn.modal;

import com.github.icovn.util.DateUtil;
import java.util.Date;
import java.util.Map;
import lombok.Data;

@Data
public class Alert {

  private String status;
  private Map<String, String> labels;
  private Map<String, String> annotations;
  private String startsAt;
  private String endsAt;
  private String generatorURL;

  public Date getStartsAtInDate(){
    return DateUtil.toDate(startsAt, "yyyy-MM-dd'T'HH:mm:ssXXX");
  }

  public Date getEndsAtInDate(){
    return DateUtil.toDate(endsAt, "yyyy-MM-dd'T'HH:mm:ssXXX");
  }
}
