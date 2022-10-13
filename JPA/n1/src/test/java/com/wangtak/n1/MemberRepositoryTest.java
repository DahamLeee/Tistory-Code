package com.wangtak.n1;

import com.wangtak.n1.member.domain.Member;
import com.wangtak.n1.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    @DisplayName("멤버 조회 시 추가적인 쿼리 발생 확인")
    @Transactional(readOnly = true)
    void findAllTest() {
        List<Member> findMembers = memberRepository.findAll();

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getClass());
        });

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getName());
        });
    }
}
