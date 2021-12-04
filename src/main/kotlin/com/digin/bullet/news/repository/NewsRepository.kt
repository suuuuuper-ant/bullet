package com.digin.bullet.news.repository

import com.digin.bullet.news.domain.entity.News
import com.digin.bullet.news.model.dto.NewsDTO
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface NewsRepository : R2dbcRepository<News, Long> {
    fun findByStockCode(stockCode: String, pageable: Pageable): Flow<News>
    fun findByStockCodeIn(stockCodes: List<String>, pageable: Pageable): Flow<News>

    suspend fun findNewsById(id: Long): News

    fun findAllByOrderByUpdatedAtDesc(pageable: Pageable): Flow<News>
}