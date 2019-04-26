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
        for(User user: applicationConfig.getUsers()){
          if(!user.getPhone().isEmpty()){
            if(ip.startsWith("10.0")){
              //production
              smsService.sendSms(user.getPhone(), getSmsContent(alert, false), SmsBrand.TOPICA);
            }else {
              smsService.sendSms(user.getPhone(), getSmsContent(alert, true), SmsBrand.KIDTOPI);
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

  private String getSmsContent(Alert alert, boolean isDev){
    String prefix = "*TOPKID";
    if(isDev){
      prefix = "*TOPKID-DEV";
    }

    String sms = "";
    if(alert.getStatus().equals("firing")){
      sms += prefix + " - Alerts Firing:*\n";
    }else {
      sms += prefix + " - Alerts Resolved:*\n";
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
