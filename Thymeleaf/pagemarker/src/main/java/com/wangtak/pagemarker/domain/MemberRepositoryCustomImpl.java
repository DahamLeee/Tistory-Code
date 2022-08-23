package com.wangtak.pagemarker.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wangtak.pagemarker.application.dto.MemberDto;
import com.wangtak.pagemarker.application.dto.QMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.wangtak.pagemarker.domain.QMember.member;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<MemberDto> search(Pageable pageable) {
        List<MemberDto> contents = queryFactory
                .select(new QMemberDto(member.id, member.name, member.age))
                .from(member)
                .offset(pageable.getOffset())
//                .where(memberNameEqual(memberParameter))
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member);

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private BooleanExpression memberNameEqual(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        return member.name.eq(memberName);
    }
}
