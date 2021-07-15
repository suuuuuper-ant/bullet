package com.digin.bullet.news.handler

import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.news.service.NewsService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class NewsHandler(
    private val newsService: NewsService
) {
    suspend fun getNewsByStockCode(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        val pageRequest = getPageRequest(serverRequest)
        val newsList = newsService.getNewsByStockCode(stockCode = stockCode, pageable = pageRequest)

        return ServerResponse.ok().bodyValueAndAwait(SuccessResponse(
            result = newsList.map { it.toDTO(it) }
        ))
    }
}