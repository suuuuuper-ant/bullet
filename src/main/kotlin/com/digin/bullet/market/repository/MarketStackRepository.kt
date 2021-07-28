package com.digin.bullet.market.repository

import com.digin.bullet.common.util.defaultPageRequest
import com.digin.bullet.market.domain.entity.MarketStack
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface MarketStackRepository : R2dbcRepository<MarketStack, Long> {
    fun findAllByStockCode(stockCode: String, pageable: Pageable = defaultPageRequest): Flow<MarketStack>
}