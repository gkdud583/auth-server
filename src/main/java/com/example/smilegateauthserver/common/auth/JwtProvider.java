package com.example.smilegateauthserver.common.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.example.smilegateauthserver.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProvider {
  private final String secret;
  private final long expirationInMs;

  public String generateToken(long userId, User.Role role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    claims.put("authority", role.getName());

    byte[] keyBytes = Decoders.BASE64.decode(secret);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(now.getTime()))
        .setExpiration(new Date(now.getTime() + expirationInMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }
}
