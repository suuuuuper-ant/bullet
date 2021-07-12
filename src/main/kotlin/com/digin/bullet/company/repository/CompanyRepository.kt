package com.digin.bullet.company.repository

import com.digin.bullet.company.domain.entity.Company
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyRepository: R2dbcRepository<Company, Long> {

    suspend fun getCompanyByStockCode(stockCode: String): Company?
}