package com.digin.bullet.company.repository

import com.digin.bullet.common.util.defaultPageRequest
import com.digin.bullet.company.domain.entity.Company
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyRepository: R2dbcRepository<Company, Long> {

    // TODO fix query method name
    suspend fun getCompanyByStockCode(stockCode: String): Company?
    suspend fun findCompanyByStockCode(stockCode: String): Company?

    suspend fun getCompaniesByStockCodeIn(stockCodes: List<String>): List<Company>
    suspend fun findCompaniesByStockCodeIn(stockCodes: List<String>): List<Company>

    fun getCompaniesByKrNameContaining(name: String, pageable: Pageable): Flow<Company>
    fun findCompaniesByKrNameContaining(name: String, pageable: Pageable): Flow<Company>

    suspend fun findCompaniesByIdIn(ids: List<Long>): List<Company>

    suspend fun findCompaniesByIsKospi200(isKospi200: Boolean): List<Company>
}