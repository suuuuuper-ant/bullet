package com.digin.bullet.account.handler

import com.digin.bullet.account.model.Account
import com.digin.bullet.core.jwt.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Service
class AuthHandler(
    private val jwtUtil: JWTUtil
) {

    suspend fun signIn(): ServerResponse {
        val account = Account(
            "a",
            "b",
            "c",
            listOf("USER")
        )

        return ServerResponse.ok().bodyValueAndAwait(jwtUtil.generateToken(account))
    }
}