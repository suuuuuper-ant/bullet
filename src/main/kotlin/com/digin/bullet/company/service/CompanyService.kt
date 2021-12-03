package com.digin.bullet.company.service

import arrow.core.Either
import com.digin.bullet.common.util.defaultPageRequest
import com.digin.bullet.company.domain.entity.Company
import com.digin.bullet.company.domain.entity.CompanyFavorite
import com.digin.bullet.company.model.dto.CompanyAnnualDTO
import com.digin.bullet.company.model.dto.CompanyDTO
import com.digin.bullet.company.model.dto.CompanyFavoriteDTO
import com.digin.bullet.company.model.dto.CompanyQuarterDTO
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.model.http.response.CompanyDetailResponse
import com.digin.bullet.company.repository.CompanyAnnualRepository
import com.digin.bullet.company.repository.CompanyFavoriteRepository
import com.digin.bullet.company.repository.CompanyQuarterRepository
import com.digin.bullet.company.repository.CompanyRepository
import com.digin.bullet.consensus.service.ConsensusService
import com.digin.bullet.news.service.NewsService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitLast
import mu.KotlinLogging
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyFavoriteRepository: CompanyFavoriteRepository,
    private val companyAnnualRepository: CompanyAnnualRepository,
    private val companyQuarterRepository: CompanyQuarterRepository,
    private val newsService: NewsService,
    private val consensusService: ConsensusService
) {

    private val log = KotlinLogging.logger {}


    suspend fun getCompanyByStockCode(stockCode: String): Either<CompanyException, CompanyDTO> {
        val company = companyRepository.getCompanyByStockCode(stockCode) ?: return Either.Left(CompanyException.NOT_FOUND_COMPANY)
        val companyDTO = company.toDTO(company)

       return Either.Right(companyDTO)
    }

    @Cacheable(cacheNames = ["company"])
    suspend fun getCompanyDetailByStockCode(stockCode: String): Either<CompanyException, CompanyDetailResponse> {
        val company = companyRepository.getCompanyByStockCode(stockCode) ?: return Either.Left(CompanyException.NOT_FOUND_COMPANY)
        val companyDTO = company.toDTO(company)

        val newsList = newsService.getNewsByStockCode(stockCode)
        val newsDTOs = newsList.map { it.toDTO(it) }

        val consensusList = consensusService.getConsensusByStockCode(stockCode)
        val consensusDTOs = consensusList.map { it.toDTO(it) }

        return Either.Right(
            CompanyDetailResponse(
                company = companyDTO,
                news = newsDTOs,
                consensus = consensusDTOs
            )
        )
    }

    suspend fun getCompaniesByName(name: String, pageable: Pageable): Either<CompanyException, List<CompanyDTO>> {
        val companies = companyRepository.getCompaniesByKrNameContaining(name = name, pageable = PageRequest.of(0, 5))
            .toList()
            .map { it.toDTO(it) }
        if (companies.isEmpty()) {
            return Either.Left(CompanyException.NOT_FOUND_COMPANY)
        }
        return Either.Right(companies)
    }

    suspend fun getCompaniesByIds(ids: List<Long>): Either.Right<List<CompanyDTO>> {
        val companies = companyRepository.findCompaniesByIdIn(ids).map { it.toDTO(it) }
        return Either.Right(companies)
    }

    suspend fun doFavoriteCompanyByStockCodes(accountId: Long, stockCodes: List<String>) {
        val companies = companyRepository.getCompaniesByStockCodeIn(stockCodes)
        val existsFavorites = companyFavoriteRepository.findAllByAccountIdAndCompanyIdInAndIsDeleted(
            accountId = accountId,
            companyIds = companies.map { it.id!! },
            isDeleted = false
        )
        val ids = existsFavorites.map { it.id }.toSet()
        val companyFavorites = companies
            .filterNot { ids.contains(it.id) }
            .map {
                CompanyFavorite(
                    companyId = it.id!!,
                    accountId = accountId,
                    isDeleted = false
                )
            }
        log.info { "favorites $companyFavorites" }
        companyFavoriteRepository.saveAll(companyFavorites).awaitLast()

        val incLikeCountCompany = companies.map {
            val count = it.likeCount
            it.likeCount = count.inc()
            it
        }


        log.info { "inc $incLikeCountCompany" }
        companyRepository.saveAll(incLikeCountCompany).awaitLast()

    }

    suspend fun cancelFavorites(accountId: Long, stockCodes: List<String>) {
        val companies = companyRepository.getCompaniesByStockCodeIn(stockCodes = stockCodes)

        val existsFavorites = companyFavoriteRepository.findAllByAccountIdAndCompanyIdInAndIsDeleted(
            accountId = accountId,
            companyIds = companies.map { it.id!! },
            isDeleted = false
        )
        val ids = existsFavorites.map { it.id!! }.toSet()
        val favorites = companyFavoriteRepository.findAllByIdIn(ids)

        companyFavoriteRepository.saveAll(favorites.map {
            it.isDeleted = true
            it
        })
            .awaitLast()
    }

    suspend fun getFavoriteCompanies(accountId: Long): Either.Right<List<CompanyFavoriteDTO>> {
        val favorites = companyFavoriteRepository.findAllByAccountIdAndIsDeleted(accountId = accountId, isDeleted = false, pageable = defaultPageRequest)
        return Either.Right(favorites.toList().map { it.toDTO(it) })
    }


    suspend fun getCompanyAnnuals(stockCode: String): Either.Right<List<CompanyAnnualDTO>> {
        val annuals = companyAnnualRepository.findAllByStockCode(stockCode = stockCode).map { it.toDTO() }
        return Either.Right(annuals)
    }

    suspend fun getCompanyQuarters(stockCode: String): Either.Right<List<CompanyQuarterDTO>> {
        val quarters = companyQuarterRepository.findAllByStockCode(stockCode).map { it.toDTO() }
        return Either.Right(quarters)
    }

    suspend fun getKospi200(): Either.Right<List<CompanyDTO>> {
        val companies = companyRepository.findCompaniesByIsKospi200(true)
        return Either.Right(companies.filter { it.imageUrl != null }.map { it.toDTO()}.shuffled())
    }
}