package com.digin.bullet.market.model.dto

import org.joda.time.LocalDateTime

data class MarketStackDTO(
    var id: Long?,
    val stockCode: String,
    val open: Int,
    val close: Int,
    val high: Int,
    val low: Int,
    val date: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
