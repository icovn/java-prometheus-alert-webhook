package com.github.icovn.service;

import vn.edu.topica.sdk.constant.SmsBrand;

public interface SmsService {

  void sendSms(String phone, String content);

  void sendSms(String phone, String content, SmsBrand brand);
}
