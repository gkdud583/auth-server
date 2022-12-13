package com.example.smilegateauthserver.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
  private final String token;

  @Builder
  public LoginResponse(String token) {
    this.token = token;
  }
}
