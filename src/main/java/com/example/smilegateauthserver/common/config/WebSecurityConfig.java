package com.example.smilegateauthserver.common.config;

import com.example.smilegateauthserver.common.auth.JwtFilter;
import com.example.smilegateauthserver.common.auth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtProvider jwtProvider;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, "/api/v1/users/register")
        .permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/users/login")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/users")
        .hasRole("ADMIN")
        .anyRequest()
        .hasAnyRole("USER", "ADMIN")
        .and()
        .headers()
        .disable()
        .formLogin()
        .disable()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .rememberMe()
        .disable()
        .logout()
        .disable()
        .sessionManagement()
        .disable()
        .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
