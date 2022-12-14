package com.example.smilegateauthserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @NotBlank
  @Size(max = 255)
  @Column(unique = true)
  private String email;

  @NotNull
  @NotBlank
  @Size(max = 255)
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder(access = AccessLevel.PRIVATE)
  private User(
    String email,
    String password,
    Role role
  ) {
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public static User of(String email, String password, Role role) {
    return User.builder()
               .email(email)
               .password(password)
               .role(role)
               .build();
  }

  @RequiredArgsConstructor
  @Getter
  public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String name;
  }
}
