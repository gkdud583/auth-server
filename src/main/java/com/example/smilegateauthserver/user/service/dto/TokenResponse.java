package com.example.smilegateauthserver.user.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {

  private final String accessToken;
  private final String refreshToken;

  @Builder(access = AccessLevel.PRIVATE)
  private TokenResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static TokenResponse of(String accessToken, String refreshToken) {
    return TokenResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
  }
}
