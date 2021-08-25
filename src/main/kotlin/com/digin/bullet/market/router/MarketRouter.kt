package com.digin.bullet.market.router

import com.digin.bullet.market.handler.MarketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MarketRouter(
    private val marketHandler: MarketHandler
) {
    @Bean
    fun marketRoute() = coRouter {
        "/markets".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/{stockCode}", marketHandler::getMarketStacksByStockCode)
            }
        }
    }
}