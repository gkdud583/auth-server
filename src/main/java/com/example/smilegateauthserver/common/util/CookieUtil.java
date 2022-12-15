package com.example.smilegateauthserver.common.util;


import jakarta.servlet.http.Cookie;

public final class CookieUtil {

  public static Cookie generateCookie(String key, String data) {
    Cookie cookie = new Cookie(key, data);
    cookie.setDomain("localhost");
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    return cookie;
  }
}
