package com.digin.bullet.company.domain.entity

import com.digin.bullet.company.model.dto.CompanyQuarterDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("company_info_quarter")
class CompanyQuarter(
    @Id
    var id: Long?,
    val stockCode: String,
    val date: String,
    val sales: String,
    val profit: String,
    val isExpect: Boolean
) {
    fun toDTO(): CompanyQuarterDTO {
        return CompanyQuarterDTO(
            id, stockCode, date, sales, profit, isExpect
        )
    }
}