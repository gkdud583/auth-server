package com.example.smilegateauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.smilegateauthserver.common.auth.JwtProvider;

@SpringBootApplication
@EnableConfigurationProperties(JwtProvider.class)
public class SmilegateAuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SmilegateAuthServerApplication.class, args);
  }

}
