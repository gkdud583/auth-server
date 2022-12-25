package com.example.smilegateauthserver.user.controller;

import static com.example.smilegateauthserver.user.exception.ExceptionMessage.NOTFOUND_REFRESH_TOKEN;

import com.example.smilegateauthserver.common.util.CookieUtil;
import com.example.smilegateauthserver.user.controller.dto.LoginRequest;
import com.example.smilegateauthserver.user.controller.dto.LoginResponse;
import com.example.smilegateauthserver.user.controller.dto.RefreshTokenResponse;
import com.example.smilegateauthserver.user.controller.dto.RegisterRequest;
import com.example.smilegateauthserver.user.controller.dto.UserResponse;
import com.example.smilegateauthserver.user.service.UserService;
import com.example.smilegateauthserver.user.service.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("register")
  public void register(@RequestBody @Valid RegisterRequest request) {
    userService.register(request);
  }

  @PostMapping("login")
  public LoginResponse login(@RequestBody @Valid LoginRequest request,
    HttpServletResponse httpServletResponse) {
    TokenResponse tokenResponse = userService.login(request);
    setRefreshTokenCookie(httpServletResponse, tokenResponse.getRefreshToken());
    return LoginResponse.from(tokenResponse.getAccessToken());
  }

  @PostMapping("logout")
  public void logout(@AuthenticationPrincipal Object userId) {
    userService.logout(Long.valueOf(String.valueOf(userId)));
  }

  @PostMapping("token/refresh")
  public RefreshTokenResponse refreshToken(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse) {
    Cookie cookie = CookieUtil.find(httpServletRequest, "refreshToken");
    if (cookie == null) {
      throw new IllegalArgumentException(NOTFOUND_REFRESH_TOKEN.getMsg());
    }
    TokenResponse tokenResponse = userService.refreshToken(cookie.getValue());

    setRefreshTokenCookie(httpServletResponse, tokenResponse.getRefreshToken());
    return RefreshTokenResponse.from(tokenResponse.getAccessToken());
  }

  @GetMapping
  public List<UserResponse> findAll() {
    return userService.findAll()
                      .stream()
                      .map((user -> UserResponse.of(user.getId(), user.getEmail())))
                      .collect(Collectors.toList());
  }

  private void setRefreshTokenCookie(HttpServletResponse httpServletResponse, String token) {
    httpServletResponse.addCookie(
      CookieUtil.generate("refreshToken", token));
  }
}
