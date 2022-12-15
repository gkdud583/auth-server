package com.example.smilegateauthserver.common.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
  private static final String HEADER_NAME = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer";
  private final JwtProvider jwtProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String token = resolveToken((HttpServletRequest)request);
    if (token != null && jwtProvider.validateToken(token)) {
      Authentication authentication = jwtProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(HEADER_NAME);
    if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
      return token.substring(TOKEN_PREFIX.length() + 1);
    }
    return null;
  }
}
