package com.github.icovn.service;

import com.github.icovn.http.client.HttpProperties;
import com.github.icovn.http.client.HttpResult;
import com.github.icovn.http.client.service.DefaultHttpClient;
import com.github.icovn.http.client.service.HttpClient;
import com.github.icovn.util.MapperUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import vn.edu.topica.sdk.constant.SmsBrand;

@Slf4j
public class SmsServiceVietnamImpl implements SmsService {

  @Value("${sms.url}")
  private String url;

  @Value("${sms.username}")
  private String username;

  @Value("${sms.password}")
  private String password;

  @Value("${sms.brand}")
  private String brand;

  private HttpClient httpClient = new DefaultHttpClient(new HttpProperties());

  @Override
  public void sendSms(String phone, String content) {
    log.debug("(sendSms)phone: {}, content: {}", phone, content);

    sendSms(phone, content, SmsBrand.DEFAULT);
  }

  @Override
  public void sendSms(String phone, String content, SmsBrand brand) {
    log.debug("(sendSms)phone: {}, content: {}, brand: {}", phone, content, brand);

    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    Map<String, Object> params = new HashMap<>();
    params.put("phone", phone);
    params.put("mess", content);
    params.put("user", username);
    params.put("pass", password);
    params.put("brandName", brand);
    params.put("tranId", System.currentTimeMillis());
    params.put("dataEncode", 0);
    params.put("sendTime", "");

    HttpResult result = httpClient.post(url, headers, MapperUtil.toJson(params));
    log.info("(sendSms)phone: {}, brand: {}, result: {}", phone, brand, result);
  }
}
