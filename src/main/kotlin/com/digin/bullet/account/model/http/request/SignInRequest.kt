package com.digin.bullet.account.model.http.request

data class SignInRequest(
    val email: String,
    val password: String
)
