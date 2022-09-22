package com.wangtak.member.application.dto;

import com.wangtak.member.domain.Member;

public class MemberResponse {

    private final Long memberId;
    private final String name;
    private final int age;

    private MemberResponse(Long memberId, String name, int age) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
    }

    public static MemberResponse toDto(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getAge());
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
