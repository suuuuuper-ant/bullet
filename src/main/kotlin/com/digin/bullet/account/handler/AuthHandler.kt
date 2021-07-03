package com.digin.bullet.account.handler

import arrow.core.Either
import arrow.core.right
import com.digin.bullet.account.model.dto.SignUpDTO
import com.digin.bullet.account.model.http.request.SignUpRequest
import com.digin.bullet.account.model.http.response.SignUpResponse
import com.digin.bullet.account.service.AccountService
import com.digin.bullet.core.jwt.JWTUtil
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class AuthHandler(
    private val jwtUtil: JWTUtil,
    private val accountService: AccountService
) {

    suspend fun signUp(request: ServerRequest): ServerResponse {
        val signUpRequest = request.awaitBody<SignUpRequest>()
        val signUpDTO = SignUpDTO(
            email = signUpRequest.email,
            name = signUpRequest.name,
            password = signUpRequest.password,
            favorites = signUpRequest.favorites
        )
        return when (val createdAccount = accountService.signUp(signUpDTO)) {
            is Either.Left -> ServerResponse
                .badRequest()
                .bodyValueAndAwait(createdAccount.value.name)
            is Either.Right -> ServerResponse
                .ok()
                .bodyValueAndAwait(SignUpResponse(
                    id = createdAccount.value.id!!,
                    email = createdAccount.value.email,
                    name = createdAccount.value.name,
                    favorites = listOf()
                ))
        }
    }

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val accountId = request.queryParamOrNull("id") ?: return ServerResponse.badRequest().bodyValueAndAwait("no query")

        val account = accountService.getAccount(accountId.toLong())
        return when (account) {
            is Either.Left -> ServerResponse.badRequest().bodyValueAndAwait("not found account")
            is Either.Right -> ServerResponse.ok().bodyValueAndAwait(account)
        }
    }
}