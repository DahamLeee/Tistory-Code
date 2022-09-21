package com.wangtak.webclient.member.application;

import com.wangtak.webclient.member.application.response.MemberResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MemberService {

    public MemberResponse members() {
        WebClient webClient = WebClient.create("http://127.0.0.1:8080");

        return webClient.get()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MemberResponse.class)
                .blockFirst();
    }
}
