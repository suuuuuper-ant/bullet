package com.digin.bullet.account.service

import arrow.core.Either
import com.digin.bullet.account.domain.entity.Account
import com.digin.bullet.account.model.dto.SignUpDTO
import com.digin.bullet.account.model.exception.AccountException
import com.digin.bullet.account.repository.AccountRepository
import com.digin.bullet.core.utils.passwordEncode
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

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

        return Either.Right(created)
    }

    suspend fun getAccount(accountId: Long): Either<AccountException, Account> {
        val account = accountRepository.findById(accountId)
            .awaitSingleOrNull() ?: return Either.Left(AccountException.NOT_FOUND_ACCOUNT)

        return Either.Right(account)
//        return Either.conditionally(account != null,
//            { AccountException.NOT_FOUND_ACCOUNT },
//            { account })
    }
}