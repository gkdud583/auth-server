package com.example.smilegateauthserver.user.service;

import com.example.smilegateauthserver.user.dto.RegisterRequest;

public interface UserService {
  void register(RegisterRequest request);
}