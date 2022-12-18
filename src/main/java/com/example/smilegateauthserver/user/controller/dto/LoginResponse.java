package com.example.smilegateauthserver.user.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

  private final String token;

  @Builder(access = AccessLevel.PRIVATE)
  private LoginResponse(String token) {
    this.token = token;
  }

  public static LoginResponse from(String token) {
    return LoginResponse.builder()
      .token(token)
      .build();
  }
}
