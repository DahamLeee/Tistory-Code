package com.wangtak.pagemarker;

import com.wangtak.pagemarker.domain.Member;
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
        initService.addMembers();
    }

    @Component
    @Transactional
    static class InitService {

        @PersistenceContext
        private EntityManager em;

        public void addMembers() {
            for (int i = 1; i <= 100; i++) {
                em.persist(new Member("member" + i, i));
            }
        }
    }
}
