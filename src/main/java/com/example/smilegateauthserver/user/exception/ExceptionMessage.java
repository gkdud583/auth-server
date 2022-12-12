package com.example.smilegateauthserver.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {
  DUPLICATED_EMAIL("중복된 이메일입니다.");
  private final String msg;
}
