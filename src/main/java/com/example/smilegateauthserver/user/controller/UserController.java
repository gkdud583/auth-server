package com.example.smilegateauthserver.user.controller;

import com.example.smilegateauthserver.common.annotation.LoginUser;
import com.example.smilegateauthserver.common.util.CookieUtil;
import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.controller.dto.LoginRequest;
import com.example.smilegateauthserver.user.controller.dto.LoginResponse;
import com.example.smilegateauthserver.user.controller.dto.RegisterRequest;
import com.example.smilegateauthserver.user.controller.dto.UserResponse;
import com.example.smilegateauthserver.user.service.UserService;
import com.example.smilegateauthserver.user.service.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    httpServletResponse.addCookie(
      CookieUtil.generateCookie("refreshToken", tokenResponse.getRefreshToken()));
    return LoginResponse.builder()
                        .token(tokenResponse.getAccessToken())
                        .build();
  }

  @GetMapping
  public List<UserResponse> findAll() {
    return userService.findAll()
                      .stream()
                      .map((user -> UserResponse.of(user.getId(), user.getEmail())))
                      .collect(Collectors.toList());
  }
}
