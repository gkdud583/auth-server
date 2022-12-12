package com.example.smilegateauthserver.user.service;

import org.springframework.stereotype.Service;

import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.dto.RegisterRequest;
import com.example.smilegateauthserver.user.exception.ExceptionMessage;
import com.example.smilegateauthserver.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public void register(RegisterRequest request) {
    if (isDuplicatedEmail(request.getEmail())) {
      throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_EMAIL.getMsg());
    }
    User user = User
        .builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .build();
    userRepository.save(user);
  }

  private boolean isDuplicatedEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
