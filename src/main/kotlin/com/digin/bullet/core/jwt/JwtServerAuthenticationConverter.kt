package com.digin.bullet.core.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.http.HttpHeaders

@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
            .flatMap { Mono.justOrEmpty(it.request.headers["X-AUTH-TOKEN"]) }
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .map { UsernamePasswordAuthenticationToken(it, it) }
    }
}