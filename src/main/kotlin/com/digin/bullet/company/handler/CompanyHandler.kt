package com.digin.bullet.company.handler

import arrow.core.*
import com.digin.bullet.common.model.http.response.ErrorResponse
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.service.CompanyService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import kotlin.math.log

@Component
class CompanyHandler(
    private val companyService: CompanyService
) {
    private val log = KotlinLogging.logger {}

    suspend fun getCompany(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        return when (val result = companyService.getCompanyByStockCode(stockCode)) {
            is Either.Left -> ServerResponse
                .badRequest()
                .bodyValueAndAwait(ErrorResponse(result = result.getOrHandle { it.name }))
            is Either.Right -> {
                val companyDTO = result.map {
                    CompanyDTO(
                        id = it.id!!,
                        stockCode = it.stockCode,
                        shortName = it.shortName,
                        imageUrl = it.imageUrl ?: "",
                        likeCount = it.likeCount,
                    )
                }
                ServerResponse
                    .ok()
                    .bodyValueAndAwait(SuccessResponse(result = companyDTO))
            }

        }
    }
}