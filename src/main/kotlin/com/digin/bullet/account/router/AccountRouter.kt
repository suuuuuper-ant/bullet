package com.digin.bullet.account.router

import com.digin.bullet.account.handler.AuthHandler
import kotlinx.coroutines.CoroutineScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*

@Configuration
class AccountRouter(
    private val authHandler: AuthHandler
) {
    @Bean
    fun accountRoute() = RouterFunctions.nest(RequestPredicates.path("/accounts"),
        router {
            listOf(
                GET("/") { ServerResponse.ok().body(BodyInserters.fromObject(arrayOf(1, 2, 3))) }
            )
        }
    )
    @Bean
    fun authRoute() =  coRouter {
        "/auth".nest {
            POST("/sign-up", authHandler::signUp)
            GET("/sign-in", authHandler::signIn)
        }

    }
}