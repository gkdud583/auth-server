package com.example.smilegateauthserver.common.auth;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.smilegateauthserver.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class JwtProvider {
  private Key key;
  private final String secret;
  private final long expirationInMs;

  public String generateToken(long userId, User.Role role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);
    claims.put("authority", role.getName());

    byte[] keyBytes = Decoders.BASE64.decode(secret);
    key = Keys.hmacShaKeyFor(keyBytes);

    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(now.getTime()))
        .setExpiration(new Date(now.getTime() + expirationInMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty", e);
    }
    return false;
  }

  public Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority((String)claims.get("authority")));
    return new JwtToken(claims.get("userId"), authorities);
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
