package com.example.smilegateauthserver.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Builder
  public User(
      String email,
      String password
  ) {
    this.email = email;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
