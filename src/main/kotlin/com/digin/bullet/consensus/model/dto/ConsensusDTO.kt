package com.digin.bullet.consensus.model.dto

import org.joda.time.LocalDateTime

data class ConsensusDTO(
    val id: Long,
    val stockCode: String,
    val opinion: String,
    val price: String,
    val createdAt: LocalDateTime
)
