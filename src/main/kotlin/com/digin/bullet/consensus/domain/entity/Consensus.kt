package com.digin.bullet.consensus.domain.entity

import com.digin.bullet.consensus.model.dto.ConsensusDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("consensus")
class Consensus(
    @Id
    var id: Long?,
    val stockCode: String,
    val name: String,
    val title: String,
    val opinion: String,
    val price: String,
    val writer: String,
    val source: String,
    val chart: String,
    @CreatedDate
    val createdAt: LocalDateTime,
    @LastModifiedDate
    val updatedAt: LocalDateTime
) {
    fun toDTO(consensus: Consensus): ConsensusDTO {
        return ConsensusDTO(
            id = consensus.id!!,
            stockCode = consensus.stockCode,
            opinion = consensus.opinion,
            price = consensus.price
        )
    }
}