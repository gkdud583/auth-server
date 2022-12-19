package com.example.smilegateauthserver.common.config;

import com.example.smilegateauthserver.common.argumentResolver.LoginUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final LoginUserArgumentResolver loginUserArgumentResolver;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/users/register").setViewName("/users/register");
    registry.addViewController("/users/login").setViewName("/users/login");
    registry.addViewController("/users/list").setViewName("/users/list");

  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserArgumentResolver);
    WebMvcConfigurer.super.addArgumentResolvers(resolvers);
  }


}
