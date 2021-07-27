package com.digin.bullet.company.model.dto

import org.joda.time.LocalDateTime


data class CompanyFavoriteDTO(
    val id: Long,
    val companyId: Long,
    val accountId: Long,
    val updatedAt: LocalDateTime
)
