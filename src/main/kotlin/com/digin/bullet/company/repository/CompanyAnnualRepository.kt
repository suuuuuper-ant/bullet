package com.digin.bullet.company.repository

import com.digin.bullet.company.domain.entity.CompanyAnnual
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyAnnualRepository: R2dbcRepository<CompanyAnnual, Long> {

    suspend fun findAllByStockCode(stockCode: String): List<CompanyAnnual>
}