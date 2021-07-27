package com.digin.bullet.news.domain.entity

import com.digin.bullet.news.model.dto.NewsDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("news")
class News(
    @Id var id: Long? = null,
    val stockCode: String,
    val title: String,
    val link: String,
    val description: String,
    @CreatedDate
    val createdAt: LocalDateTime,
    @LastModifiedDate
    val updatedAt: LocalDateTime
) {
    fun toDTO(news: News): NewsDTO {
        return NewsDTO(
            id = news.id!!,
            stockCode = news.stockCode,
            title = news.title,
            link = news.link,
            description = news.description,
            createdAt = news.createdAt,
        )
    }
}

