package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    fun findByName(name: String): User?

    @EntityGraph(attributePaths = ["userLoanHistories"])
    @Query("select u from User u")
    fun findAllWithHistories(): List<User>
}