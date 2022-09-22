package com.wangtak.member.application.dto;

import java.util.Collections;
import java.util.List;

public class MemberResponses {

    private final List<MemberResponse> memberResponse;

    public MemberResponses(List<MemberResponse> memberResponse) {
        this.memberResponse = memberResponse;
    }

    public List<MemberResponse> getMemberResponse() {
        return Collections.unmodifiableList(memberResponse);
    }
}
