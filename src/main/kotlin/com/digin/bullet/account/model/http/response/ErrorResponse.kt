package com.digin.bullet.account.model.http.response

data class ErrorResponse(
    val status: String = "error",
    val result: String
)
