package com.digin.bullet.account.model.http.response

data class SignUpResponse(
    val id: Long,
    val email: String,
    val name: String,
    val favorites: List<String>
) {
}