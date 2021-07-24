package com.digin.bullet.company.repository

import com.digin.bullet.company.domain.entity.CompanyFavorite
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CompanyFavoriteRepository : R2dbcRepository<CompanyFavorite, Long>{
    suspend fun findAllByAccountIdAndCompanyIdInAndIsDeleted(accountId: Long,
                                                           companyIds: List<Long>,
                                                           isDeleted: Boolean): List<CompanyFavorite>
    suspend fun findAllByIdIn(ids: Set<Long>): List<CompanyFavorite>

    fun findAllByAccountIdAndIsDeleted(accountId: Long,
                                             isDeleted: Boolean = false,
                                             pageable: Pageable
    ): Flow<CompanyFavorite>
}