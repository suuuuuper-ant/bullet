package com.digin.bullet.company.repository

import com.digin.bullet.company.domain.entity.CompanyQuarter
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyQuarterRepository : R2dbcRepository<CompanyQuarter, Long> {
    suspend fun findAllByStockCode(stockCode: String): List<CompanyQuarter>
}