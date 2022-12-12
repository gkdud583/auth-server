package com.example.smilegateauthserver.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smilegateauthserver.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
}