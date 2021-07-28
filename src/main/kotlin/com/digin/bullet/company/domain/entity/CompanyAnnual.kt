package com.digin.bullet.company.domain.entity

import com.digin.bullet.company.model.dto.CompanyAnnualDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("company_info_annual")
class CompanyAnnual(
    @Id
    val id: Long?,
    val stockCode: String,
    val date: String,
    val sales: String,
    val profit: String,
    val isExpect: Boolean
) {

    fun toDTO(): CompanyAnnualDTO {
        return CompanyAnnualDTO(
            id, stockCode, date, sales, profit, isExpect
        )
    }
}