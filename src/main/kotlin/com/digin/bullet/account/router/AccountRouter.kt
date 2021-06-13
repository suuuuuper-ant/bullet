package com.digin.bullet.account.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class AccountRouter {
    @Bean
    fun accountRoute() = RouterFunctions.nest(RequestPredicates.path("/accounts"),
        router {
            listOf(
                GET("/") { ServerResponse.ok().body(BodyInserters.fromObject(arrayOf(1, 2, 3))) }
            )
        }
    )
    @Bean
    fun authRoute() = RouterFunctions.nest(RequestPredicates.path("/auth"),
        router {
            listOf(
                GET("/signin") { ServerResponse.ok().body(BodyInserters.fromObject(arrayOf(1, 2, 3))) }
            )
        }
    )
}