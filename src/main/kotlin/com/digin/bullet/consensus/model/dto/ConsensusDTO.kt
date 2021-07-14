package com.digin.bullet.consensus.model.dto

data class ConsensusDTO(
    val id: Long,
    val stockCode: String,
    val opinion: String,
    val price: String
)
