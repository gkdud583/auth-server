package com.example.smilegateauthserver.common.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public final class CookieUtil {

  public static Cookie generate(String key, String data) {
    Cookie cookie = new Cookie(key, data);
    cookie.setDomain("localhost");
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    return cookie;
  }

  public static Cookie find(HttpServletRequest httpServletRequest, String key) {
    Cookie[] cookies = httpServletRequest.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(key)) {
        return cookie;
      }
    }
    return null;
  }
}
