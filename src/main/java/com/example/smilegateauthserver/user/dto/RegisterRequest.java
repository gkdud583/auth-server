package com.example.smilegateauthserver.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterRequest {
  @NotBlank(message = "이메일은 필수 값입니다.")
  @Size(max = 255, message = "이메일의 최대 길이는 255자입니다.")
  private final String email;

  @NotBlank(message = "비밀번호는 필수 값입니다.")
  @Size(max = 255, message = "비밀번호의 최대 길이는 255자입니다.")
  private final String password;
}
