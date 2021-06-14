package com.digin.bullet.company.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class CompanyHandler {

    suspend fun getCompany(): ServerResponse {
        return ok().bodyValueAndAwait(1)
    }
}