package com.github.icovn.controller;

import com.github.icovn.config.ApplicationConfig;
import com.github.icovn.modal.Alerts;
import com.github.icovn.service.AlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AlertController extends BaseController {

  private final ApplicationConfig applicationConfig;
  private final AlertService alertService;

  @Autowired
  public AlertController(ApplicationConfig applicationConfig, AlertService alertService) {
    this.applicationConfig = applicationConfig;
    this.alertService = alertService;
  }

  @PostMapping("/alert")
  public void alert(@RequestBody Alerts alerts){
    String ip = getIp();

    if(isValidRequest()){
      log.info("(alert)VALID_REQUEST|ip: {}, alerts: {}", ip, alerts);
      alertService.send(alerts);
    }else {
      log.warn("(alert)INVALID_REQUEST|ip: {}, alerts: {}", ip, alerts);
    }
  }

  private boolean isValidRequest(){
    if(applicationConfig.getEnabled()){
      return applicationConfig.isInWhiteListIp(getIp());
    }

    return true;
  }
}
