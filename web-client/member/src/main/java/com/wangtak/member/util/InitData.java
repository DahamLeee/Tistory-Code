package com.wangtak.member.util;

import com.wangtak.member.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class InitData {

    private final InitService initService;

    public InitData(InitService initService) {
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        initService.initMembers();
    }

    @Component
    @Transactional
    static class InitService {

        @PersistenceContext
        private EntityManager em;

        public void initMembers() {
            for (int i = 1; i <= 20; i++) {
                em.persist(Member.of("관리자" + i, i));
            }
        }
    }
}
