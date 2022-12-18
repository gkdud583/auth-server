package com.example.smilegateauthserver.common.argumentResolver;

import com.example.smilegateauthserver.common.annotation.LoginUser;
import com.example.smilegateauthserver.common.auth.JwtToken;
import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final UserRepository userRepository;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    JwtToken authentication = (JwtToken) SecurityContextHolder.getContext().getAuthentication();
    long userId = Long.valueOf(String.valueOf(authentication.getPrincipal()));
    User user = userRepository.findById(userId)
                              .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    return user;
  }
}
