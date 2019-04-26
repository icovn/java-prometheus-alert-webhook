package com.github.icovn.service;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.sdk.constant.SmsBrand;

@Slf4j
public class SmsServiceSimpleImpl implements SmsService {

  @Override
  public void sendSms(String phone, String content) {
    log.info("(sendSms)phone: {}, content: {}", phone, content);
  }

  @Override
  public void sendSms(String phone, String content, SmsBrand brand) {
    log.info("(sendSms)phone: {}, content: {}, brand: {}", phone, content, brand);
  }
}
