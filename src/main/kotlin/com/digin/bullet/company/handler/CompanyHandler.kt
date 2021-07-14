package com.digin.bullet.company.handler

import arrow.core.Either
import com.digin.bullet.common.model.http.response.ErrorResponse
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.service.CompanyService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class CompanyHandler(
    private val companyService: CompanyService
) {

    suspend fun getCompany(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        return when (val result = companyService.getCompanyByStockCode(stockCode)) {
            is Either.Left -> when(result.value) {
                CompanyException.NOT_FOUND_COMPANY -> ServerResponse.badRequest().bodyValueAndAwait(ErrorResponse(result = result.value.name))
            }
            is Either.Right -> ServerResponse
                .ok()
                .bodyValueAndAwait(SuccessResponse(result = CompanyDTO(
                    id = result.value.id!!,
                    stockCode = result.value.stockCode,
                    shortName = result.value.shortName,
                    imageUrl = result.value.imageUrl ?: "",
                    likeCount = result.value.likeCount,
                )))

        }
    }
}