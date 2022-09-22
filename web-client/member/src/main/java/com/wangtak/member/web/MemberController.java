package com.wangtak.member.web;

import com.wangtak.member.application.MemberService;
import com.wangtak.member.application.dto.MemberCreateRequest;
import com.wangtak.member.application.dto.MemberResponse;
import com.wangtak.member.application.dto.MemberResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<MemberResponses> members() {
        return ResponseEntity.ok(memberService.members());
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        MemberResponse memberResponse = memberService.createMember(memberCreateRequest);
        return ResponseEntity.created(URI.create("/members/" + memberResponse.getMemberId())).body(memberResponse);
    }
}
