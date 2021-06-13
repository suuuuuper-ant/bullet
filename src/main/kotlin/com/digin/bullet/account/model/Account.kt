package com.digin.bullet.account.model

data class Account(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val roles: List<String> = listOf()
)