package com.digin.bullet.account.model.dto

data class SignUpDTO(
    val email: String,
    val password: String,
    val name: String,
    val favorites: List<String>
)
