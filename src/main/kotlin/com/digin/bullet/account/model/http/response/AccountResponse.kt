package com.digin.bullet.account.model.http.response

data class AccountResponse(
    val status: String = "success",
    val result: AccountDTO
)


data class AccountDTO(
    val id: Long,
    val email: String
)
