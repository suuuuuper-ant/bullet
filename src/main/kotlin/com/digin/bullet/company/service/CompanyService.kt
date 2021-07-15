package com.digin.bullet.company.service

import arrow.core.Either
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.model.http.response.CompanyDetailResponse
import com.digin.bullet.company.repository.CompanyRepository
import com.digin.bullet.consensus.service.ConsensusService
import com.digin.bullet.news.service.NewsService
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val newsService: NewsService,
    private val consensusService: ConsensusService
) {

    private val log = KotlinLogging.logger {}

    suspend fun getCompanyByStockCode(stockCode: String): Either<CompanyException, CompanyDetailResponse> {
        val company = companyRepository.getCompanyByStockCode(stockCode) ?: return Either.Left(CompanyException.NOT_FOUND_COMPANY)
        val companyDTO = company.toDTO(company)

        val newsList = newsService.getNewsByStockCode(stockCode)
        val newsDTOs = newsList.map { it.toDTO(it) }

        val consensusList = consensusService.getConsensusByStockCode(stockCode)
        val consensusDTOs = consensusList.map { it.toDTO(it) }

        return Either.Right(CompanyDetailResponse(
            company = companyDTO,
            news = newsDTOs,
            consensus = consensusDTOs
        ))
    }
}