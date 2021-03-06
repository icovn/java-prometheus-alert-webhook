package com.github.icovn.service;

import com.github.icovn.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import vn.edu.topica.sdk.client.SmsClient;
import vn.edu.topica.sdk.constant.SmsBrand;
import vn.edu.topica.sdk.util.SmsUtil;

@Slf4j
public class SmsServiceTopicaImpl implements SmsService {

  @Value("${application.service.sms.secretKey}")
  private String secretKey;

  @Value("${application.service.sms.appName}")
  private String appName;

  @Override
  public void sendSms(String phone, String content) {
    log.debug("(sendSms)phone: {}, content: {}", phone, content);

    sendSms(phone, content, SmsBrand.DEFAULT);
  }

  @Override
  public void sendSms(String phone, String content, SmsBrand brand) {
    log.debug("(sendSms)phone: {}, content: {}, brand: {}", phone, content, brand);
    try {
      SmsClient smsClient = new SmsClient(appName, secretKey);
      smsClient.send(SmsUtil.removeAccent(content), phone, brand);
    }catch (Exception ex){
      log.error("(sendSms)phone: {}, ex: {}", phone, ExceptionUtil.getFullStackTrace(ex, true));
    }
  }
}
