package com.wangtak.webclient.member.web;

import com.wangtak.webclient.member.application.MemberService;
import com.wangtak.webclient.member.application.response.MemberResponse;
import com.wangtak.webclient.member.domain.ReceivedMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
