package com.wangtak.n1.team.domain;

import com.wangtak.n1.member.domain.Member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    protected Team() { }

    private Team(String name) {
        this.name = name;
    }

    public static Team from(String name) {
        return new Team(name);
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public String getName() {
        return name;
    }
}
