package com.digin.bullet.core.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.UnsupportedOperationException

@Component
class SecurityContextRepository(
    private val authenticationManager: JwtAuthenticationManager
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        throw UnsupportedOperationException("Not Supported yet.")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange)
            .flatMap { Mono.justOrEmpty(it.request.headers["X-AUTH-TOKEN"]) }
            .filter { it.isNotEmpty() }
            .map { it.first() }
//            .map { it[0].value }
            .flatMap { authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(it, it))
                .map { i -> SecurityContextImpl(i)} }
    }
}