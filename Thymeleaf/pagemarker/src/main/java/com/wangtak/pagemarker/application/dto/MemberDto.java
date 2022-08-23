package com.wangtak.pagemarker.application.dto;

import com.querydsl.core.annotations.QueryProjection;

public class MemberDto {

    private Long memberId;
    private String name;
    private int age;

    @QueryProjection
    public MemberDto(Long memberId, String name, int age) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
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
