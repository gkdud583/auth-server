package com.example.smilegateauthserver.user.service;

import static com.example.smilegateauthserver.user.exception.ExceptionMessage.NOTFOUND_USER;

import com.example.smilegateauthserver.common.auth.JwtProvider;
import com.example.smilegateauthserver.common.auth.TokenStore;
import com.example.smilegateauthserver.user.User;
import com.example.smilegateauthserver.user.User.Role;
import com.example.smilegateauthserver.user.controller.dto.LoginRequest;
import com.example.smilegateauthserver.user.controller.dto.RegisterRequest;
import com.example.smilegateauthserver.user.exception.ExceptionMessage;
import com.example.smilegateauthserver.user.repository.UserRepository;
import com.example.smilegateauthserver.user.service.dto.TokenResponse;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtProvider jwtProvider;

  private final TokenStore tokenStore;

  @Override
  @Transactional
  public void register(RegisterRequest request) {
    if (isDuplicatedEmail(request.getEmail())) {
      throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_EMAIL.getMsg());
    }
    User user = User.of(request.getEmail(), passwordEncoder.encode(request.getPassword()),
      Role.USER);

    userRepository.save(user);
  }

  @Override
  public TokenResponse login(LoginRequest request) {
    User user = userRepository
      .findByEmail(request.getEmail())
      .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NONEXISTENT_EMAIL.getMsg()));

    validatePassword(request.getPassword(), user.getPassword());

    String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
    String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getRole());

    tokenStore.set(user.getId(), refreshToken);
    return TokenResponse.of(accessToken, refreshToken);
  }

  @Override
  public void logout(long userId) {
    tokenStore.remove(userId);
  }

  @Override
  public TokenResponse refreshToken(String token) {
    jwtProvider.validateToken(token);
    Authentication authentication = jwtProvider.getAuthentication(token);
    long userId = Long.valueOf(String.valueOf(authentication.getPrincipal()));
    if (!userRepository.existsById(userId)) {
      throw new IllegalArgumentException(NOTFOUND_USER.getMsg());
    }

    String accessToken = jwtProvider.generateAccessToken(userId, Role.USER);
    String refreshToken = jwtProvider.generateRefreshToken(userId, Role.USER);

    tokenStore.set(userId, refreshToken);
    return TokenResponse.of(accessToken, refreshToken);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  private void validatePassword(String password, String encodedPassword) {
    if (!passwordEncoder.matches(password, encodedPassword)) {
      throw new IllegalArgumentException(ExceptionMessage.INCONSISTENT_PASSWORD.getMsg());
    }
  }

  private boolean isDuplicatedEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @PostConstruct
  public void createAdmin() {
    User user = User.of("admin", passwordEncoder.encode("admin123"), Role.ADMIN);
    userRepository.save(user);
  }
}
