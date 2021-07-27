package com.digin.bullet.api.handler

import arrow.core.flatMap
import arrow.core.getOrElse
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.api.model.http.response.*
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.company.service.CompanyService
import com.digin.bullet.consensus.service.ConsensusService
import com.digin.bullet.news.service.NewsService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class HomeHandler(
    private val accountService: AccountService,
    private val companyService: CompanyService,
    private val newsService: NewsService,
    private val consensusService: ConsensusService
) {
    private val log = KotlinLogging.logger {}

    suspend fun getHome(serverRequest: ServerRequest): ServerResponse {
        val accountId = serverRequest.awaitPrincipal()!!.name.toLong()

        val favorites = companyService.getFavoriteCompanies(accountId = accountId)
        val favoriteIds = favorites.getOrElse { listOf() }.map { it.companyId }

        val companies = companyService.getCompaniesByIds(ids = favoriteIds).getOrElse { listOf() }
        val stockCodes = companies.map { it.stockCode }
        val newsList = newsService.searchNewsByStockCodes(stockCodes = stockCodes)
        val consensuses = consensusService.getConsensusByStockCodes(stockCodes = stockCodes)

        val companyByStockCode = companies.groupBy { it.stockCode }
        val newsByStockCode = newsList.groupBy { it.stockCode }
        val consensusByStockCode = consensuses.groupBy { it.stockCode }

        val companyGroupContents = stockCodes.map {
            GroupContent(
                company = companyByStockCode.getOrDefault(it, listOf()).first(),
                consensusList = consensusByStockCode.getOrDefault(it, listOf()),
                newsList = newsByStockCode.getOrDefault(it, listOf())
            )
        }

        val favoritesGroupContents = companies.map {
            GroupContent(
                company = it,
            )
        }

        val companySlideGroup = Group(
            type = GroupType.COMPANY,
            action = GroupAction.SLIDE,
            contents = companyGroupContents
        )

        val favoritesListGroup = Group(
            type = GroupType.FAVORITES,
            action = GroupAction.LIST,
            contents = favoritesGroupContents
        )


        return ServerResponse.ok()
            .bodyValueAndAwait(
                SuccessResponse(
                    result = HomeResponse(
                        groups = listOf(companySlideGroup, favoritesListGroup)
                    )
                )
            )
    }
}