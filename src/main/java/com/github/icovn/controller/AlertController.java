package com.github.icovn.controller;

import com.github.icovn.modal.Alert;
import com.github.icovn.modal.Alerts;
import com.github.icovn.config.PrivateIpConfig;
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

  private final PrivateIpConfig privateIpConfig;
  private final SmsService smsService;

  @Autowired
  public AlertController(
      PrivateIpConfig privateIpConfig,
      SmsService smsService
  ) {
    this.privateIpConfig = privateIpConfig;
    this.smsService = smsService;
  }

  @PostMapping("/alert/sms")
  public void sms(@RequestBody Alerts alerts){
    if(isValidRequest()){
      log.info("(sms)alerts: {}", alerts);
      for(Alert alert:alerts.getAlerts()){
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
        smsService.sendSms(alerts.getReceiver(), sms);
      }
    }else {
      log.warn("(sms)alerts: {}", alerts);
    }
  }

  private boolean isValidRequest(){
    if(privateEnabled){
      return privateIpConfig.isInWhiteListIp(getIp());
    }

    return true;
  }
}
