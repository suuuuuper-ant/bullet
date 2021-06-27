package com.digin.bullet.account.entitiy

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
data class Account(
    @Id
    var id: Long?,
    @Column
    val email: String,
    @Column
    val name: String,
    @Column
    private val password: String,
    @Column
    val role: String,
    @Column
    val createdAt: LocalDateTime,
    @Column
    val updatedAt: LocalDateTime
)