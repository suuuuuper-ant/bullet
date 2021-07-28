package com.digin.bullet.company.model.dto

data class CompanyQuarterDTO(
    val id: Long?,
    val stockCode: String,
    val date: String,
    val sales: String,
    val profit: String,
    val isExpect: Boolean
)
