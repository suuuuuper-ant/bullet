package com.digin.bullet.account.service

import arrow.core.Either
import com.digin.bullet.account.domain.entity.Account
import com.digin.bullet.account.model.dto.SignUpDTO
import com.digin.bullet.account.model.exception.AccountException
import com.digin.bullet.account.repository.AccountRepository
import com.digin.bullet.company.domain.entity.CompanyFavorite
import com.digin.bullet.company.repository.CompanyFavoriteRepository
import com.digin.bullet.company.repository.CompanyRepository
import com.digin.bullet.company.service.CompanyService
import com.digin.bullet.core.jwt.JWTUtil
import com.digin.bullet.core.utils.passwordEncode
import com.digin.bullet.core.utils.passwordMatch
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service


@Service
class AccountService(
    private val jwtUtil: JWTUtil,
    private val accountRepository: AccountRepository,
    private val companyFavoriteRepository: CompanyFavoriteRepository,
    private val companyRepository: CompanyRepository
) {

    suspend fun signUp(signUpDTO: SignUpDTO): Either<AccountException, Account> {
        val account = accountRepository.findByEmail(signUpDTO.email)
        if (account != null) {
            return Either.Left(AccountException.DUPLICATED_ACCOUNT)
        }

        val newAccount = Account(
            email = signUpDTO.email,
            name = signUpDTO.name,
            password = passwordEncode(signUpDTO.password),
            role = "USER",
        )




        val created = accountRepository
            .save(newAccount)
            .awaitSingleOrNull() ?: return Either.Left(AccountException.FAIL_CREATE_ACCOUNT)

        val favoriteCompanies = companyRepository.findCompaniesByStockCodeIn(signUpDTO.favorites)


        companyFavoriteRepository.saveAll(
                favoriteCompanies.map {
                    CompanyFavorite(
                            companyId = it.id!!,
                            accountId = created.id!!,
                            isDeleted = false,
                    )
                })

        return Either.Right(created)
    }

    suspend fun signIn(email: String, rawPassword: String): Either<AccountException, Account> {
        val account = accountRepository.findByEmail(email)
            ?: return Either.Left(AccountException.NOT_FOUND_ACCOUNT)
        val isMatched = passwordMatch(rawPassword = rawPassword, encodedPassword = account.password)
        if (!isMatched) {
           return Either.Left(AccountException.NOT_MATCHED_PASSWORD)
        }

        return Either.Right(account)
    }

    suspend fun getAccount(accountId: Long): Either<AccountException, Account> {
        val account = accountRepository.findById(accountId)
            .awaitSingleOrNull() ?: return Either.Left(AccountException.NOT_FOUND_ACCOUNT)

        return Either.Right(account)
    }

    suspend fun getAccountByEmail(email: String): Either<AccountException, Account> {
        val account = accountRepository.findByEmail(email)
            ?: return Either.Left(AccountException.NOT_FOUND_ACCOUNT)

        return Either.Right(account)
    }
}