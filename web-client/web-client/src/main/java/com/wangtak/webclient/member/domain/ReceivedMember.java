package com.wangtak.webclient.member.domain;

public class ReceivedMember {

    private Long memberId;
    private String name;
    private int age;

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "ReceivedMember{" +
                "id=" + memberId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
