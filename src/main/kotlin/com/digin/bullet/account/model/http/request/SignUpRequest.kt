package com.digin.bullet.account.model.http.request

data class SignUpRequest(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val roles: List<String> = listOf(),
    val favorites: List<String> = listOf()
)