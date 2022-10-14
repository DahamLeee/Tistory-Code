package com.wangtak.n1.member.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.team")
    List<Member> findAllUsingFetchJoin();

    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m")
    List<Member> findAllUsingJPQLAndEntityGraph();

    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();
}
