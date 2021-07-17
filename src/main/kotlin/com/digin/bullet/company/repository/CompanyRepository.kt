package com.digin.bullet.company.repository

import com.digin.bullet.common.util.defaultPageRequest
import com.digin.bullet.company.domain.entity.Company
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyRepository: R2dbcRepository<Company, Long> {

    suspend fun getCompanyByStockCode(stockCode: String): Company?
    suspend fun getCompaniesByStockCodeIn(stockCodes: List<String>): List<Company>

    fun getCompaniesByKrNameContaining(name: String, pageable: Pageable): Flow<Company>
}