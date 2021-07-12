package com.digin.bullet.common.model.http.response

data class SuccessResponse<T>(
    val status: String = "success",
    val result: T
)
