package com.digin.bullet.account.handler

import com.digin.bullet.account.model.Account
import com.digin.bullet.core.jwt.JWTUtil
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.time.LocalDateTime

@Service
class AuthHandler(
    private val jwtUtil: JWTUtil,
    private val accountRepository: AccountRepository
) {

    suspend fun signIn(): ServerResponse {
        val account = Account(
            "a",
            "b",
            "c",
            listOf("USER")
        )

        try {
            val dd = accountRepository.save(com.digin.bullet.account.entitiy.Account(
                id = null,
                email = "ddddd",
                password = "ddddd",
                name = "ddddd",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                role = "USER"
            )).awaitSingle()

            println("dd = ${dd}")
        } catch (e: Exception) {
            println("e = ${e}")
        }
   

        return ServerResponse.ok().bodyValueAndAwait("")
    }
}