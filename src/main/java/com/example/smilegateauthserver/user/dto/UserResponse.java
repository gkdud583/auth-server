package com.example.smilegateauthserver.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

  private final long id;
  private final String email;

  @Builder(access = AccessLevel.PRIVATE)
  public UserResponse(long id, String email) {
    this.id = id;
    this.email = email;
  }

  public static UserResponse of(long id, String email) {
    return UserResponse.builder()
                       .id(id)
                       .email(email)
                       .build();
  }
}
