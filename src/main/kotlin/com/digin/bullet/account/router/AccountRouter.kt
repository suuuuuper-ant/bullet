package com.digin.bullet.account.router

import com.digin.bullet.account.handler.AccountHandler
import com.digin.bullet.account.handler.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class AccountRouter(
//    private val authHandler: AuthHandler,
    private val accountHandler: AccountHandler
) {
    @Bean
    fun accountsRoute() = coRouter {
        "/accounts".nest {
            GET("", accountHandler::getAccount)
        }
    }



//    @Bean
//    fun authRoute() =  coRouter {
//        "/auth".nest {
//            POST("/sign-up", authHandler::signUp)
//            POST("/sign-in", authHandler::signIn)
//        }
//
//    }
}