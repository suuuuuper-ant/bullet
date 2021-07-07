package com.digin.bullet.account.router

import com.digin.bullet.account.handler.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRouter(
    private val authHandler: AuthHandler,
) {
    @Bean
    fun authRoute() =  coRouter {
        "/auth".nest {
            POST("/sign-up", authHandler::signUp)
            POST("/sign-in", authHandler::signIn)
        }

    }
}