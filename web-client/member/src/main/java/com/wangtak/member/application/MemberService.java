package com.wangtak.member.application;

import com.wangtak.member.application.dto.MemberDto;
import com.wangtak.member.application.response.MemberResponse;
import com.wangtak.member.domain.Member;
import com.wangtak.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse members() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberDto::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), MemberResponse::new));
    }
}
