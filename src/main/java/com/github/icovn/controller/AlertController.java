package com.github.icovn.controller;

import com.github.icovn.modal.Alert;
import com.github.icovn.modal.Alerts;
import com.github.icovn.config.ApplicationConfig;
import com.github.icovn.modal.User;
import com.github.icovn.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    if(isValidRequest()){
      log.info("(sms)alerts: {}", alerts);
      for(Alert alert:alerts.getAlerts()){
        for(User user: applicationConfig.getUsers()){
          if(!user.getPhone().isEmpty()){
            smsService.sendSms(user.getPhone(), getSmsContent(alert));
          }else {
            log.warn("(sms)alerts: {}, user:", alerts, user);
          }
        }
      }
    }else {
      log.warn("(sms)alerts: {}", alerts);
    }
  }

  private String getSmsContent(Alert alert){
    String sms = "";
    if(alert.getStatus().equals("firing")){
      sms += "*TOPKID - Alerts Firing:*\n";
    }else {
      sms += "*TOPKID - Alerts Resolved:*\n";
    }
    sms += alert.getLabels().get("instance") + ": " + alert.getAnnotations().get("description")+ "\n";
    if(alert.getStartsAt() != null){
      sms += "start at: " + alert.getStartsAt() + "\n";
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
