package com.example.smilegateauthserver.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionController {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleMethodArgumentNotValidException(
    MethodArgumentNotValidException e) {
    log.warn("handleMethodArgumentNotValidException: {}", e);
    return new ExceptionResponse(e.getMessage());
  }

  @ExceptionHandler(RedisConnectionFailureException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionResponse handleRedisConnectionFailureException(
    RedisConnectionFailureException e) {
    log.warn("handleRedisConnectionFailureException: {}", e);
    return new ExceptionResponse(e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException e) {
    log.warn("handleIllegalArgumentException: {}", e);
    return new ExceptionResponse(e.getMessage());
  }
}
