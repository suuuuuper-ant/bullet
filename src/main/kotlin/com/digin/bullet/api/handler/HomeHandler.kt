package com.digin.bullet.api.handler

import arrow.core.flatMap
import arrow.core.getOrElse
import com.digin.bullet.account.service.AccountService
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

        log.info { "favoriteIds : $favoriteIds"  }
        val companies = companyService.getCompaniesByIds(ids = favoriteIds).getOrElse { listOf() }
        val stockCodes = companies.map { it.stockCode }
        val newsList = newsService.searchNewsByStockCodes(stockCodes = stockCodes)
        val consensuses = consensusService.getConsensusByStockCodes(stockCodes = stockCodes)


        log.info { "accountId: $accountId" }
        log.info { "stockCodes : $stockCodes" }
        log.info { "consensuses $consensuses" }
        return ServerResponse.ok().bodyValueAndAwait(consensuses)
    }
}