package com.github.icovn.controller;

import com.github.icovn.modal.Alert;
import com.github.icovn.modal.Alerts;
import com.github.icovn.config.ApplicationConfig;
import com.github.icovn.modal.User;
import com.github.icovn.service.SmsService;
import com.github.icovn.util.DateUtil;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.topica.sdk.constant.SmsBrand;

@RestController
@Slf4j
public class AlertController extends BaseController {

  @Value("${application.private.enabled}")
  private boolean privateEnabled;

  private final ApplicationConfig applicationConfig;
  private final SmsService smsService;

  @Autowired
  public AlertController(
      ApplicationConfig applicationConfig,
      SmsService smsService
  ) {
    this.applicationConfig = applicationConfig;
    this.smsService = smsService;
  }

  @PostMapping("/alert/sms")
  public void sms(@RequestBody Alerts alerts){
    String ip = getIp();

    if(isValidRequest()){
      log.info("(sms)VALID_REQUEST|ip: {}, alerts: {}", ip, alerts);
      for(Alert alert:alerts.getAlerts()){
        for(User user: applicationConfig.getUsers(alerts.getReceiver())){
          if(!user.getPhone().isEmpty()){
            if(ip.startsWith("10.0")){
              //production
              smsService.sendSms(user.getPhone(), getSmsContent(alert, alerts.getReceiver()), SmsBrand.TOPICA);
            }else {
              smsService.sendSms(user.getPhone(), getSmsContent(alert, alerts.getReceiver()), SmsBrand.KIDTOPI);
            }
          }else {
            log.warn("(sms)EMPTY_USER|user: {}, alerts: {}", user, alerts);
          }
        }
      }
    }else {
      log.warn("(sms)INVALID_REQUEST|ip: {}, alerts: {}", ip, alerts);
    }
  }

  private String getSmsContent(Alert alert, String groupId){
    String prefix = "*" + alert.getLabels().get("alertname");

    if(prefix == null){
      prefix = "*" + groupId;
    }

    String sms = prefix + " (" + alert.getStatus().toUpperCase() + "):*\n";
    if(alert.getStatus().equals("firing")){
      String description = alert.getAnnotations().get("description");
      if(description == null){
        description = alert.getAnnotations().get("descriptions");
      }

      sms += "- DESCRIPTION: " + description+ "\n";
      if(alert.getStartsAt() != null){
        sms += "- START AT: " + alert.getStartsAt() + "\n";
        sms += "- SEND ALERT AT: " + DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss") + "\n";
      }
    }else {
      if(alert.getAnnotations().get("summary") != null){
        sms += "- SUMMARY: " + alert.getAnnotations().get("summary") + "\n";
      }
      if(alert.getStartsAt() != null){
        sms += "- START AT: " + alert.getStartsAt() + "\n";
      }
      if(alert.getEndsAt() != null){
        sms += "- END AT: " + alert.getEndsAt() + "\n";
      }
    }

    return sms;
  }

  private boolean isValidRequest(){
    if(privateEnabled){
      return applicationConfig.isInWhiteListIp(getIp());
    }

    return true;
  }
}
