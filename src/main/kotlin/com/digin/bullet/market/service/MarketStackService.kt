package com.digin.bullet.market.service

import com.digin.bullet.market.model.dto.MarketStackDTO
import com.digin.bullet.market.repository.MarketStackRepository
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MarketStackService(
    private val marketStackRepository: MarketStackRepository
) {

    suspend fun getMarketStackByStockCode(stockCode: String, pageable: Pageable): List<MarketStackDTO> {
        val marketStacks = marketStackRepository.findAllByStockCode(stockCode)
            .toList()
            .map { it.toDTO() }

        return marketStacks
    }
}