package com.digin.bullet.news.handler

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrHandle
import com.digin.bullet.common.model.http.response.ErrorResponse
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.company.service.CompanyService
import com.digin.bullet.news.service.NewsService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class NewsHandler(
    private val newsService: NewsService,
    private val companyService: CompanyService
) {

    private val log = KotlinLogging.logger {}

    suspend fun searchNewsByCompanyName(serverRequest: ServerRequest): ServerResponse {
        val pageRequest = getPageRequest(serverRequest)
        val keyword = serverRequest.queryParamOrNull("keyword") ?: ""

        val companies = companyService.getCompaniesByName(name = keyword, pageRequest)
        val stockCodes = companies.map { it.map { it.stockCode } }.getOrHandle {
            return ServerResponse.badRequest().bodyValueAndAwait(ErrorResponse(result = it.name))
        }
        val newsList = newsService.searchNewsByStockCodes(stockCodes = stockCodes, pageable = pageRequest)

        return ServerResponse.ok().bodyValueAndAwait(SuccessResponse(result = newsList))
    }


    suspend fun getNewsByStockCode(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        val pageRequest = getPageRequest(serverRequest)
        val newsList = newsService.getNewsByStockCode(stockCode = stockCode, pageable = pageRequest)

        return ServerResponse.ok().bodyValueAndAwait(SuccessResponse(
            result = newsList.map { it.toDTO(it) }
        ))
    }
}