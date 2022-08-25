package com.wangtak.auditing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AuditingTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void dataJpaAuditingTest() throws Exception {
        String email = "wangtak@gmail.com";
        String name = "왕탁이";
        String password = "1234";

        Member member = Member.createMember(email, name, password);

        memberRepository.save(member);

    }
}
