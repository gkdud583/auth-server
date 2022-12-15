package com.example.smilegateauthserver.user.controller;

import com.example.smilegateauthserver.common.auth.JwtProvider;
import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.dto.LoginRequest;
import com.example.smilegateauthserver.user.dto.LoginResponse;
import com.example.smilegateauthserver.user.dto.RegisterRequest;
import com.example.smilegateauthserver.user.dto.UserResponse;
import com.example.smilegateauthserver.user.service.UserService;
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
  private final JwtProvider jwtProvider;

  @PostMapping("register")
  public void register(@RequestBody @Valid RegisterRequest request) {
    userService.register(request);
  }

  @PostMapping("login")
  public LoginResponse login(@RequestBody @Valid LoginRequest request) {
    User user = userService.login(request);

    String token = jwtProvider.generateToken(user.getId(), user.getRole());

    return LoginResponse.builder()
                        .token(token)
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
