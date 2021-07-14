package com.digin.bullet.news.model.dto

import java.time.LocalDateTime

data class NewsDTO(
    val id: Long,
    val stockCode: String,
    val title: String,
    val link: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
