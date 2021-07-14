package com.digin.bullet.news.service

import com.digin.bullet.news.domain.entity.News
import com.digin.bullet.news.repository.NewsRepository
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {

    private val log = KotlinLogging.logger {}
    suspend fun getNewsByStockCode(stockCode: String): List<News> {
        val pageable = PageRequest.ofSize(5)
        return newsRepository.findByStockCode(stockCode, pageable).toList()
    }
}