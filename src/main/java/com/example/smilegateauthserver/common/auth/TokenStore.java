package com.example.smilegateauthserver.common.auth;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenStore {

  @Value("${jwt.refreshTokenExpirationInMs}")
  private long duration;
  private final RedisTemplate redisTemplate;

  public void set(long key, String value) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(String.valueOf(key), value, Duration.ofSeconds(duration));
  }

  public void remove(long key) {
    redisTemplate.delete(String.valueOf(key));
  }
}
