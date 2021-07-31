package com.digin.bullet.account.model.http.response

import org.joda.time.LocalDateTime


data class SuccessResponse<T>(
    val status: String = "success",
    val result: T
)


data class AccountDTO(
    val id: Long,
    val email: String,
    val name: String,
    val favorites: List<Long> = listOf(),
    val createdAt: LocalDateTime
)
