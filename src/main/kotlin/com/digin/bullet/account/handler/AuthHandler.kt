package com.digin.bullet.account.handler

import arrow.core.Either
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.core.jwt.JWTUtil
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class AuthHandler(
    private val jwtUtil: JWTUtil,
    private val accountService: AccountService
) {

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val accountId = request.queryParamOrNull("id") ?: return ServerResponse.badRequest().bodyValueAndAwait("no query")

        val account = accountService.getAccount(accountId.toLong())
        return when (account) {
            is Either.Left -> ServerResponse.badRequest().bodyValueAndAwait("not found account")
            is Either.Right -> ServerResponse.ok().bodyValueAndAwait(account)
        }

    }
}