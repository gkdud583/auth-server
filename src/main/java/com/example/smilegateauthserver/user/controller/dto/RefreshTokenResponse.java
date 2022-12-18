package com.example.smilegateauthserver.user.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshTokenResponse {

  private String token;

  @Builder
  private RefreshTokenResponse(String token) {
    this.token = token;
  }

  public static RefreshTokenResponse from(String token) {
    return RefreshTokenResponse.builder()
                               .token(token)
                               .build();
  }
}
