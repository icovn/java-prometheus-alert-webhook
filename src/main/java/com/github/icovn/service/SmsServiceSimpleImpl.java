package com.github.icovn.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsServiceSimpleImpl implements SmsService {

  @Override
  public void sendSms(String phone, String content) {
    log.info("(sendSms)phone: {}, content: {}", phone, content);
  }
}