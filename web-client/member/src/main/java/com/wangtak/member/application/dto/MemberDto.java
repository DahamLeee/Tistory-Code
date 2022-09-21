package com.wangtak.member.application.dto;

import com.wangtak.member.domain.Member;

public class MemberDto {

    private final Long memberId;
    private final String name;
    private final int age;

    private MemberDto(Long memberId, String name, int age) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getAge());
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
