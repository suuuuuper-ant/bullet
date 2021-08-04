package com.digin.bullet.news.service

import arrow.core.Either
import arrow.core.flatMap
import com.digin.bullet.common.util.defaultPageRequest
import com.digin.bullet.common.util.getPageRequest
import com.digin.bullet.company.service.CompanyService
import com.digin.bullet.news.domain.entity.News
import com.digin.bullet.news.model.dto.NewsDTO
import com.digin.bullet.news.repository.NewsRepository
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {

    private val log = KotlinLogging.logger {}


    suspend fun getNewsByStockCode(stockCode: String, pageable: Pageable = defaultPageRequest): List<News> {
        return newsRepository.findByStockCode(stockCode, pageable).toList()
    }

    suspend fun getNewsByStockCodes(stockCodes: List<String>): List<NewsDTO> {
        return stockCodes.flatMap { getNewsByStockCode(it) }.map { it.toDTO(it) }
    }

    suspend fun searchNewsByStockCodes(stockCodes: List<String>, pageable: Pageable = defaultPageRequest): List<NewsDTO> {
        val pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "updatedAt"))
        return newsRepository.findByStockCodeIn(stockCodes, pageRequest)
            .toList()
            .map {
                it.toDTO(it)
            }
    }
}
