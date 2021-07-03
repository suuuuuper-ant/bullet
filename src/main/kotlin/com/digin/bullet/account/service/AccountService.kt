package com.digin.bullet.account.service

import arrow.core.Either
import com.digin.bullet.account.domain.entity.Account
import com.digin.bullet.account.model.exception.AccountException
import com.digin.bullet.account.repository.AccountRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

    suspend fun getAccount(accountId: Long): Either<AccountException, Account> {
        val account = accountRepository.findById(accountId)
            .awaitSingleOrNull() ?: return Either.Left(AccountException.NOT_FOUND_ACCOUNT)

        return Either.Right(account)
//        return Either.conditionally(account != null,
//            { AccountException.NOT_FOUND_ACCOUNT },
//            { account })
    }
}