package com.wangtak.member.application.response;

import com.wangtak.member.application.dto.MemberDto;

import java.util.Collections;
import java.util.List;

public class MemberResponse {

    private final List<MemberDto> memberResponse;

    public MemberResponse(List<MemberDto> memberResponse) {
        this.memberResponse = memberResponse;
    }

    public List<MemberDto> getMemberResponse() {
        return Collections.unmodifiableList(memberResponse);
    }
}
