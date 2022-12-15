package com.example.smilegateauthserver.user.repository;

import com.example.smilegateauthserver.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}
