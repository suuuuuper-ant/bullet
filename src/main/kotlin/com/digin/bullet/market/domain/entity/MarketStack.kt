package com.digin.bullet.market.domain.entity

import com.digin.bullet.market.model.dto.MarketStackDTO
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("market_stack")
class MarketStack(
    @Id
    var id: Long?,
    val stockCode: String,
    val open: Int,
    val close: Int,
    val high: Int,
    val low: Int,
    val date: LocalDateTime,
    @Column("created_at")
    val createdAt: LocalDateTime,
    @Column("updated_at")
    val updatedAt: LocalDateTime
) {

    fun toDTO(): MarketStackDTO {
        return MarketStackDTO(
            id, stockCode, open, close, high, low, date, createdAt, updatedAt
        )
    }
}