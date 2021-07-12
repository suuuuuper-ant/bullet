package com.digin.bullet.company.service

import arrow.core.Either
import com.digin.bullet.company.domain.entity.Company
import com.digin.bullet.company.model.exception.CompanyException
import com.digin.bullet.company.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {
    suspend fun getCompanyByStockCode(stockCode: String): Either<CompanyException, Company> {
        val company = companyRepository.getCompanyByStockCode(stockCode) ?: return Either.Left(CompanyException.NOT_FOUND_COMPANY)
        return Either.Right(company)
    }
}