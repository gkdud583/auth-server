package com.example.smilegateauthserver.common.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtToken extends AbstractAuthenticationToken {
  private Object principal;

  public JwtToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    throw new AssertionError(); // 호출 금지
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public Object getCredentials() {
    return null;
  }
}
