package com.wangtak.pagemarker.application;

import com.wangtak.pagemarker.application.dto.MemberDto;
import com.wangtak.pagemarker.domain.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberDto> searchMember(Pageable pageable) {
        return memberRepository.search(pageable);
    }

}
