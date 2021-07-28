package com.digin.bullet.api.router

import com.digin.bullet.api.handler.HomeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class HomeRouter(
    private val homeHandler: HomeHandler
) {

    @Bean
    fun homeRoute() = coRouter {
        "/home".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("", homeHandler::getHome)
                GET("/{stockCode}", homeHandler::getHomeByStockCode)
            }
        }

    }

}