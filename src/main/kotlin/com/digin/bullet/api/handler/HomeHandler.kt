package com.digin.bullet.api.handler

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.api.model.http.response.*
import com.digin.bullet.common.model.http.response.SuccessResponse
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.service.CompanyService
import com.digin.bullet.consensus.service.ConsensusService
import com.digin.bullet.market.service.MarketStackService
import com.digin.bullet.news.service.NewsService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class HomeHandler(
        private val accountService: AccountService,
        private val companyService: CompanyService,
        private val newsService: NewsService,
        private val consensusService: ConsensusService,
        private val marketStackService: MarketStackService
) {
    private val log = KotlinLogging.logger {}

    suspend fun getHome(serverRequest: ServerRequest): ServerResponse {
        val accountId = serverRequest.awaitPrincipal()!!.name.toLong()

        val favorites = companyService.getFavoriteCompanies(accountId = accountId)
        val favoriteIds = favorites.getOrElse { listOf() }.map { it.companyId }

        val companies = companyService.getCompaniesByIds(ids = favoriteIds).getOrElse { listOf() }
        val stockCodes = companies.map { it.stockCode }
        val newsList = newsService.getNewsByStockCodes(stockCodes = stockCodes)
        val consensuses = consensusService.getConsensusByStockCodes(stockCodes = stockCodes)

        val companyByStockCode = companies.groupBy { it.stockCode }
        val newsByStockCode = newsList.groupBy { it.stockCode }
        val consensusByStockCode = consensuses.groupBy { it.stockCode }


        val companyGroupContents = stockCodes.map {
            GroupContent(
                    type = GroupType.TYPE,
                    items = listOf(
                            GroupItem(
                                    company = companyByStockCode.getOrDefault(it, listOf()).first(),
                                    consensusList = consensusByStockCode.getOrDefault(it, listOf()),
                                    newsList = newsByStockCode.getOrDefault(it, listOf())
                            )
                    )
            )
        }

        val favoritesGroupContents = companies.map {
            GroupContent(
                    type = GroupType.TYPE,
                    items = listOf(GroupItem(
                            company = it,
                    ))
            )
        }

        val companySlideGroup = Group(
                section = GroupSection.COMPANY,
                header = GroupHeader.SLIDE,
                contents = companyGroupContents
        )

        val favoritesListGroup = Group(
                section = GroupSection.FAVORITES,
                header = GroupHeader.LIST,
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


    suspend fun getHomeByStockCode(serverRequest: ServerRequest): ServerResponse {
        val stockCode = serverRequest.pathVariable("stockCode")
        val pageRequest = getPageRequest(serverRequest)
        val company = companyService.getCompanyByStockCode(stockCode).getOrElse { null }
                ?: return ServerResponse.badRequest().bodyValueAndAwait(CompanyException.NOT_FOUND_COMPANY)
        val consensus = consensusService.getConsensusByStockCodes(listOf(stockCode))
        val newsList = newsService.getNewsByStockCode(stockCode).map { it.toDTO(it) }
        val annuals = companyService.getCompanyAnnuals(stockCode).getOrElse { listOf() }
        val quarters = companyService.getCompanyQuarters(stockCode).getOrElse { listOf() }
        val marketStacks = marketStackService.getMarketStackByStockCode(stockCode = stockCode, pageable = pageRequest)

        return ServerResponse.ok().bodyValueAndAwait(
                SuccessResponse(
                        result = HomeDetailResponse(
                                company = company,
                                consensusList = consensus,
                                newsList = newsList,
                                stacks = marketStacks,
                                annuals = annuals,
                                quarters = quarters
                        )
                )
        )
    }
}