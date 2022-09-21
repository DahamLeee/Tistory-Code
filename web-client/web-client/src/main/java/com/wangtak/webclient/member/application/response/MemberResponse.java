package com.wangtak.webclient.member.application.response;

import com.wangtak.webclient.member.domain.ReceivedMember;

import java.util.List;

public class MemberResponse {

    private List<ReceivedMember> memberResponse;

    public MemberResponse() { }

    public MemberResponse(List<ReceivedMember> memberResponse) {
        this.memberResponse = memberResponse;
    }

    public List<ReceivedMember> getMemberResponse() {
        return memberResponse;
    }
}
