package com.github.icovn.service;

import static com.github.icovn.util.MapperUtil.toJson;

import com.github.icovn.config.ApplicationSkypeConfig;
import com.github.icovn.http.HttpProperties;
import com.github.icovn.http.HttpResult;
import com.github.icovn.http.service.DefaultHttpClient;
import com.github.icovn.http.service.HttpClient;
import com.github.icovn.modal.Alert;
import com.github.icovn.modal.Alerts;
import com.github.icovn.util.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlertServiceSkypeImpl implements AlertService {
  
  private final ApplicationSkypeConfig applicationSkypeConfig;
  private final HttpClient httpClient = new DefaultHttpClient(new HttpProperties());
  
  public AlertServiceSkypeImpl(ApplicationSkypeConfig applicationSkypeConfig) {
    this.applicationSkypeConfig = applicationSkypeConfig;
  }
  
  @Override
  public void send(Alerts alerts) {
    //TODO: send alert by group's id
    String groupId = alerts.getReceiver();
    for(Alert alert:alerts.getAlerts()){
      sendAlert(getAlertContent(alert), applicationSkypeConfig.getTopic());
    }
  }
  
  private void sendAlert(String content, String topic) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
  
    Map<String, Object> params = new HashMap<>();
    params.put("content", content);
    params.put("topic", topic);
  
    HttpResult result = httpClient.post(applicationSkypeConfig.getUrl(), headers, toJson(params));
    log.info("(sendAlert)content: {}, topic: {}, result: {}", content, topic, result);
  }
  
  private String getAlertContent(Alert alert){
    String prefix = "*" + alert.getLabels().get("alertname");
    
    String content = prefix + " (" + alert.getStatus().toUpperCase() + "):*\n";
    if(alert.getStatus().equals("firing")){
      String description = alert.getAnnotations().get("description");
      if(description == null){
        description = alert.getAnnotations().get("descriptions");
      }
      
      content += "- DESCRIPTION: " + description+ "\n";
      if(alert.getStartsAt() != null){
        content += "- START AT: " + alert.getStartsAt() + "\n";
        content += "- SEND ALERT AT: " + DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss") + "\n";
      }
    }else {
      if(alert.getAnnotations().get("summary") != null){
        content += "- SUMMARY: " + alert.getAnnotations().get("summary") + "\n";
      }
      if(alert.getStartsAt() != null){
        content += "- START AT: " + alert.getStartsAt() + "\n";
      }
      if(alert.getEndsAt() != null){
        content += "- END AT: " + alert.getEndsAt() + "\n";
      }
    }
    
    return content;
  }
}
