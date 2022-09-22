package com.wangtak.webclient.member.web;

import com.wangtak.webclient.member.application.MemberService;
import com.wangtak.webclient.member.application.dto.MemberCreateRequest;
import com.wangtak.webclient.member.application.dto.MemberResponse;
import com.wangtak.webclient.member.application.dto.ReceivedMember;
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
    public ResponseEntity<MemberResponse> members() {
        return ResponseEntity.ok(memberService.members());
    }

    @PostMapping("/members")
    public ResponseEntity<ReceivedMember> createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        ReceivedMember receivedMember = memberService.createMember(memberCreateRequest);
        return ResponseEntity.created(URI.create("/members/" + receivedMember.getMemberId())).body(receivedMember);
    }
}
