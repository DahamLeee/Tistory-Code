package com.wangtak.n1.util;

import com.wangtak.n1.member.domain.Member;
import com.wangtak.n1.team.domain.Team;
import org.springframework.context.annotation.Profile;
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
    public void initData() {
        initService.addData();
    }

    @Component
    @Transactional
    public static class InitService {

        @PersistenceContext
        private EntityManager em;

        public void addData() {
            Team team1 = Team.from("team1");
            Team team2 = Team.from("team2");
            Team team3 = Team.from("team3");

            em.persist(team1);
            em.persist(team2);
            em.persist(team3);

            Member member1 = Member.from("member1");
            Member member2 = Member.from("member2");
            Member member3 = Member.from("member3");
            Member member4 = Member.from("member4");
            Member member5 = Member.from("member5");

            member1.joinTeam(team1);
            member2.joinTeam(team1);
            member3.joinTeam(team2);
            member4.joinTeam(team3);
            member5.joinTeam(team3);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
        }
    }

}
