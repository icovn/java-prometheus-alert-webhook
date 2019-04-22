package com.github.icovn.service;

import org.springframework.beans.factory.annotation.Value;
import vn.edu.topica.sdk.client.SmsClient;
import vn.edu.topica.sdk.util.SmsUtil;

public class SmsServiceTopicaImpl implements SmsService {

  @Value("${application.service.sms.secretKey}")
  private String secretKey;

  @Value("${application.service.sms.appName}")
  private String appName;

  @Override
  public void sendSms(String phone, String content) {
    SmsClient smsClient = new SmsClient(appName, secretKey);
    smsClient.send(SmsUtil.removeAccent(content), phone);
  }
}
