package com.github.icovn.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

  @Autowired
  HttpServletRequest request;

  String getIp() {
    String ipAddress = request.getHeader("X-FORWARDED-FOR");
    if (ipAddress == null) {
      ipAddress = request.getRemoteAddr();
    }

    return ipAddress;
  }
}
