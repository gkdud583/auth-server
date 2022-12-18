package com.example.smilegateauthserver.user.service;

import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.controller.dto.LoginRequest;
import com.example.smilegateauthserver.user.controller.dto.RegisterRequest;
import com.example.smilegateauthserver.user.service.dto.TokenResponse;
import java.util.List;

public interface UserService {

  void register(RegisterRequest request);

  TokenResponse login(LoginRequest request);

  void logout(long userId);

  TokenResponse refreshToken(String token);

  List<User> findAll();
}
