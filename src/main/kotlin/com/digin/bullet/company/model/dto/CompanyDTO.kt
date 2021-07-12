package com.digin.bullet.company.model.dto

import org.springframework.data.relational.core.mapping.Column

data class CompanyDTO(
    val id: Long,
    val stockCode: String,
    val shortName: String,
    val likeCount: Int = 0,
    val total: Int = 0,
    val searchCount: Int = 0,
    val imageUrl: String = "",
    val category: String = ""
)
