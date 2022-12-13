package com.example.smilegateauthserver.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.dto.LoginRequest;
import com.example.smilegateauthserver.user.dto.RegisterRequest;
import com.example.smilegateauthserver.user.exception.ExceptionMessage;
import com.example.smilegateauthserver.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Override
  @Transactional
  public void register(RegisterRequest request) {
    if (isDuplicatedEmail(request.getEmail())) {
      throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_EMAIL.getMsg());
    }
    User user = User
        .builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(User.Role.USER)
        .build();
    userRepository.save(user);
  }

  @Override
  public User login(LoginRequest request) {
    User user = userRepository
        .findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NONEXISTENT_EMAIL.getMsg()));

    validatePassword(request.getPassword(), user.getPassword());

    return user;
  }

  private void validatePassword(String password, String encodedPassword) {
    if (!passwordEncoder.matches(password, encodedPassword)) {
      throw new IllegalArgumentException(ExceptionMessage.INCONSISTENT_PASSWORD.getMsg());
    }
  }

  private boolean isDuplicatedEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
