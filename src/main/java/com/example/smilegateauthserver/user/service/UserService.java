package com.example.smilegateauthserver.user.service;

import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.dto.LoginRequest;
import com.example.smilegateauthserver.user.dto.RegisterRequest;
import java.util.List;

public interface UserService {

  void register(RegisterRequest request);

  User login(LoginRequest request);
}
