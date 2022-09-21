package com.wangtak.member.web;

import com.wangtak.member.application.MemberService;
import com.wangtak.member.application.response.MemberResponse;
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
