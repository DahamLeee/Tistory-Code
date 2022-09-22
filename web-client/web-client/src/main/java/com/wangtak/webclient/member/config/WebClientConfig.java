package com.wangtak.webclient.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String MEMBER_API_SERVER = "http://127.0.0.1:8080"; // 암호화 ??

    @Bean
    public WebClient webClient() {
        return WebClient.create(MEMBER_API_SERVER);
    }
}
