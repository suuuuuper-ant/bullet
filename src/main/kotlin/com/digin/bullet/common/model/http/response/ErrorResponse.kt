package com.digin.bullet.common.model.http.response

data class ErrorResponse(
    val status: String = "error",
    val result: String
)
