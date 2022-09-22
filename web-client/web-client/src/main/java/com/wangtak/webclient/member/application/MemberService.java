package com.wangtak.webclient.member.application;

import com.wangtak.webclient.member.application.dto.MemberCreateRequest;
import com.wangtak.webclient.member.application.dto.MemberResponse;
import com.wangtak.webclient.member.application.dto.ReceivedMember;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MemberService {

    private final WebClient webClient;

    public MemberService(WebClient webClient) {
        this.webClient = webClient;
    }

    public MemberResponse members() {
        return webClient.get()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MemberResponse.class)
                .blockFirst();
    }

    public ReceivedMember createMember(MemberCreateRequest memberCreateRequest) {
        return webClient.post()
                .uri("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberCreateRequest)
                .retrieve()
                .bodyToMono(ReceivedMember.class)
                .block();
    }
}
