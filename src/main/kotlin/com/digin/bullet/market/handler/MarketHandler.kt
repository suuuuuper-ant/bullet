package com.digin.bullet.market.handler

import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.market.service.MarketStackService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class MarketHandler(
    private val marketStackService: MarketStackService
) {
    suspend fun getMarketStacksByStockCode(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        val pageRequest = getPageRequest(serverRequest)
        val marketStacks = marketStackService.getMarketStackByStockCode(stockCode = stockCode, pageable = pageRequest)

        return ServerResponse.ok().bodyValueAndAwait(
            SuccessResponse(
                result = marketStacks
            )
        )
    }
}