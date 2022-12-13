package com.example.smilegateauthserver.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
  DUPLICATED_EMAIL("중복된 이메일입니다."),
  NONEXISTENT_EMAIL("존재하지 않는 이메일입니다."),
  INCONSISTENT_PASSWORD("비밀번호가 일치하지 않습니다.");
  private final String msg;
}
