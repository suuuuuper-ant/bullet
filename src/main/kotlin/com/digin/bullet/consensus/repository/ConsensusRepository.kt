package com.digin.bullet.consensus.repository

import com.digin.bullet.consensus.domain.entity.Consensus
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface ConsensusRepository : R2dbcRepository<Consensus, Long> {
    fun findByStockCode(pageable: Pageable, stockCode: String): Flow<Consensus>
}