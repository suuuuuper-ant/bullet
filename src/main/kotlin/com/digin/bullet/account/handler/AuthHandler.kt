package com.digin.bullet.account.handler

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.right
import com.digin.bullet.account.model.dto.SignInDTO
import com.digin.bullet.account.model.dto.SignUpDTO
import com.digin.bullet.account.model.http.request.SignInRequest
import com.digin.bullet.account.model.http.request.SignUpRequest
import com.digin.bullet.account.model.http.response.SignInResponse
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
                .bodyValueAndAwait(
                    SignUpResponse(
                        id = createdAccount.value.id!!,
                        email = createdAccount.value.email,
                        name = createdAccount.value.name,
                        favorites = listOf()
                    )
                )
        }
    }

    suspend fun signIn(request: ServerRequest): ServerResponse {
        val body = request.awaitBodyOrNull<SignInRequest>()
            ?: return ServerResponse.badRequest().bodyValueAndAwait("invalid request")

        val account = accountService.signIn(email = body.email, rawPassword = body.password)

        return when (account) {
            is Either.Left -> ServerResponse
                .badRequest()
                .bodyValueAndAwait(SignInResponse(status = "error", result = "not found account"))
            is Either.Right -> ServerResponse
                .ok()
                .bodyValueAndAwait(
                    SignInResponse(
                        result = account
                            .map { jwtUtil.generateToken(
                                id = it.id!!,
                                email = it.email,
                                name = it.name,
                                role = it.role) }
                            .getOrElse { } as String
                    )
                )
        }
    }
}