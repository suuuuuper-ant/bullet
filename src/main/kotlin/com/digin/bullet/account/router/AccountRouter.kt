package com.digin.bullet.account.router

import com.digin.bullet.account.handler.AuthHandler
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
    fun authRoute() = RouterFunctions.nest(RequestPredicates.path("/auth"),
        coRouter {
          listOf(
              GET("/signin") { authHandler.signIn() }
          )
        }
//        router {
//            listOf(
//                GET("/signin") { ServerResponse.ok().body(BodyInserters.fromObject(arrayOf(1, 2, 3))) }
//            )
//        }
    )
}