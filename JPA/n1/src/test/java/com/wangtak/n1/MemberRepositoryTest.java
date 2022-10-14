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
@Transactional(readOnly = true)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 조회 시 추가적인 쿼리 발생 확인")
    void findAllTest() {
        List<Member> findMembers = memberRepository.findAll();

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getClass());
        });

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getName());
        });
    }

    @Test
    @DisplayName("fetch join 을 사용하여 멤버 조회")
    void findAllMemberUsingFetchJoinTest() {
        List<Member> findMembers = memberRepository.findAllUsingFetchJoin();

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getClass());
        });

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getName());
        });
    }

    @Test
    @DisplayName("Entity Graph + JPQL 을 사용하여 멤버 조회")
    void findAllMemberUsingEntityGraphAndJPQL() {
        List<Member> findMembers = memberRepository.findAllUsingJPQLAndEntityGraph();

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getClass());
        });

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getName());
        });
    }

    @Test
    @DisplayName("Spring Data JPA 에서 제공하는 기본 메서드에 EntityGraph 활용")
    void findAllMemberUsingEntityGraph() {
        List<Member> findMembers = memberRepository.findAll();

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getClass());
        });

        findMembers.forEach(member -> {
            System.out.println(member.getTeam().getName());
        });
    }
}
