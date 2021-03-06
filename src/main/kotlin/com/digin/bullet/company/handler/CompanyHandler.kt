package com.digin.bullet.company.handler

import arrow.core.*
import com.digin.bullet.common.model.http.response.ErrorResponse
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.common.util.getPageRequestWithoutSort
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.model.http.response.CompanyDetailResponse
import com.digin.bullet.company.service.CompanyService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class CompanyHandler(
    private val companyService: CompanyService
) {
    private val log = KotlinLogging.logger {}

    suspend fun getSimples(serverRequest: ServerRequest): ServerResponse {
        val result = companyService.getKospi200()
        return when (result) {
            else -> ServerResponse
                    .ok()
                    .bodyValueAndAwait(SuccessResponse(result = result.getOrElse { listOf() }))
        }
    }

    suspend fun getCompany(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        return when (val result = companyService.getCompanyDetailByStockCode(stockCode)) {
            is Either.Left -> ServerResponse
                .badRequest()
                .bodyValueAndAwait(ErrorResponse(result = result.getOrHandle { it.name }))
            is Either.Right ->
                ServerResponse
                    .ok()
                    .bodyValueAndAwait(SuccessResponse(result = result.getOrElse {  }))
        }
    }

    suspend fun searchCompaniesByName(serverRequest: ServerRequest): ServerResponse {
        val keyword = serverRequest.queryParamOrNull("keyword") ?: return ServerResponse.badRequest().bodyValueAndAwait(CompanyException.INPUT_EMPTY)
        val companies = companyService.getCompaniesByName(name = keyword, pageable = getPageRequestWithoutSort(serverRequest))
        return when(companies) {
            is Either.Left -> ServerResponse.ok().bodyValueAndAwait(SuccessResponse(result = listOf("")))
            is Either.Right -> ServerResponse.ok().bodyValueAndAwait(SuccessResponse(result = companies.getOrElse {  }))
        }
    }


    suspend fun doFavorites(serverRequest: ServerRequest): ServerResponse {
        val accountId = serverRequest.awaitPrincipal()!!.name.toLong()
        val body = serverRequest.awaitBodyOrNull<CompanyFavorites>() ?: return ServerResponse.badRequest().bodyValueAndAwait(CompanyException.INPUT_EMPTY)

        companyService.doFavoriteCompanyByStockCodes(accountId = accountId, stockCodes =  body.stockCodes)

        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun cancelFavorites(serverRequest: ServerRequest): ServerResponse {
        val accountId = serverRequest.awaitPrincipal()!!.name.toLong()
        val stockCodes = serverRequest.queryParamOrNull("stockCodes") ?: return ServerResponse.badRequest().bodyValueAndAwait(CompanyException.INPUT_EMPTY)

        companyService.cancelFavorites(accountId, stockCodes.split(","))

        return ServerResponse.ok().buildAndAwait()
    }
    data class CompanyFavorites(
        val stockCodes: List<String>
    )
}