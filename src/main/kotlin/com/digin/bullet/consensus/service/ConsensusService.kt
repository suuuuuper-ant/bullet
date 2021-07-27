package com.digin.bullet.consensus.service

import com.digin.bullet.consensus.domain.entity.Consensus
import com.digin.bullet.consensus.model.dto.ConsensusDTO
import com.digin.bullet.consensus.repository.ConsensusRepository
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ConsensusService(
    private val consensusRepository: ConsensusRepository
) {

    suspend fun getConsensusByStockCode(stockCode: String): List<Consensus> {
        val pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
        return consensusRepository.findByStockCode(pageable, stockCode).toList()
    }

    suspend fun getConsensusByStockCodes(stockCodes: List<String>): List<ConsensusDTO> {
        return stockCodes.flatMap { getConsensusByStockCode(it) }.map { it.toDTO(it) }
    }
}