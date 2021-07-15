package com.digin.bullet.news.router

import com.digin.bullet.news.handler.NewsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class NewsRouter(
    private val newsHandler: NewsHandler
) {

    @Bean
    fun newsRoute() = coRouter {
        "/news".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/{stockCode}", newsHandler::getNewsByStockCode)
            }
        }
    }
}