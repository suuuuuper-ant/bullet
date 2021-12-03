package com.digin.bullet.company.router

import com.digin.bullet.company.handler.CompanyHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
class CompanyRouter(
    private val companyHandler: CompanyHandler
) {

    @Bean
    fun companyRoute() = coRouter {
        "/companies".nest {
           accept(MediaType.APPLICATION_JSON).nest {
               GET("/simple", companyHandler::getSimples)
               GET("/search", companyHandler::searchCompaniesByName)
               GET("/{stockCode}", companyHandler::getCompany)
               POST("/favorites", companyHandler::doFavorites)
               DELETE("/favorites", companyHandler::cancelFavorites)
           }
        }
    }
}