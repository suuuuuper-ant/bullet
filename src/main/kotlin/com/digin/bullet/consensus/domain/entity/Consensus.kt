package com.digin.bullet.consensus.domain.entity

import com.digin.bullet.consensus.model.dto.ConsensusDTO
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table

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
            price = consensus.price,
            createdAt = consensus.createdAt
        )
    }

    override fun toString(): String {
        return "Consensus(id=$id, stockCode='$stockCode', name='$name', title='$title', opinion='$opinion', price='$price', writer='$writer', source='$source', chart='$chart', createdAt=$createdAt, updatedAt=$updatedAt)"
    }


}