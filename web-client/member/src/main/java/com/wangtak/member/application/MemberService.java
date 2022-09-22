package com.wangtak.member.application;

import com.wangtak.member.application.dto.MemberCreateRequest;
import com.wangtak.member.application.dto.MemberResponse;
import com.wangtak.member.application.dto.MemberResponses;
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

    public MemberResponses members() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberResponse::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), MemberResponses::new));
    }

    public MemberResponse createMember(MemberCreateRequest memberCreateRequest) {
        Member savedMember = memberRepository.save(Member.of(memberCreateRequest.getName(), memberCreateRequest.getAge()));
        return MemberResponse.toDto(savedMember);
    }
}
