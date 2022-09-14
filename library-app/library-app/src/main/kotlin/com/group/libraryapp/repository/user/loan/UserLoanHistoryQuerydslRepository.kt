package com.group.libraryapp.repository.user.loan

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserLoanHistoryQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return queryFactory.select(userLoanHistory)
            .from(userLoanHistory)
            .where(
                equalBookName(bookName),
                status?.let { equalStatus(status) }
            )
            .limit(1)
            .fetchOne()
    }

    fun count(status: UserLoanStatus): Long {
        return queryFactory.select(userLoanHistory.id.count())
            .from(userLoanHistory)
            .where(
                equalStatus(status)
            )
            .fetchOne() ?: 0L
    }

    private fun equalBookName(bookName: String) = userLoanHistory.bookName.eq(bookName)

    private fun equalStatus(status: UserLoanStatus?) = userLoanHistory.status.eq(status)

}