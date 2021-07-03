package com.digin.bullet.account.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
class Account(
    id: Long? = null,
    email: String,
    name: String,
    password: String,
    role: String,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    var id: Long? = id
    var email: String = email
    var name: String = name
    var password: String = password
    var role: String = role
    var createdAt: LocalDateTime = createdAt
    var updatedAt: LocalDateTime = updatedAt

    override fun toString(): String {
        return "Account(id=$id, email='$email', name='$name', password='$password', role='$role', createdAt=$createdAt, updatedAt=$updatedAt)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}