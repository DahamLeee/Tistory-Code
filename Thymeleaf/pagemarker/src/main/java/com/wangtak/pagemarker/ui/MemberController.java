package com.wangtak.pagemarker.ui;

import com.wangtak.pagemarker.application.MemberService;
import com.wangtak.pagemarker.application.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String members(@PageableDefault Pageable pageable, Model model) {
        Page<MemberDto> members = memberService.searchMember(pageable);

        model.addAttribute("members", members);

        return "member/members";
    }
}
