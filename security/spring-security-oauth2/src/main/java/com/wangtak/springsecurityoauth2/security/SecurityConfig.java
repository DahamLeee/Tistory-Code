package com.wangtak.springsecurityoauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .apply(customSecurityConfigurer());
        return http.build();
    }

    @Bean
    public CustomSecurityConfigurer customSecurityConfigurer() {
        CustomSecurityConfigurer customSecurityConfigurer = new CustomSecurityConfigurer();
        customSecurityConfigurer.setFlag(false);
        return customSecurityConfigurer;
    }
}
