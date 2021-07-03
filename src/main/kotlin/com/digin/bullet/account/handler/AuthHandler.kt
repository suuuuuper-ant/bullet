package com.digin.bullet.account.handler

import arrow.core.Either
import com.digin.bullet.account.model.exception.AccountException
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.core.jwt.JWTUtil
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AuthHandler(
    private val jwtUtil: JWTUtil,
    private val accountService: AccountService
) {

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val account = accountService.getAccount(1)
        return when (account) {
            is Either.Left -> ServerResponse.badRequest().bodyValueAndAwait("not found account")
            is Either.Right -> ServerResponse.ok().bodyValueAndAwait(account)
        }

    }
}