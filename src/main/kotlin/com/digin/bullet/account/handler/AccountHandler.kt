package com.digin.bullet.account.handler

import arrow.core.Either
import com.digin.bullet.account.model.http.response.AccountDTO
import com.digin.bullet.account.model.http.response.AccountResponse
import com.digin.bullet.account.model.http.response.ErrorResponse
import com.digin.bullet.account.service.AccountService
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class AccountHandler(
    val accountService: AccountService
) {
    private val logger = KotlinLogging.logger {}

    suspend fun getAccount(serverRequest: ServerRequest): ServerResponse {
        val accountId = serverRequest.awaitPrincipal()!!.name.toLong()

        return when (val account = accountService.getAccount(accountId = accountId)) {
            is Either.Left -> ServerResponse
                .badRequest()
                .bodyValueAndAwait(account.mapLeft { ErrorResponse(result = it.name) })
            is Either.Right -> ServerResponse
                .ok()
                .bodyValueAndAwait(AccountResponse(result = AccountDTO(id = account.value.id!!, email = account.value.email)))
        }
    }
}